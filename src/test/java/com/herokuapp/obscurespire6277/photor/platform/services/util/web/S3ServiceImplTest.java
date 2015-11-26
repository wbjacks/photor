package com.herokuapp.obscurespire6277.photor.platform.services.util.web;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.io.ByteStreams;
import com.herokuapp.obscurespire6277.photor.util.Pointer;
import com.herokuapp.obscurespire6277.photor.util.testing.Mockeries;
import org.apache.commons.io.IOUtils;
import org.hamcrest.core.IsEqual;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Invocation;
import org.jmock.lib.action.CustomAction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class S3ServiceImplTest {

    Mockery mockery;
    AmazonS3 s3Client;

    @Before
    public void before() {
        mockery = Mockeries.mockery();
        s3Client = mockery.mock(AmazonS3.class);
    }

    @After
    public void after() {
        mockery.assertIsSatisfied();
    }

    @Test
    public void get_delegatesToS3Client_returnsAsBytes() throws IOException {
        mockery.checking(new Expectations() {{
            oneOf(s3Client).getObject("BUCKETNAME", "KEY");
            will(returnValue(new S3Object() {{
                setObjectContent(IOUtils.toInputStream("IDK SOME BYTES"));
            }}));
        }});

        assertThat(getS3Service().get("BUCKETNAME", "KEY"), IsEqual.equalTo("IDK SOME BYTES".getBytes()));
    }

    @Test
    public void put_delegatesToS3Client_uploadsAsInputStream() throws IOException {
        final Pointer<InputStream> uploadedInputStream = Pointer.pointer();
        final Pointer<ObjectMetadata> uploadedMetadata = Pointer.pointer();
        mockery.checking(new Expectations() {{
            oneOf(s3Client).putObject(
                    with(equal("BUCKETNAME")), with(equal("KEY")), with(any(InputStream.class)), with(any(ObjectMetadata.class)));
            will(new CustomAction("capture input stream and metadata") {
                @Override
                public Object invoke(Invocation invocation) throws Throwable {
                    uploadedInputStream.set((InputStream) invocation.getParameter(2));
                    uploadedMetadata.set((ObjectMetadata) invocation.getParameter(3));
                    return null;
                }
            });
        }});

        getS3Service().put("BUCKETNAME", "KEY", "IDK SOME BYTES".getBytes());

        assertThat(ByteStreams.toByteArray(uploadedInputStream.get()), IsEqual.equalTo("IDK SOME BYTES".getBytes()));
        assertEquals(14L, uploadedMetadata.get().getContentLength());
    }

    @Test
    public void delete_delegatesToS3Client() {
        mockery.checking(new Expectations() {{
            oneOf(s3Client).deleteObject("BUCKETNAME", "KEY");
        }});

        getS3Service().delete("BUCKETNAME", "KEY");
    }

    private S3Service getS3Service() {
        return new S3ServiceImpl(s3Client);
    }

}
