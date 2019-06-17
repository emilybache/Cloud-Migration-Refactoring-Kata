package codingdojo;


public class AwsUtil {

    private static AwsUtil instance;
    private AwsUtil() {}

    public static AwsUtil getInstance() {
        if (instance == null) {
            instance = new AwsUtil();
        }
        return instance;
    }

    public byte[] getBytes(Content content) {
        throw new RuntimeException("This method cannot be unit tested, it goes over the network");
    }

    public void upload(Content content, byte[] result) {
        throw new RuntimeException("This method cannot be unit tested, it goes over the network");
    }
}
