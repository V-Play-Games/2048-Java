package net.vpg.game2048;

import java.util.Arrays;

public class Util {
    @SuppressWarnings("unchecked")
    public static  <T> T[] deepArrayCopy(T[] original) {
        if (original == null)
            return null;
        T[] copy = Arrays.copyOf(original, original.length);
        for (int i = 0, originalLength = original.length; i < originalLength; i++) {
            T element = original[i];
            original[i] = element instanceof Object[]
                ? (T) deepArrayCopy((Object[]) element)
                : element;
        }
        return copy;
    }
}
