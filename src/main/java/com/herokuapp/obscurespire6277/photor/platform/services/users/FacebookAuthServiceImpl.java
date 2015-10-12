package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookDebugTokenResponse;
import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import com.herokuapp.obscurespire6277.photor.util.web.WebCallService;
import jodd.json.JsonParser;
import jodd.petite.meta.PetiteBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@PetiteBean("facebookAuthService")
public class FacebookAuthServiceImpl implements FacebookAuthService {

    private static final String FACEBOOK_API_HOST = "graph.facebook.com";
    private static final String DEBUG_TOKEN_PATH = "/debug_token";
    private static final String LONG_TOKEN_PATH = "oauth/access_token";

    private final WebCallService _webCallService;

    public FacebookAuthServiceImpl(WebCallService webCallService) {
        _webCallService = webCallService;
    }

    @Override
    public boolean isUserAuthenticatedWithToken(String userId, String token) {
        // Check our DBs to make sure user is authenticated on our system (userId and token match,
        // user is logged in)

        // Make call to facebook token inspection
        Map<String, String> params = new HashMap<>();
        params.put("input_token", token);
        params.put("access_token", "" /* app token */);
        Optional<String> json;
        try {
            json = _webCallService.doGetRequest(FACEBOOK_API_HOST, DEBUG_TOKEN_PATH, params);
        }
        catch (IOException e) {
            // TODO: (wjacks) Log error
            return false;
        }
        catch (ThirdPartyException e) {
            // TODO: (wjacks) Log error
            return false;
        }

        if (json.isPresent()) {
            // TODO: (wbjacks) this is the naive approach
            FacebookDebugTokenResponse facebookDebugTokenResponse =
                new JsonParser().map("data", FacebookDebugTokenResponse.class).parse(json.get());

            // TODO: (wbjacks) add ids
            return facebookDebugTokenResponse.isValid() &&
                facebookDebugTokenResponse.getUserId().equals(userId) &&
                facebookDebugTokenResponse.getAppId().equals("");
        }
        else {
            return false;
        }
    }

    @Override
    public Optional<String> getLongTokenFromShortToken(String shortToken) {
        // Make call to fbook to get long token
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "fb_exchange_token");
        params.put("client_id", "" /* app id */);
        params.put("client_secret", "" /* app secret */);
        params.put("fb_exchange_token", shortToken);

        // TODO: (wjackson) check if response contains more than just token
        try {
            return _webCallService.doGetRequest(FACEBOOK_API_HOST, LONG_TOKEN_PATH, params);
        }
        catch (IOException e) {
            return Optional.empty();
        }
        catch (ThirdPartyException e) {
            return Optional.empty();
        }
    }
}