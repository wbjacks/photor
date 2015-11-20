package com.herokuapp.obscurespire6277.photor.platform.services.util.web;

import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import com.herokuapp.obscurespire6277.photor.platform.web.util.WebCallException;

import java.util.Map;
import java.util.Optional;

public interface WebCallService {
    Optional<String> doGetRequest(String host, String path, Map<String, String> urlParams)
            throws WebCallException, ThirdPartyException;
}
