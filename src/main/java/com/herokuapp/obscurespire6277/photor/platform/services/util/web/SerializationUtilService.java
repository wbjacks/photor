package com.herokuapp.obscurespire6277.photor.platform.services.util.web;

public interface SerializationUtilService {
    <T> T parseJsonToObjectSkippingRoot(String json, String rootNode, Class<T> cla$$);
}
