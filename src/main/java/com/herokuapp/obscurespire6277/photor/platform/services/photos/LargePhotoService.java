package com.herokuapp.obscurespire6277.photor.platform.services.photos;

import com.herokuapp.obscurespire6277.photor.entities.Photo;
import com.herokuapp.obscurespire6277.photor.platform.services.util.web.S3Service;
import jodd.petite.meta.PetiteInject;

public class LargePhotoService implements PhotoService {

    private static final String BUCKET_NAME = "photor.large";

    private final S3Service _s3Service;

    @PetiteInject
    public LargePhotoService(S3Service s3Service) {
        _s3Service = s3Service;
    }

}
