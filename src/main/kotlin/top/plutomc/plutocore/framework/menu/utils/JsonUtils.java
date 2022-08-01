package top.plutomc.plutocore.framework.menu.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public final class JsonUtils {
    private JsonUtils() {
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        return new Gson().fromJson(json, TypeToken.getParameterized(List.class, clazz).getType());
    }

}
