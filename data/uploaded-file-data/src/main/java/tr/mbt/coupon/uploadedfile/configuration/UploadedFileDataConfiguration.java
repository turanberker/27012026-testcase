package tr.mbt.coupon.uploadedfile.configuration;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tr.mbt.coupon.uploadedfile.entity.UploadedFileEntity;
import tr.mbt.coupon.uploadedfile.repository.UploadedFileRepository;

@Configuration
@EntityScan(basePackageClasses = UploadedFileEntity.class)
@EnableJpaRepositories(basePackageClasses = UploadedFileRepository.class)
public class UploadedFileDataConfiguration {
}
