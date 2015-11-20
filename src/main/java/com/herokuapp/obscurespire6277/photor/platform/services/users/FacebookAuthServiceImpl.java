package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookDebugTokenResponse;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.services.util.web.SerializationUtilService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import com.herokuapp.obscurespire6277.photor.platform.services.util.crypto.CryptoService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.WebCallException;
import com.herokuapp.obscurespire6277.photor.platform.services.util.web.WebCallService;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@PetiteBean("facebookAuthService")
public class FacebookAuthServiceImpl implements FacebookAuthService {
    private static final Logger _logger = Logger.getLogger(WebCallService.class);

    static final String FACEBOOK_API_HOST = "graph.facebook.com";
    static final String DEBUG_TOKEN_PATH = "/v2.5/debug_token";
    static final String FACEBOOK_APP_ID = "1696711563891334";
    static final String FACEBOOK_APP_SECRET = "FACEBOOK_APP_SECRET";

    private final CryptoService _cryptoService;
    private final SerializationUtilService _serializationUtilService;
    private final WebCallService _webCallService;

    @PetiteInject
    public FacebookAuthServiceImpl(CryptoService cryptoService, SerializationUtilService serializationUtilService, WebCallService webCallService) {
        _cryptoService = cryptoService;
        _serializationUtilService = serializationUtilService;
        _webCallService = webCallService;
    }

    @Override
    public boolean isUserAuthenticatedWithToken(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken) {
        Map<String, String> params = new HashMap<>();
        params.put("input_token", facebookLongToken.getToken());
        params.put("access_token",
                String.format("%s|%s", FACEBOOK_APP_ID, _cryptoService.getEnvironmentVariableValue(FACEBOOK_APP_SECRET)));
        Optional<String> maybeJson;
        try {
            maybeJson = _webCallService.doGetRequest(FACEBOOK_API_HOST, DEBUG_TOKEN_PATH, params);
        } catch (WebCallException e) {
            _logger.error(String.format("Error checking auth [%s]", e.getMessage()));
            return false;
        } catch (ThirdPartyException e) {
            _logger.error(String.format("Error checking auth [%s]", e.getMessage()));
            return false;
        }

        if (maybeJson.isPresent()) {
            _logger.debug(String.format("Response from token auth check: %s", maybeJson.get()));
            FacebookDebugTokenResponse facebookDebugTokenResponse = _serializationUtilService.parseJsonToObjectSkippingRoot(maybeJson.get(), "data", FacebookDebugTokenResponse.class);

            return facebookDebugTokenResponse.isValid() &&
                    facebookDebugTokenResponse.getFacebookUserId().equals(facebookUserId) &&
                    facebookDebugTokenResponse.getAppId().equals(FACEBOOK_APP_ID);
        } else {
            return false;
        }
    }
}