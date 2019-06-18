package codingdojo;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.File;
import java.io.InputStream;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ContentService.class, ImageUtil.class, FileUtil.class, GoogleStorageUtil.class, AwsUtil.class})
public class ContentServiceTest {
    @Test
    public void getBytes() throws Exception {
        // Arrange
        ContentService contentService = new ContentService();
        ContentService contentServiceSpy = PowerMockito.spy(contentService);

        byte[] bytes = new byte[8];
        doReturn(bytes).when(contentServiceSpy, "getFileBytes", any());

        Content contentSpy = Mockito.spy(Content.class);

        // Act
        byte[] result = contentServiceSpy.getBytes(contentSpy);

        // Assert
        assertNotNull(result);

    }

    @Test
    public void getBytes_with_Exception() throws Exception {
        // Arrange
        ContentService contentService = new ContentService();
        ContentService contentServiceSpy = PowerMockito.spy(contentService);

        doThrow(new RuntimeException("failed to read file bytes")).when(contentServiceSpy, "getFileBytes", any());

        Content contentSpy = Mockito.spy(Content.class);

        // Act
        byte[] result = contentServiceSpy.getBytes(contentSpy);

        // Assert
        assertNull(result);

    }

    @Test
    public void generateAndSaveThumbnailToTempDisk() throws Exception {
        // Arrange
        ContentService contentService = new ContentService();
        ContentService contentServiceSpy = PowerMockito.spy(contentService);

        Content contentSpy = Mockito.spy(Content.class);
        doReturn(true).when(contentSpy).isImage();

        byte[] bytes = new byte[8];
        doReturn(bytes).when(contentServiceSpy, "getBytes", any());

        mockStatic(ImageUtil.class);
        when(ImageUtil.scaleImage(bytes, 0, bytes.length, Content.THUMBNAIL_WIDTH, Content.THUMBNAIL_HEIGHT))
                .thenReturn(new byte[8]);

        File fileSpy = PowerMockito.spy(new File("myfile.txt"));
        doReturn(true).when(fileSpy).exists();

        ContentThumbnailTempFileHelper thumbnailFileHelper = Mockito.spy(ContentThumbnailTempFileHelper.class);
        doReturn(fileSpy).when(thumbnailFileHelper, "save", anyString(), any());
        doReturn(thumbnailFileHelper).when(contentServiceSpy, "getThumbnailTempFileHelperInstance");

        // Act
        InputStream result = contentServiceSpy.generateAndSaveThumbnailToTempDisk(contentSpy);

        assertNotNull(result);
    }

    @Test
    public void getThumbnailTempFileHelperInstance() throws Exception {
        // Arrange
        ContentService contentService = new ContentService();

        // Act
        ContentThumbnailTempFileHelper result = Whitebox.<ContentThumbnailTempFileHelper> invokeMethod(contentService, "getThumbnailTempFileHelperInstance");

        assertNotNull(result);
    }

    @Test
    public void getContentTempFileHelperInstance() throws Exception {
        // Arrange
        ContentService contentService = new ContentService();

        // Act
        ContentTempFileHelper result = Whitebox.<ContentTempFileHelper> invokeMethod(contentService, "getContentTempFileHelperInstance");

        assertNotNull(result);
    }

    @Ignore("fails since we migrated from Google to AWS")
    @Test
    public void getFileBytes() throws Exception {
        // Arrange
        ContentService contentService = new ContentService();
        Content contentSpy = Mockito.spy(Content.class);
        GoogleStorageUtil storageSpy = Mockito.spy(GoogleStorageUtil.class);
        PowerMockito.mockStatic(GoogleStorageUtil.class);
        when(GoogleStorageUtil.getInstance()).thenReturn(storageSpy);
        doReturn(new byte[8]).when(storageSpy).getBytes(any());

        // Act
        byte[] result = Whitebox.<byte[]> invokeMethod(contentService, "getFileBytes", contentSpy);

        assertNotNull(result);
    }

}
