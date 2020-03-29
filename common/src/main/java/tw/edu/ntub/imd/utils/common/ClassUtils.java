package tw.edu.ntub.imd.utils.common;

import tw.edu.ntub.imd.exception.*;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ClassUtils {
    private ClassUtils() {

    }

    public static <T> T newInstance(Class<T> objectClass) throws ModifierPrivateException, NotFoundMethodException {
        try {
            return objectClass.getConstructor().newInstance();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ModifierPrivateException(objectClass.getName());
        } catch (InstantiationException | NoSuchMethodException e) {
            throw new NotFoundMethodException("沒有無參數建構元：" + objectClass.getName());
        }
    }

    public static boolean isSuperClass(Class<?> target, Class<?>... extendClassArray) {
        for (Class<?> superClass : extendClassArray) {
            if (!target.isAssignableFrom(superClass)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isChildrenClass(Class<?> target, Class<?>... superClassArray) {
        for (Class<?> superClass : superClassArray) {
            if (!superClass.isAssignableFrom(target)) {
                return false;
            }
        }
        return true;
    }

    public static List<FieldWrapper> getAllFieldAndContainSuperClass(Class<?> target) throws NullParameterException {
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

    public static Collection<Method> getAllMethods(Class clazz,
                                                   boolean includeAllPackageAndPrivateMethodsOfSuperclasses,
                                                   boolean includeOverridenAndHidden) {

        Predicate<Method> include = m -> !m.isBridge() && !m.isSynthetic() &&
                Character.isJavaIdentifierStart(m.getName().charAt(0))
                && m.getName().chars().skip(1).allMatch(Character::isJavaIdentifierPart);

        Set<Method> methods = new LinkedHashSet<>();
        Collections.addAll(methods, clazz.getMethods());
        methods.removeIf(include.negate());
        Stream.of(clazz.getDeclaredMethods()).filter(include).forEach(methods::add);

        final int access = Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE;

        Package p = clazz.getPackage();
        if (!includeAllPackageAndPrivateMethodsOfSuperclasses) {
            int pass = includeOverridenAndHidden ?
                    Modifier.PUBLIC | Modifier.PROTECTED : Modifier.PROTECTED;
            include = include.and(m -> {
                int mod = m.getModifiers();
                return (mod & pass) != 0
                        || (mod & access) == 0 && m.getDeclaringClass().getPackage() == p;
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
                    types.computeIfAbsent(methodKey(m), x -> new HashSet<>()).add(p);
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
            Stream.of(clazz.getDeclaredMethods()).filter(include).forEach(methods::add);
        }
        return methods;
    }

    static boolean noPkgOverride(
            Method m, Map<Object, Set<Package>> types, Set<Package> pkgIndependent) {
        Set<Package> pkg = types.computeIfAbsent(methodKey(m), key -> new HashSet<>());
        return pkg != pkgIndependent && pkg.add(m.getDeclaringClass().getPackage());
    }

    private static Object methodKey(Method m) {
        return Arrays.asList(m.getName(),
                MethodType.methodType(m.getReturnType(), m.getParameterTypes()));
    }

    public static class ClassWrapper {
        private Class<?> target;

        public ClassWrapper(Class<?> target) throws NullParameterException {
            if (target == null) {
                throw new NullParameterException();
            }
            this.target = target;
        }

        public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
            return target.isAnnotationPresent(annotationClass);
        }

        public <A extends Annotation> A getAnnotation(Class<A> annotationClass) throws AnnotationNotDeclareException {
            if (isAnnotationPresent(annotationClass)) {
                return target.getAnnotation(annotationClass);
            } else {
                throw new AnnotationNotDeclareException(annotationClass);
            }
        }

        public Object newInstance(Object... parameters) throws ProjectException {
            Class<?>[] parameterTypes = new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }
            try {
                Constructor<?> constructor = target.getConstructor(parameterTypes);
                return constructor.newInstance(parameters);
            } catch (NoSuchMethodException e) {
                throw new NotFoundMethodException("無此建構元：" + Arrays.toString(parameterTypes));
            } catch (IllegalAccessException e) {
                throw new ModifierPrivateException(target.getName() + "#Constructor(" + Arrays.toString(parameterTypes) + ")");
            } catch (InvocationTargetException e) {
                throw new MethodUtils.MethodIsNotInTargetException(target, parameterTypes);
            } catch (InstantiationException e) {
                throw new UnknownException(e);
            }
        }

        public String getName() {
            return target.getName();
        }

        public String getPackageName() {
            return target.getPackageName();
        }

        public boolean isSuperClass(Class<?> extendClass) {
            return ClassUtils.isSuperClass(target, extendClass);
        }

        public boolean isChildrenClass(Class<?> superClass) {
            return ClassUtils.isChildrenClass(target, superClass);
        }

        public Class<?> get() {
            return target;
        }

        public List<FieldWrapper> getAllField() throws NullParameterException {
            return ClassUtils.getAllFieldAndContainSuperClass(target);
        }

        public Collection<Method> getAllMethod(boolean includeAllPackageAndPrivateMethodsOfSuperclasses,
                                               boolean includeOverridenAndHidden) {
            return ClassUtils.getAllMethods(target, includeAllPackageAndPrivateMethodsOfSuperclasses, includeOverridenAndHidden);
        }
    }

    public static class FieldWrapper {
        private Object fieldSourceObject;
        private Field field;

        public FieldWrapper(Field field) throws NullParameterException {
            this(null, field);
        }

        public FieldWrapper(Object fieldSourceObject, Field field) throws NullParameterException {
            if (field == null) {
                throw new NullParameterException();
            }
            this.fieldSourceObject = fieldSourceObject;
            this.field = field;
        }

        public boolean isStatic() {
            return Modifier.isStatic(field.getModifiers());
        }

        public boolean isFinal() {
            return Modifier.isFinal(field.getModifiers());
        }

        public Class<?> getType() {
            return field.getType();
        }

        public String getName() {
            return field.getName();
        }

        public Object getValue() throws NullParameterException, ModifierPrivateException {
            return getValue(fieldSourceObject);
        }

        public Object getValue(Object fieldSourceObject) throws NullParameterException, ModifierPrivateException {
            if (fieldSourceObject == null) {
                throw new NullParameterException();
            }
            try {
                return field.get(fieldSourceObject);
            } catch (IllegalAccessException e) {
                throw new ModifierPrivateException(field.getName());
            }
        }

        public void setValue(Object value) throws NullParameterException, ModifierPrivateException {
            setValue(fieldSourceObject, value);
        }

        public void setValue(Object fieldSourceObject, Object value) throws NullParameterException, ModifierPrivateException {
            if (fieldSourceObject == null) {
                throw new NullParameterException();
            }
            try {
                field.set(fieldSourceObject, value);
            } catch (IllegalAccessException e) {
                throw new ModifierPrivateException(field.getName());
            }
        }

        public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
            return field.isAnnotationPresent(annotationClass);
        }

        public <A extends Annotation> A getAnnotation(Class<A> annotationClass) throws AnnotationNotDeclareException {
            if (isAnnotationPresent(annotationClass)) {
                return field.getAnnotation(annotationClass);
            } else {
                throw new AnnotationNotDeclareException(annotationClass);
            }
        }

        public Field get() {
            return field;
        }
    }
}
