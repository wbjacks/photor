package com.herokuapp.obscurespire6277.photor.platform.web.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookDebugTokenResponse;
import jodd.petite.meta.PetiteBean;

import java.util.Map;

@PetiteBean("serializationUtilService")
public class SerializationUtilServiceImpl implements SerializationUtilService {
    // TODO: (wbjacks) use reflection to populate type adaptors
    private final Gson _parser = new GsonBuilder()
            .registerTypeAdapter(FacebookDebugTokenResponse.class, FacebookDebugTokenResponse.getDeserializer())
            .create();

    public SerializationUtilServiceImpl() {
    }

    @Override
    public <T> T parseJsonToObjectSkippingRoot(String json, String rootNode, Class<T> cla$$) {
        return _parser.fromJson(_parser.toJson(_parser.fromJson(json, Map.class).get(rootNode)), cla$$);
    }
}
