package tr.mbt.coupon.loggingaop.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import tr.mtb.coupon.logdata.LogConstants;
import tr.mtb.coupon.logdata.MethodLogEvent;

@RequiredArgsConstructor
public class KafkaLogProducer {

    private final KafkaTemplate<String, MethodLogEvent> kafkaTemplate;

    public void send(MethodLogEvent event) {
        kafkaTemplate.send(
                LogConstants.TOPICNAME,
                event
        );
    }
}
