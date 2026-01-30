package tr.mbt.coupon.commandservice.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import tr.mbt.coupon.coupondata.entity.RecordEntity;
import tr.mbt.coupon.commandservice.repository.CouponRepository;
import tr.mbt.coupon.commandservice.redis.RecordDocumentRepository;
import tr.mbt.coupon.coupondata.entity.CouponEntity;
import tr.mbt.coupon.coupondata.entity.UploadedFileEntity;
import tr.mbt.coupon.uploadedfile.repository.UploadedFileRepository;
import tr.mbt.minioclient.configuration.EnableMinioConfiguration;

@Configuration
@EntityScan(basePackageClasses = {UploadedFileEntity.class, CouponEntity.class,RecordEntity.class})
@EnableJpaRepositories(basePackageClasses = {UploadedFileRepository.class, CouponRepository.class})
@EnableRedisRepositories(basePackageClasses = {RecordDocumentRepository.class})

@EnableMinioConfiguration
@EnableCaching
public class CommandServiceConfiguration {
}
