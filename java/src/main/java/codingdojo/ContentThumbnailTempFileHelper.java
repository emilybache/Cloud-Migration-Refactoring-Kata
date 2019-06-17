package codingdojo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContentThumbnailTempFileHelper {
    private static Logger logger = Logger.getLogger("codingdojo.ContentThumbnailTempFileHelper");
    private final Path thumbnailTempPath;

    public ContentThumbnailTempFileHelper() {
        this(Path.of(System.getProperty("user.dir"), "thumbnail_cache"));
    }
    public ContentThumbnailTempFileHelper(Path thumbnailTempPath) {
        this.thumbnailTempPath = thumbnailTempPath;
    }

    public File save(String filename, byte[] content) {
        File file = thumbnailTempPath.resolve(filename).toFile();
        try (FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(content);
        } catch (IOException e) {
            logger.log(Level.WARNING, "failed to save thumbnail to filename " + filename, e);
        }
        return file;
    }
}
