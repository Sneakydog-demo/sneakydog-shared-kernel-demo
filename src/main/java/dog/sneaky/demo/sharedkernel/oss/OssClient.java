package dog.sneaky.demo.sharedkernel.oss;

import java.io.IOException;
import java.io.InputStream;

public interface OssClient {
    void putObject(InputStream inputStream, String objectName) throws IOException;
}
