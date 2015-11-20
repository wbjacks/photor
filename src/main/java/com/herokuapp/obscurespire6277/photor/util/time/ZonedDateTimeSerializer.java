package com.herokuapp.obscurespire6277.photor.util.time;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

public class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {
    @Override
    public JsonElement serialize(final ZonedDateTime zonedDateTime, final Type type, final JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("time", new JsonPrimitive(zonedDateTime.toEpochSecond()));
        return jsonObject;
    }
}
