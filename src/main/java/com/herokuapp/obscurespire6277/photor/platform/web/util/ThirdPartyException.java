package com.herokuapp.obscurespire6277.photor.platform.web.util;

public class ThirdPartyException extends RuntimeException {
    public ThirdPartyException(int httpCode, String errorMessage) {
        super(String.format("Third part error with response code [%d]. Message is: [%s]", httpCode, errorMessage));
    }
}
