package tr.mbt.minioclient.listener;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

 @RequiredArgsConstructor
public class BucketCreateListener {

    private final Logger LOGGER= LoggerFactory.getLogger(BucketCreateListener.class);
    private final MinioClient minioClient;

    private final String bucketName;



    @EventListener(ApplicationReadyEvent.class)
    public void createBucket() {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );

            if (!exists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
                LOGGER.info("MinIO bucket created: {}", bucketName);
            } else {
                LOGGER.info("MinIO bucket already exists: {}", bucketName);
            }

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Could not create/check MinIO bucket: " + bucketName, e
            );
        }
    }
}
