package top.plutomc.plutocore.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class NmsRefUtil {
    private NmsRefUtil() {
    }

    public static double getRecentTps() {
        try {
            Object serverInstance = Class.forName("net.minecraft.server.MinecraftServer").getMethod("getServer").invoke(null);
            Field recentTpsField = serverInstance.getClass().getField("recentTps");
            double[] recentTps = (double[]) recentTpsField.get(serverInstance);
            return recentTps[0];
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchFieldException |
                 NoSuchMethodException e) {
            return 0.00;
        }
    }
}
