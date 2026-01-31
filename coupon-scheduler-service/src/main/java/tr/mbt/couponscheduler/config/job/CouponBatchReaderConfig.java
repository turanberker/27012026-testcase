package tr.mbt.couponscheduler.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import tr.mbt.minioclient.FileStorageClient;

import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class CouponBatchReaderConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<CouponCsvRow> couponCsvReader(
            @Value("#{jobParameters['fileName']}") String fileName,
            FileStorageClient fileStorageClient
    ) {
        InputStream is = fileStorageClient.download(fileName);

        InputStreamResource resource = new InputStreamResource(is);

        return CouponCsvReader.reader(resource);
    }
}
