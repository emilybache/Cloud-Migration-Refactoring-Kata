package codingdojo;

import java.io.File;
import java.nio.file.Path;

public class ContentTempFileHelper {
    Path tempDir;

    public ContentTempFileHelper() {
        this(Path.of(System.getProperty("user.dir"),"content_cache"));
    }

    public ContentTempFileHelper(Path tempDir) {
        this.tempDir = tempDir;
    }

    public File getFile(Content content) {
        File result = null;
        if (content.getFilename() != null) {
            result = tempDir.resolve(content.getFilename()).toFile();
        }
        return result;
    }
}
