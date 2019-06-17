package codingdojo;

/**
 * Represents a content asset - an article or image
 */
public class Content {
    public static final int THUMBNAIL_WIDTH = 100;
    public static final int THUMBNAIL_HEIGHT = 140;
    private int length;
    private boolean isImage;
    private boolean isUploadedToAws;
    private boolean isUploadedToGoogle;
    private String filename;

    public Content() {
        this("filename.txt", 0, false, false, false);
    }

    public Content(String filename, int length, boolean isImage, boolean isUploadedToAws, boolean isUploadedToGoogle) {
        this.length = length;
        this.isImage = isImage;
        this.isUploadedToAws = isUploadedToAws;
        this.isUploadedToGoogle = isUploadedToGoogle;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public boolean isImage() {
        return isImage;
    }

    public boolean isUploadedToAws() {
        return isUploadedToAws;
    }

    public boolean isUploadedToGoogle() {
        return isUploadedToGoogle;
    }

    public void checkAndUpdateFileSize(int length) {
        if (this.length != length)
            this.length = length;
    }
}
