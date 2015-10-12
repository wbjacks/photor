package com.herokuapp.obscurespire6277.photor.util.web;

import org.apache.http.HttpResponse;
import java.util.Map;

public interface WebCallService {
    HttpResponse doGetRequest(String host, String path, Map<String, String> urlParams);
}
