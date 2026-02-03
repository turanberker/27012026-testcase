package tr.mbt.coupon.loggingaop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.core.KafkaTemplate;
import tr.mbt.coupon.loggingaop.producer.KafkaLogProducer;
import tr.mtb.coupon.logdata.MethodLogEvent;

@EnableAspectJAutoProxy
public class LoggingAopAutoConfiguration {

    @Bean
    public KafkaLogProducer kafkaLogProducer(
            KafkaTemplate<String, MethodLogEvent> kafkaTemplate
            ) {
        return new KafkaLogProducer(kafkaTemplate);
    }

    @Bean
    public LogAspect kafkaLogAspect(
            KafkaLogProducer producer) {
        return new LogAspect(producer);
    }
}
