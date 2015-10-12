package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookDebugTokenResponse;
import com.herokuapp.obscurespire6277.photor.util.web.WebCallService;
import jodd.json.JsonParser;
import jodd.petite.meta.PetiteBean;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@PetiteBean("facebookAuthService")
public class FacebookAuthServiceImpl implements FacebookAuthService {

    private static final String FACEBOOK_API_HOST = "graph.facebook.com";
    private static final String DEBUG_TOKEN_PATH = "/debug_token";

    private final WebCallService _webCallService;

    public FacebookAuthServiceImpl(WebCallService webCallService) {
        _webCallService = webCallService;
    }

    public boolean isUserAuthenticatedWithToken(String userId, String token) {
        // Check our DBs to make sure user is authenticated on our system (userId and token match,
        // user is logged in)

        // Make call to facebook token inspection
        Map<String, String> params = new HashMap<>();
        params.put("input_token", token);
        params.put("access_token", "" /* app token */);
        HttpResponse response = _webCallService.doGetRequest(FACEBOOK_API_HOST, DEBUG_TOKEN_PATH, params);

        // TODO: (wbjacks) this is the naive approach
        if (response.getStatusLine().getStatusCode() == 200) {
            // Check is_valid, user_id, and app_id
            InputStream inputStream;
            try {
                inputStream = response.getEntity().getContent();
            }
            catch (IOException e) {
                // TODO: (wbjacks) log
                return false;
            }
            String json;
            try {
                json = IOUtils.toString(inputStream,
                        response.getEntity().getContentEncoding().getValue());
            }
            catch (IOException e) {
                // TODO: (wbjacks) log
                return false;
            }
            finally {
                IOUtils.closeQuietly(inputStream);
            }
            FacebookDebugTokenResponse facebookDebugTokenResponse =
                new JsonParser().map("data", FacebookDebugTokenResponse.class).parse(json);

            // TODO: (wbjacks) add ids
            return facebookDebugTokenResponse.isValid() &&
                facebookDebugTokenResponse.getUserId().equals(userId) &&
                facebookDebugTokenResponse.getAppId().equals("");
        }
        else {
            // TODO: (wbjacks) error logging
            return false;
        }
    }
}