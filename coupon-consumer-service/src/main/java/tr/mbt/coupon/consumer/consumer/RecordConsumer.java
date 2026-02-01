package tr.mbt.coupon.consumer.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tr.mbt.coupon.consumer.service.CouponTypeLogService;
import tr.mbt.coupon.coupondata.events.CouponLogEvent;
import tr.mbt.coupon.coupondata.events.TopicConstants;

@Component
@RequiredArgsConstructor
public class RecordConsumer {

    private final CouponTypeLogService service;

    @KafkaListener(topics = TopicConstants.TOPIC_NAME, groupId = "coupon-group")
    public void listen(CouponLogEvent message, Acknowledgment ack) {
        try {
            System.out.println("Received: " + message);
            service.saveRecord(message);

            ack.acknowledge();
        } catch (Exception e) {
            // retry / DLQ vs
        }
    }
}
