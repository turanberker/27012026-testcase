package tr.mbt.coupon.consumer.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import tr.mbt.coupon.coupondata.entity.CouponUserEntity;

@Configuration
@EnableKafka
@EntityScan(basePackageClasses = CouponUserEntity.class)
@EnableJpaAuditing
public class ConsumerConfiguration {


}
