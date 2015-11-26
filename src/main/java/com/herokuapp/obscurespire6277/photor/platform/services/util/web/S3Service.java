package com.herokuapp.obscurespire6277.photor.platform.services.util.web;

import java.io.File;
import java.io.IOException;

public interface S3Service {

    byte[] get(String bucketName, String key) throws IOException;

    void put(String bucketName, String key, byte[] bytes);

    void delete(String bucketName, String key);

}
