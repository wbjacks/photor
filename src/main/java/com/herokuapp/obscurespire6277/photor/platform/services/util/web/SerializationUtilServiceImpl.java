package com.herokuapp.obscurespire6277.photor.platform.services.util.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.BaseJsonEntity;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.models.json.FacebookDebugTokenResponse;
import com.herokuapp.obscurespire6277.photor.platform.models.json.UserRequest;
import com.herokuapp.obscurespire6277.photor.util.time.ZonedDateTimeSerializer;
import jodd.petite.meta.PetiteBean;

import java.time.ZonedDateTime;
import java.util.Map;

@PetiteBean("serializationUtilService")
public class SerializationUtilServiceImpl implements SerializationUtilService {
    // TODO: (wbjacks) use reflection to populate type adaptors
    private final Gson _parser = new GsonBuilder()
            .registerTypeAdapter(FacebookDebugTokenResponse.class, FacebookDebugTokenResponse
                .getDeserializer())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer())
            .registerTypeAdapter(Id.class, Id.getSerializer())
            .registerTypeAdapter(UserRequest.class, UserRequest.getDeserializer())
            .registerTypeAdapter(UserView.class, UserView.getSerializer()).create();

    public SerializationUtilServiceImpl() {
    }

    @Override
    public <T extends BaseJsonEntity> T parseJsonToObjectSkippingRoot(String json, String rootNode, Class<T> cla$$) {
        // TODO: (wbjacks) check for bad json
        return _parser.fromJson(_parser.toJson(_parser.fromJson(json, Map.class).get(rootNode)), cla$$);
    }

    @Override
    public <T extends BaseJsonEntity> T parseJsonToObject(String json, Class<T> cla$$) {
        return _parser.fromJson(json, cla$$);
    }

    @Override
    public <T extends BaseJsonEntity> String serializeObjectToJson(T object) {
        return _parser.toJson(object);
    }
}