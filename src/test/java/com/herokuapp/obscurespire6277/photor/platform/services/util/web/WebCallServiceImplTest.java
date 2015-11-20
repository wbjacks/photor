package com.herokuapp.obscurespire6277.photor.platform.services.util.web;

import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import com.herokuapp.obscurespire6277.photor.platform.web.util.WebCallException;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicStatusLine;
import org.apache.log4j.Logger;
import org.easymock.EasyMockSupport;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Ignore
public class WebCallServiceImplTest extends EasyMockSupport {
    @Test
    @SuppressWarnings("unchecked")
    public void testDoGetRequestThrowsWebCallExceptionForBadUrl() throws URISyntaxException, WebCallException {
        WebCallServiceImpl webCallServiceImpl = createMockBuilder(WebCallServiceImpl.class).addMockedMethod("buildUri")
                .createStrictMock();
        expect(webCallServiceImpl.buildUri("", "", new HashMap<>())).andThrow(new URISyntaxException("", "")).once();
        replay(webCallServiceImpl);

        try {
            webCallServiceImpl.doGetRequest("", "", new HashMap<>());
        } catch (WebCallException e) {
            verifyAll();
            return;
        }
        fail("End of method reached, test should throw.");
    }

    @Test
    public void testDoGetRequestThrowsWebCallExceptionForBadRequest() throws URISyntaxException, IOException {
        WebCallServiceImpl webCallServiceImpl = createMockBuilder(WebCallServiceImpl.class)
                .addMockedMethod("buildUri")
                .addMockedMethod("getLogger")
                .addMockedMethod("getGetRequestRunner")
                .createStrictMock();

        expect(webCallServiceImpl.buildUri("", "", new HashMap<>())).andReturn(URI.create(""));
        Request request = createStrictMock(Request.class);
        expect(webCallServiceImpl.getGetRequestRunner(anyObject(URI.class))).andReturn(request);
        expect(request.execute()).andThrow(new IOException());

        Logger logger = createMock(Logger.class);
        expect(webCallServiceImpl.getLogger()).andStubReturn(logger);
        logger.info(anyString());
        expectLastCall().asStub();

        //replayAll();
        replay(webCallServiceImpl, request, logger);

        try {
            webCallServiceImpl.doGetRequest("", "", new HashMap<>());
        } catch (WebCallException e) {
            verifyAll();
            return;
        }
        fail("End of method reached, test should throw.");
    }

    @Test
    public void testDoGetRequestThrowsWebCallExceptionForBadRequestParse() throws URISyntaxException, IOException {
        WebCallServiceImpl webCallServiceImpl = createMockBuilder(WebCallServiceImpl.class)
                .addMockedMethod("buildUri")
                .addMockedMethod("getLogger")
                .addMockedMethod("getGetRequestRunner")
                .addMockedMethod("generateStringResponse")
                .createStrictMock();

        expect(webCallServiceImpl.buildUri("", "", new HashMap<>())).andReturn(URI.create(""));

        Logger logger = createMock(Logger.class);
        expect(webCallServiceImpl.getLogger()).andStubReturn(logger);
        logger.info(anyString());
        expectLastCall().asStub();

        Request request = createStrictMock(Request.class);
        Response response = createStrictMock(Response.class);
        expect(webCallServiceImpl.getGetRequestRunner(anyObject(URI.class))).andReturn(request);
        expect(request.execute()).andReturn(response);
        expect(response.returnResponse()).andReturn(null);
        expect(webCallServiceImpl.generateStringResponse(null)).andThrow(new IOException());

        replay(webCallServiceImpl, request, response, logger);

        try {
            webCallServiceImpl.doGetRequest("", "", new HashMap<>());
        } catch (WebCallException e) {
            verifyAll();
            return;
        }
        fail("End of method reached, test should throw.");
    }

    @Test
    public void testDoGetRequestThrowsThirdPartyExceptionWhenResponseDoesntHave200Status() throws URISyntaxException, IOException, WebCallException {
        WebCallServiceImpl webCallServiceImpl = createMockBuilder(WebCallServiceImpl.class)
                .addMockedMethod("buildUri")
                .addMockedMethod("getLogger")
                .addMockedMethod("getGetRequestRunner")
                .addMockedMethod("generateStringResponse")
                .createStrictMock();

        expect(webCallServiceImpl.buildUri("", "", new HashMap<>())).andReturn(URI.create(""));

        Logger logger = createMock(Logger.class);
        expect(webCallServiceImpl.getLogger()).andStubReturn(logger);
        logger.info(anyString());
        expectLastCall().asStub();

        Request request = createStrictMock(Request.class);
        Response response = createStrictMock(Response.class);
        expect(webCallServiceImpl.getGetRequestRunner(anyObject(URI.class))).andReturn(request);
        expect(request.execute()).andReturn(response);
        HttpResponse httpResponse = createStrictMock(HttpResponse.class);
        expect(response.returnResponse()).andReturn(httpResponse);

        expect(webCallServiceImpl.generateStringResponse(httpResponse)).andReturn("FOO");
        expect(httpResponse.getStatusLine()).andReturn(new BasicStatusLine(new ProtocolVersion("http", 1, 1), 500, "")).times(2);

        replayAll();

        try {
            webCallServiceImpl.doGetRequest("", "", new HashMap<>());
        } catch (ThirdPartyException e) {
            verifyAll();
            return;
        }
        fail("End of method reached, test should throw.");
    }

    @Test
    public void testDoGetRequestReturnsResponseWhenResponseHas200Status() throws URISyntaxException, IOException, WebCallException {
        WebCallServiceImpl webCallServiceImpl = createMockBuilder(WebCallServiceImpl.class)
                .addMockedMethod("buildUri")
                .addMockedMethod("getLogger")
                .addMockedMethod("getGetRequestRunner")
                .addMockedMethod("generateStringResponse")
                .createStrictMock();

        expect(webCallServiceImpl.buildUri("", "", new HashMap<>())).andReturn(URI.create(""));

        Logger logger = createMock(Logger.class);
        expect(webCallServiceImpl.getLogger()).andStubReturn(logger);
        logger.info(anyString());
        expectLastCall().asStub();

        Request request = createStrictMock(Request.class);
        Response response = createStrictMock(Response.class);
        expect(webCallServiceImpl.getGetRequestRunner(anyObject(URI.class))).andReturn(request);
        expect(request.execute()).andReturn(response);
        HttpResponse httpResponse = createStrictMock(HttpResponse.class);
        expect(response.returnResponse()).andReturn(httpResponse);

        expect(webCallServiceImpl.generateStringResponse(httpResponse)).andReturn("FOO");
        expect(httpResponse.getStatusLine()).andReturn(new BasicStatusLine(new ProtocolVersion("http", 1, 1), 200, "")).once();

        replayAll();

        assertEquals("FOO", webCallServiceImpl.doGetRequest("", "", new HashMap<>()).get());
        verifyAll();
    }
}