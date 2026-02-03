package tr.mbt.coupon.commandservice.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import tr.mbt.coupon.commandservice.redis.MegadealCounterRepository;
import tr.mbt.coupon.coupondata.entity.CouponEntity;
import tr.mbt.coupon.loggingaop.EnableLoggingConfig;
import tr.mbt.minioclient.configuration.EnableMinioConfiguration;

@Configuration
@EntityScan(basePackageClasses = {CouponEntity.class})
@EnableRedisRepositories(basePackageClasses = {MegadealCounterRepository.class})
@EnableMinioConfiguration
@EnableCaching
@EnableJpaAuditing
@EnableLoggingConfig
public class CommandServiceConfiguration {


}
