package tr.mbt.coupon.commandservice.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tr.mbt.coupon.coupondata.events.CouponLogEvent;
import tr.mbt.coupon.coupondata.events.TopicConstants;

@Service
@RequiredArgsConstructor
public class RecordProducerImpl implements RecordProducer {

    private final KafkaTemplate<String, CouponLogEvent> kafkaTemplate;

    @Override
    public void send(CouponLogEvent newCouponRecordEvent) {
        kafkaTemplate.send(TopicConstants.TOPIC_NAME, newCouponRecordEvent.getCouponType().name(), newCouponRecordEvent);
    }
}
