package top.plutomc.plutocore.framework.menu.utils;

public final class ArrayUtils {
    public ArrayUtils() {
    }

    public static int[] insertElement(int[] original, int element) {
        int length = original.length;
        int[] destination = new int[length + 1];
        System.arraycopy(original, 0, destination, 0, length);
        destination[length] = element;
        System.arraycopy(original, original.length, destination, length
                + 1, 0);
        return destination;
    }
}
