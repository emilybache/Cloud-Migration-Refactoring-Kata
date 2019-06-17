package codingdojo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.InputStream;


import static org.junit.Assert.assertNotNull;
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

}
