package com.stock;

import com.google.gson.Gson;
import lombok.NoArgsConstructor;

/**
 * @author waylon.mifsud
 * @since 30/09/2015
 *
 * Required to convert objects to json objects when sending rest requests.
 */
@NoArgsConstructor
public class Utils
{
    private static Gson gson = new Gson();

    public static String convertToJsonString(Object o)
    {
        return gson.toJson(o);
    }
}
