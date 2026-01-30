package tr.mbt.couponwriter.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import tr.mbt.coupon.uploadedfile.configuration.EnableUploadedFileDataConfiguration;
import tr.mbt.couponwriter.config.job.CouponCsvRow;
import tr.mbt.couponwriter.config.job.CouponItemProcessor;
import tr.mbt.couponwriter.config.job.CouponJobListener;
import tr.mbt.couponwriter.config.job.CouponJpaWriter;
import tr.mbt.coupon.coupondata.entity.CouponEntity;
import tr.mbt.minioclient.configuration.EnableMinioConfiguration;

@EnableScheduling
@EnableMinioConfiguration
@EnableBatchProcessing
@EnableUploadedFileDataConfiguration
@EntityScan(basePackageClasses = CouponEntity.class)
@Configuration
public class CouponWriterConfigurations {

    @Bean
    public Job couponImportJob(
            JobRepository jobRepository,
            Step couponImportStep,
            CouponJobListener couponJobListener
    ) {
        return new JobBuilder("couponImportJob", jobRepository)
                .start(couponImportStep)
                .listener(couponJobListener)
                .build();
    }

    @Bean
    public Step couponImportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<CouponCsvRow> couponCsvReader,
            EntityManagerFactory emf
    ) {
        return new StepBuilder("couponImportStep", jobRepository)
                .<CouponCsvRow, CouponEntity>chunk(1, transactionManager)
                .reader(couponCsvReader)
                .processor(new CouponItemProcessor())
                .writer(CouponJpaWriter.writer(emf))
                .build();
    }
}
