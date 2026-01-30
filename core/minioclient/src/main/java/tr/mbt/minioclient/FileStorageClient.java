package tr.mbt.minioclient;

import java.io.InputStream;

public interface FileStorageClient {

    String upload(
            String objectName,
            InputStream inputStream,
            long size,
            String contentType
    );

    InputStream download(String objectName);

    void delete(String objectName);

    boolean exists(String objectName);
}
