package com.herokuapp.obscurespire6277.photor.util.web;


import jodd.petite.meta.PetiteBean;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;

@PetiteBean("webCallService")
public class WebCallServiceImpl implements WebCallService {
    private static final String DEFAULT_BASE_SCHEME = "http";

    public WebCallServiceImpl() {
    }

    public HttpResponse doGetRequest(String host, String path, Map<String, String> urlParams) {
        URI uri;
        try {
            uri = new URIBuilder().setScheme(DEFAULT_BASE_SCHEME).setHost(host).setPath(path)
                .setParameters(urlParams.entrySet().stream().map(entry -> new BasicNameValuePair
                (entry.getKey(), entry.getValue())).collect(Collectors.toList())).build();
        }
        catch (URISyntaxException e) {
            // TODO: (wjackson) add logging
            return null;
        }

        try {
            return Request.Get(uri).execute().returnResponse();
        }
        catch (IOException e) {
            return null;
        }
    }
}
