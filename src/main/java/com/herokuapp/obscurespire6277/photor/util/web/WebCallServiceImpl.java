package com.herokuapp.obscurespire6277.photor.util.web;


import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import jodd.petite.meta.PetiteBean;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@PetiteBean("webCallService")
public class WebCallServiceImpl implements WebCallService {
    private static final String DEFAULT_BASE_SCHEME = "http";

    public WebCallServiceImpl() {
    }

    @Override
    public Optional<String> doGetRequest(String host, String path, Map<String, String> urlParams)
     throws IOException, ThirdPartyException {
        URI uri;
        try {
            uri = new URIBuilder().setScheme(DEFAULT_BASE_SCHEME).setHost(host).setPath(path)
            .setParameters(urlParams.entrySet().stream().map(entry -> new BasicNameValuePair
            (entry.getKey(), entry.getValue())).collect(Collectors.toList())).build();
        } catch (URISyntaxException e) {
            // TODO: (wjackson) add logging
            return null;
        }

        HttpResponse response = Request.Get(uri).execute().returnResponse();
        InputStream inputStream = null;
        String responseContent;
        try {
            inputStream = response.getEntity().getContent();
            responseContent = IOUtils.toString(inputStream, response.getEntity()
                    .getContentEncoding().getValue());
        } catch (IOException e) {
            // TODO: (wbjacks) log
            return Optional.empty();
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }

        if (response.getStatusLine().getStatusCode() == 200) {
            return Optional.of(responseContent);
        }
        else {
            throw new ThirdPartyException(response.getStatusLine().getStatusCode(), responseContent);
        }
    }
}
