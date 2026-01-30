package tr.mbt.coupon.commandservice.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tr.mbt.coupon.coupondata.events.NewCouponRecordEvent;
import tr.mbt.coupon.coupondata.events.TopicConstants;

@Service
@RequiredArgsConstructor
public class RecordProducerImpl implements RecordProducer {

    private final KafkaTemplate<String, NewCouponRecordEvent> kafkaTemplate;

    @Override
    public void send(NewCouponRecordEvent newCouponRecordEvent) {
        kafkaTemplate.send(TopicConstants.TOPIC_NAME,newCouponRecordEvent.getCouponCode(),newCouponRecordEvent);
    }
}
