package tr.mbt.coupon.consumer.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import tr.mbt.coupon.consumer.entity.LogEntity;
import tr.mbt.coupon.coupondata.entity.CouponUserEntity;
import tr.mbt.coupon.loggingaop.EnableLoggingConfig;

@Configuration
@EnableKafka
@EntityScan(basePackageClasses = {CouponUserEntity.class, LogEntity.class})
@EnableJpaAuditing
@EnableLoggingConfig
public class ConsumerConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
}
