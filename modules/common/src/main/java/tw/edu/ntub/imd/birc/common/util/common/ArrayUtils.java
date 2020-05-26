package tw.edu.ntub.imd.birc.common.util.common;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

@UtilityClass
public class ArrayUtils {
    public int NOT_FOUND_INDEX = -1;

    public <T> boolean contains(T[] array, T t) {
        return Arrays.asList(array)
                .contains(t);
    }

    public <T> boolean allContainsMatchArray(T[] array, Object... matchArray) {
        List<Object> matchList = Arrays.asList(matchArray);
        for (T t : array) {
            if (!matchList.contains(t)) {
                return false;
            }
        }
        return true;
    }

    public <T> int indexOf(T[] array, T t) {
        return Arrays.asList(array)
                .indexOf(t);
    }

    public <T> int lastIndexOf(T[] array, T t) {
        return Arrays.asList(array)
                .lastIndexOf(t);
    }

    public <T> String[] mapToString(T[] array, Function<T, String> mapFunction) {
        return map(array, String.class, mapFunction);
    }

    public <T> int[] mapToInt(T[] array, ToIntFunction<T> toIntFunction) {
        return Arrays.stream(array)
                .mapToInt(toIntFunction)
                .toArray();
    }

    public <T> long[] mapToLong(T[] array, ToLongFunction<T> toLongFunction) {
        return Arrays.stream(array)
                .mapToLong(toLongFunction)
                .toArray();
    }

    public <T> double[] mapToDouble(T[] array, ToDoubleFunction<T> toDoubleFunction) {
        return Arrays.stream(array)
                .mapToDouble(toDoubleFunction)
                .toArray();
    }

    @SuppressWarnings("unchecked")
    public <T, R> R[] map(T[] array, Class<R> resultClass, Function<T, R> mapFunction) {
        return Arrays.stream(array)
                .map(mapFunction)
                .toArray(i -> (R[]) Array.newInstance(resultClass, i));
    }
}
