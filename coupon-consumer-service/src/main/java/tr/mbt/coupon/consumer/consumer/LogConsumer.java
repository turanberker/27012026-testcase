package tr.mbt.coupon.consumer.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tr.mbt.coupon.consumer.service.LogService;
import tr.mtb.coupon.logdata.LogConstants;
import tr.mtb.coupon.logdata.MethodLogEvent;

@Component
@RequiredArgsConstructor
public class LogConsumer {

    private final LogService service;

    @KafkaListener(topics = LogConstants.TOPICNAME, groupId = "coupon-group")
    public void listen(MethodLogEvent message, Acknowledgment ack) {
        try {
            System.out.println("Received: " + message);
            service.saveLog(message);
            ack.acknowledge();
        } catch (Exception e) {
            // retry / DLQ vs
        }
    }
}
