package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookDebugTokenResponse;
import com.herokuapp.obscurespire6277.photor.platform.web.util.SerializationUtilService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import com.herokuapp.obscurespire6277.photor.util.crypto.CryptoService;
import com.herokuapp.obscurespire6277.photor.util.web.WebCallException;
import com.herokuapp.obscurespire6277.photor.util.web.WebCallService;
import jodd.petite.meta.PetiteBean;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@PetiteBean("facebookAuthService")
public class FacebookAuthServiceImpl implements FacebookAuthService {
    private static final Logger _logger = Logger.getLogger(WebCallService.class);

    private static final String FACEBOOK_API_HOST = "graph.facebook.com";
    private static final String DEBUG_TOKEN_PATH = "/v2.5/debug_token";
    private static final String LONG_TOKEN_PATH = "oauth/access_token";
    private static final String FACEBOOK_APP_ID = "1696711563891334";
    private static final String FACEBOOK_APP_SECRET = "FACEBOOK_APP_SECRET";

    private final CryptoService _cryptoService;
    private final SerializationUtilService _serializationUtilService;
    private final WebCallService _webCallService;

    public FacebookAuthServiceImpl(CryptoService cryptoService, SerializationUtilService serializationUtilService, WebCallService webCallService) {
        _cryptoService = cryptoService;
        _serializationUtilService = serializationUtilService;
        _webCallService = webCallService;
    }

    @Override
    public boolean isUserAuthenticatedWithToken(String userId, String token) {
        // Check our DBs to make sure user is authenticated on our system (userId and token match,
        // user is logged in)

        // Make call to facebook token inspection
        Map<String, String> params = new HashMap<>();
        params.put("input_token", token);
        params.put("access_token",
                String.format("%s|%s", FACEBOOK_APP_ID, _cryptoService.getEnvironmentVariableValue(FACEBOOK_APP_SECRET)));
        Optional<String> json;
        try {
            json = _webCallService.doGetRequest(FACEBOOK_API_HOST, DEBUG_TOKEN_PATH, params);
        } catch (WebCallException e) {
            _logger.error(String.format("Error checking auth [%s]", e.getMessage()));
            return false;
        } catch (ThirdPartyException e) {
            _logger.error(String.format("Error checking auth [%s]", e.getMessage()));
            return false;
        }

        if (json.isPresent()) {
            _logger.debug(String.format("Response from token auth check: %s", json));
            // TODO: (wbjacks) this is the naive approach
            FacebookDebugTokenResponse facebookDebugTokenResponse = _serializationUtilService.parseJsonToObjectSkippingRoot(json.get(), "data", FacebookDebugTokenResponse.class);

            // TODO: (wbjacks) add ids
            return facebookDebugTokenResponse.isValid() &&
                    facebookDebugTokenResponse.getUserId().equals(userId) &&
                    facebookDebugTokenResponse.getAppId().equals(FACEBOOK_APP_ID);
        } else {
            return false;
        }
    }

    @Override
    public String getLongTokenFromShortToken(String shortToken) throws WebCallException, ThirdPartyException {
        // Make call to fbook to get long token
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "fb_exchange_token");
        params.put("client_id", FACEBOOK_APP_ID);
        params.put("client_secret", _cryptoService.getEnvironmentVariableValue(FACEBOOK_APP_SECRET));
        params.put("fb_exchange_token", shortToken);

        // TODO: (wjackson) check if response contains more than just token
        // TODO: (wjackson) thow something other than IOException
        return _webCallService.doGetRequest(FACEBOOK_API_HOST, LONG_TOKEN_PATH, params)
                .orElseThrow(() -> new ThirdPartyException(0, "Unknown error in webcall."));
    }
}