package tr.mbt.minioclient;

import io.minio.*;

import java.io.InputStream;

public class MinioFileStorageClient implements FileStorageClient {

    private final MinioClient minioClient;

    private final String bucket;

    public MinioFileStorageClient(MinioClient minioClient,String bucket) {
        this.minioClient = minioClient;
        this.bucket=bucket;
    }

    @Override
    public String upload(

            String objectName,
            InputStream inputStream,
            long size,
            String contentType
    ) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(inputStream, size, -1)
                            .contentType(contentType)
                            .build()
            );
            return objectName;
        } catch (Exception e) {
            throw new FileStorageException("MinIO upload failed", e);
        }
    }

    @Override
    public InputStream download( String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new FileStorageException("MinIO download failed", e);
        }
    }

    @Override
    public void delete( String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new FileStorageException("MinIO delete failed", e);
        }
    }

    @Override
    public boolean exists( String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
