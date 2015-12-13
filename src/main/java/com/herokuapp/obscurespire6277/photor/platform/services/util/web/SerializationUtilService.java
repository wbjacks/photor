package com.herokuapp.obscurespire6277.photor.platform.services.util.web;

import com.herokuapp.obscurespire6277.photor.platform.models.BaseJsonEntity;

public interface SerializationUtilService {
    <T extends BaseJsonEntity> T parseJsonToObject(String json, Class<T> cla$$);

    <T extends BaseJsonEntity> T parseJsonToObjectSkippingRoot(String json, String rootNode, Class<T> cla$$);

    <T extends BaseJsonEntity> String serializeObjectToJson(T object);

}
