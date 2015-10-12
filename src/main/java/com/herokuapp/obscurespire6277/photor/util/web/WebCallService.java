package com.herokuapp.obscurespire6277.photor.util.web;

import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface WebCallService {
    Optional<String> doGetRequest(String host, String path, Map<String, String> urlParams) throws IOException, ThirdPartyException;
}
