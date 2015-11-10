package com.herokuapp.obscurespire6277.photor.platform.services.util.web;


import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import com.herokuapp.obscurespire6277.photor.platform.web.util.WebCallException;
import jodd.petite.meta.PetiteBean;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@PetiteBean("webCallService")
public class WebCallServiceImpl implements WebCallService {
    private static final Logger _logger = Logger.getLogger(WebCallService.class);
    private static final String BASE_SCHEME = "https";

    public WebCallServiceImpl() {
    }

    @Override
    public Optional<String> doGetRequest(String host, String path, Map<String, String> urlParams)
     throws WebCallException, ThirdPartyException {
        URI uri;
        HttpResponse response;
        try {
            uri = buildUri(host, path, urlParams);
            getLogger().info(String.format("<Request>%s</Request>", uri.toString()));
            response = getGetRequestRunner(uri).execute().returnResponse();
        }
        catch (IOException e) {
            throw new WebCallException(String.format("Error executing webcall: %s", e.getMessage()));
        } catch (URISyntaxException e) {
            throw new WebCallException(String
                    .format("Bad URI syntax in web call [host=%s]. [path=%s], [urlParams=%s]", host,
                            path, urlParams.entrySet().stream().map(entry -> String.format("%s:%s", entry
                                    .getKey(), entry.getValue())).collect(Collectors.joining("|"))));
        }
        InputStream inputStream = null;
        String responseContent;
        try {
            // TODO: (wbjacks) SEC ERROR: check encoding
            responseContent = generateStringResponse(response);
            getLogger().info(String.format("<Response>%s</Response>", responseContent));
        } catch (IOException e) {
            throw new WebCallException(String.format("Error executing webcall: %s", e.getMessage()));
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

    URI buildUri(String host, String path, Map<String, String> urlParams) throws URISyntaxException {
        return new URIBuilder().setScheme(BASE_SCHEME).setHost(host).setPath(path)
                .setParameters(urlParams.entrySet().stream().map(entry -> new BasicNameValuePair
                (entry.getKey(), entry.getValue())).collect(Collectors.toList())).build();
    }

    Request getGetRequestRunner(URI uri) {
        return Request.Get(uri);
    }

    String generateStringResponse(HttpResponse httpResponse) throws IOException {
        return IOUtils.toString(httpResponse.getEntity().getContent());
    }

    Logger getLogger() {
        return _logger;
    }
}
