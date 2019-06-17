package codingdojo;

public class GoogleStorageUtil {
    private static GoogleStorageUtil instance;

    private GoogleStorageUtil() {}

    public static GoogleStorageUtil getInstance() {
        if (instance == null) {
            instance = new GoogleStorageUtil();
        }
        return instance;
    }

    public byte[] getBytes(Content content) {
        throw new RuntimeException("This method cannot be unit tested, it goes over the network");
    }
}
