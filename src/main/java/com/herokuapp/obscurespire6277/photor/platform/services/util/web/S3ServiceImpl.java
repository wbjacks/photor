package com.herokuapp.obscurespire6277.photor.platform.services.util.web;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.common.io.ByteStreams;
import jodd.petite.meta.PetiteInject;

import java.io.ByteArrayInputStream;
import java.io.IOException;

class S3ServiceImpl implements S3Service {

    private final AmazonS3 _s3Client;

    @PetiteInject
    S3ServiceImpl(AmazonS3 s3Client) {
        _s3Client = s3Client;
    }

    @Override
    public byte[] get(String bucketName, String key) throws IOException {
        return ByteStreams.toByteArray(_s3Client.getObject(bucketName, key).getObjectContent());
    }

    @Override
    public void put(String bucketName, String key, byte[] bytes) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        _s3Client.putObject(bucketName, key, new ByteArrayInputStream(bytes), metadata);
    }

    @Override
    public void delete(String bucketName, String key) {
        _s3Client.deleteObject(bucketName, key);
    }

}
