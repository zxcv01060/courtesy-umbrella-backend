package tw.edu.ntub.imd.birc.common.util.common;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.imd.birc.common.exception.ModifierPrivateException;
import tw.edu.ntub.imd.birc.common.exception.NotFoundMethodException;
import tw.edu.ntub.imd.birc.common.exception.NotPrimitiveTypeException;
import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.wrapper.FieldWrapper;

import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@UtilityClass
@SuppressWarnings("unused")
public class ClassUtils {
    private final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP;

    static {
        PRIMITIVE_WRAPPER_MAP = new HashMap<>();
        PRIMITIVE_WRAPPER_MAP.put(Void.TYPE, Void.class);
        PRIMITIVE_WRAPPER_MAP.put(Byte.TYPE, Byte.class);
        PRIMITIVE_WRAPPER_MAP.put(Short.TYPE, Short.class);
        PRIMITIVE_WRAPPER_MAP.put(Integer.TYPE, Void.class);
        PRIMITIVE_WRAPPER_MAP.put(Long.TYPE, Long.class);
        PRIMITIVE_WRAPPER_MAP.put(Float.TYPE, Float.class);
        PRIMITIVE_WRAPPER_MAP.put(Double.TYPE, Double.class);
        PRIMITIVE_WRAPPER_MAP.put(Boolean.TYPE, Boolean.class);
    }

    public <T> T newInstance(Class<T> objectClass) throws ModifierPrivateException, NotFoundMethodException {
        try {
            return objectClass.getConstructor()
                    .newInstance();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ModifierPrivateException(objectClass.getName());
        } catch (InstantiationException | NoSuchMethodException e) {
            throw new NotFoundMethodException("沒有無參數建構元：" + objectClass.getName());
        }
    }

    public boolean isSuperClass(Class<?> target, Class<?>... extendClassArray) {
        Class<?> newTarget = PRIMITIVE_WRAPPER_MAP.getOrDefault(target, target);
        for (Class<?> extendClass : extendClassArray) {
            Class<?> newExtendClass = PRIMITIVE_WRAPPER_MAP.getOrDefault(extendClass, extendClass);
            if (!target.isAssignableFrom(newExtendClass)) {
                return false;
            }
        }
        return true;
    }

    public boolean isChildrenClass(Class<?> target, Class<?>... superClassArray) {
        Class<?> newTarget = PRIMITIVE_WRAPPER_MAP.getOrDefault(target, target);
        for (Class<?> superClass : superClassArray) {
            Class<?> newSuperClass = PRIMITIVE_WRAPPER_MAP.getOrDefault(superClass, superClass);
            if (!newSuperClass.isAssignableFrom(target)) {
                return false;
            }
        }
        return true;
    }

    public Class<?> getPrimitiveWrapper(Class<?> target) throws NotPrimitiveTypeException {
        if (isPrimitive(target)) {
            return PRIMITIVE_WRAPPER_MAP.get(target);
        } else if (isPrimitiveWrapper(target)) {
            return target;
        } else {
            throw new NotPrimitiveTypeException(target);
        }
    }

    public boolean isPrimitive(Class<?> target) {
        return PRIMITIVE_WRAPPER_MAP.containsKey(target);
    }

    public boolean isPrimitiveWrapper(Class<?> target) {
        return PRIMITIVE_WRAPPER_MAP.containsValue(target);
    }

    public boolean isInteger(Class<?> target) {
        return target.equals(byte.class) ||
                target.equals(Byte.class) ||
                target.equals(short.class) ||
                target.equals(Short.class) ||
                target.equals(int.class) ||
                target.equals(Integer.class) ||
                target.equals(long.class) ||
                target.equals(Long.class);
    }

    public boolean isNumber(Class<?> target) {
        try {
            target = getPrimitiveWrapper(target);
        } catch (NotPrimitiveTypeException ignored) {

        }
        return isChildrenClass(target, Number.class);
    }

    public boolean isFloatOrDouble(Class<?> target) {
        return target.equals(float.class) ||
                target.equals(Float.class) ||
                target.equals(double.class) ||
                target.equals(Double.class);
    }

    public List<FieldWrapper> getAllFieldAndContainSuperClass(Class<?> target) throws NullParameterException {
        if (!target.equals(Object.class)) {
            List<FieldWrapper> result = new ArrayList<>();
            for (Field field : target.getDeclaredFields()) {
                result.add(new FieldWrapper(field));
            }
            result.addAll(getAllFieldAndContainSuperClass(target.getSuperclass()));
            return Collections.unmodifiableList(result);
        }
        return Collections.emptyList();
    }

    public Collection<Method> getAllMethods(Class clazz,
                                            boolean includeAllPackageAndPrivateMethodsOfSuperclasses,
                                            boolean includeOverridenAndHidden) {

        Predicate<Method> include = m -> !m.isBridge() && !m.isSynthetic() &&
                Character.isJavaIdentifierStart(m.getName()
                                                        .charAt(0))
                && m.getName()
                .chars()
                .skip(1)
                .allMatch(Character::isJavaIdentifierPart);

        Set<Method> methods = new LinkedHashSet<>();
        Collections.addAll(methods, clazz.getMethods());
        methods.removeIf(include.negate());
        Stream.of(clazz.getDeclaredMethods())
                .filter(include)
                .forEach(methods::add);

        final int access = Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE;

        Package p = clazz.getPackage();
        if (!includeAllPackageAndPrivateMethodsOfSuperclasses) {
            int pass = includeOverridenAndHidden ?
                    Modifier.PUBLIC | Modifier.PROTECTED : Modifier.PROTECTED;
            include = include.and(m -> {
                int mod = m.getModifiers();
                return (mod & pass) != 0
                        || (mod & access) == 0 && m.getDeclaringClass()
                        .getPackage() == p;
            });
        }
        if (!includeOverridenAndHidden) {
            Map<Object, Set<Package>> types = new HashMap<>();
            final Set<Package> pkgIndependent = Collections.emptySet();
            for (Method m : methods) {
                int acc = m.getModifiers() & access;
                if (acc == Modifier.PRIVATE) {
                    continue;
                }
                if (acc != 0) {
                    types.put(methodKey(m), pkgIndependent);
                } else {
                    types.computeIfAbsent(methodKey(m), x -> new HashSet<>())
                            .add(p);
                }
            }
            include = include.and(m -> {
                int acc = m.getModifiers() & access;
                return acc != 0 ? acc == Modifier.PRIVATE
                        || types.putIfAbsent(methodKey(m), pkgIndependent) == null :
                        noPkgOverride(m, types, pkgIndependent);
            });
        }
        for (clazz = clazz.getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
            Stream.of(clazz.getDeclaredMethods())
                    .filter(include)
                    .forEach(methods::add);
        }
        return methods;
    }

    boolean noPkgOverride(
            Method m, Map<Object, Set<Package>> types, Set<Package> pkgIndependent) {
        Set<Package> pkg = types.computeIfAbsent(methodKey(m), key -> new HashSet<>());
        return pkg != pkgIndependent && pkg.add(m.getDeclaringClass()
                                                        .getPackage());
    }

    private Object methodKey(Method m) {
        return Arrays.asList(m.getName(),
                             MethodType.methodType(m.getReturnType(), m.getParameterTypes()));
    }
}
