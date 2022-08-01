package top.plutomc.plutocore.framework.menu.utils;

public final class PatternUtils {
    public PatternUtils() {
    }

    public static int[] process(char patternChar, String[] pattern, int[]... line) {
        int[] ints = new int[0];
        for (int a = 0; a < pattern.length; a++) {
            for (int i = 0; i < pattern[a].length(); i++) {
                if (pattern[a].charAt(i) == patternChar) {
                    ints = ArrayUtils.insertElement(ints, line[a][i]);
                }
            }
        }
        return ints;
    }
}
