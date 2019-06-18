package codingdojo;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ContentService {
    private static Logger logger = Logger.getLogger("codingdojo.ContentService");
    private static ContentTempFileHelper contentTempFileHelper;
    private static ContentThumbnailTempFileHelper contentThumbnailTempFileHelper;

    /**
     * If the given content is an image, create a thumbnail and return it.
     * Additionally store the thumbnail on the local disk
     * (this will make it quicker to retrieve next time)
     */
    public InputStream generateAndSaveThumbnailToTempDisk(Content content) {
        InputStream result = null;

        if (content.isImage()) {
            byte[] b;
            File f;

            b = getBytes(content);
            b = ImageUtil.scaleImage(b, 0, b.length, Content.THUMBNAIL_WIDTH, Content.THUMBNAIL_HEIGHT);

            f = getThumbnailTempFileHelperInstance().save(content.getFilename(), b);
            if (f != null && f.exists()) {
                result = convertByteArrToInputStream(b);
            }
        }
        return result;
    }

    public byte[] getBytes(Content content) {
        byte[] result = null;
        try {
            result = getFileBytes(content);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
        return result;
    }

    /*
    Find file content on:
    1) local disk (not uploaded yet - fast access for new assets)
    2) Google Storage
    */
    private byte[] getFileBytes(Content content) {
        byte[] result = null;
        if (content != null) {
            File tempFile = getContentTempFileHelperInstance().getFile(content);
            if (tempFile != null && tempFile.exists()) {
                result = FileUtil.readAsByteArray(tempFile);
            } else {
                result = GoogleStorageUtil.getInstance().getBytes(content);

            }
        }
        return result;
    }

    private InputStream convertByteArrToInputStream(byte[] b) {
        return new ByteArrayInputStream(b);
    }

    private ContentThumbnailTempFileHelper getThumbnailTempFileHelperInstance() {
        if (contentThumbnailTempFileHelper == null) {
            contentThumbnailTempFileHelper = new ContentThumbnailTempFileHelper(
                    Path.of(System.getProperty("user.dir"), "thumbnail_cache"));
        }
        return contentThumbnailTempFileHelper;
    }

    private ContentTempFileHelper getContentTempFileHelperInstance() {
        if (contentTempFileHelper == null) {
            contentTempFileHelper = new ContentTempFileHelper(Path.of(
                    System.getProperty("user.dir"),"content_cache"));
        }
        return contentTempFileHelper;
    }
}
