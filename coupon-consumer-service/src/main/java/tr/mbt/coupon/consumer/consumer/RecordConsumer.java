package tr.mbt.coupon.consumer.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tr.mbt.coupon.consumer.service.RecordService;
import tr.mbt.coupon.coupondata.events.NewCouponRecordEvent;
import tr.mbt.coupon.coupondata.events.TopicConstants;

@Component
@RequiredArgsConstructor
public class RecordConsumer {

    private final RecordService recordService;

    @KafkaListener(topics = TopicConstants.TOPIC_NAME, groupId = "coupon-group")
    public void listen(NewCouponRecordEvent message, Acknowledgment ack) {
        try {
            System.out.println("Received: " + message);
            recordService.saveRecord(message);

            ack.acknowledge();
        } catch (Exception e) {
            // retry / DLQ vs
        }
    }
}
