package tr.mbt.minioclient.configuration;

import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.mbt.minioclient.FileStorageClient;
import tr.mbt.minioclient.MinioFileStorageClient;
import tr.mbt.minioclient.listener.BucketCreateListener;

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MinioClient minioClient(MinioProperties props) {
        return MinioClient.builder()
                .endpoint(props.getEndpoint())
                .credentials(props.getAccessKey(), props.getSecretKey())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public FileStorageClient fileStorageClient(MinioClient minioClient, MinioProperties props) {
        return new MinioFileStorageClient(minioClient, props.getBucketName());
    }

    @Bean
    @ConditionalOnMissingBean
    public BucketCreateListener buckerCreateListener(MinioClient minioClient, MinioProperties props) {
        return new BucketCreateListener(minioClient, props.getBucketName());
    }
}
