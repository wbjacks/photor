package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.models.json.FacebookDebugTokenResponse;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.services.util.crypto.CryptoService;
import com.herokuapp.obscurespire6277.photor.platform.services.util.web.SerializationUtilService;
import com.herokuapp.obscurespire6277.photor.platform.services.util.web.WebCallService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.WebCallException;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FacebookAuthServiceImplTest {
    private static final FacebookUserId USER_ID = new FacebookUserId("happyLeeland");

    @Test
    public void testIsUserAuthenticatedWithTokenPassesForSuccessfulResponseFromFacebook() throws WebCallException {
        FacebookDebugTokenResponse facebookDebugTokenResponse = FacebookDebugTokenResponse
            .buildForTestingWithAllValues(FacebookAuthServiceImpl.FACEBOOK_APP_ID, null, true, USER_ID);

        CryptoService cryptoService = createStrictMock(CryptoService.class);
        expect(cryptoService.getEnvironmentVariableValue(FacebookAuthServiceImpl.FACEBOOK_APP_SECRET)).andReturn("").once();

        WebCallService webCallService = createStrictMock(WebCallService.class);
        expect(webCallService.doGetRequest(eq(FacebookAuthServiceImpl.FACEBOOK_API_HOST),
                eq(FacebookAuthServiceImpl.DEBUG_TOKEN_PATH), anyObject(Map.class))).andReturn(Optional.of("")).once();

        SerializationUtilService serializationUtilService = createStrictMock(SerializationUtilService.class);
        expect(serializationUtilService.parseJsonToObjectSkippingRoot("", "data", FacebookDebugTokenResponse.class))
                .andReturn(facebookDebugTokenResponse).once();

        replay(cryptoService, webCallService, serializationUtilService);

        assertTrue(new Builder().with(cryptoService).with(webCallService).with(serializationUtilService)
            .build().isUserAuthenticatedWithToken(USER_ID, new FacebookLongToken("")));
        verify(cryptoService, webCallService, serializationUtilService);
    }

    @Test
    public void testIsUserAuthenticatedWithTokenFailsIfWebcallThrowsException() throws WebCallException {
        CryptoService cryptoService = createStrictMock(CryptoService.class);
        expect(cryptoService.getEnvironmentVariableValue(FacebookAuthServiceImpl.FACEBOOK_APP_SECRET)).andReturn("").once();

        WebCallService webCallService = createStrictMock(WebCallService.class);
        expect(webCallService.doGetRequest(eq(FacebookAuthServiceImpl.FACEBOOK_API_HOST),
                eq(FacebookAuthServiceImpl.DEBUG_TOKEN_PATH), anyObject(Map.class))).andThrow(new WebCallException("")).once();

        replay(cryptoService, webCallService);

        assertFalse(new Builder().with(cryptoService).with(webCallService).build()
            .isUserAuthenticatedWithToken(USER_ID, new FacebookLongToken("")));
        verify(cryptoService, webCallService);
    }

    private static class Builder {
        private CryptoService _cryptoService;
        private SerializationUtilService _serializationUtilService;
        private WebCallService _webCallService;

        private Builder() {
        }

        private FacebookAuthServiceImpl build() {
            return new FacebookAuthServiceImpl(_cryptoService, _serializationUtilService, _webCallService);
        }

        private Builder with(CryptoService cryptoService) {
            _cryptoService = cryptoService;
            return this;
        }

        private Builder with(SerializationUtilService serializationUtilService) {
            _serializationUtilService = serializationUtilService;
            return this;
        }

        private Builder with(WebCallService webCallService) {
            _webCallService = webCallService;
            return this;
        }
    }

}
