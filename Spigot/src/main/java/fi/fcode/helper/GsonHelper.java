package fi.fcode.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

public class GsonHelper {
    private static final GsonBuilder gsonBuilder = new GsonBuilder().enableComplexMapKeySerialization()
            .setPrettyPrinting();

    private static final Gson gson = gsonBuilder.create();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

}