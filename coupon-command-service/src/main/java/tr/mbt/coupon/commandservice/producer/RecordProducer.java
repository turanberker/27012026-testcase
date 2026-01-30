package tr.mbt.coupon.commandservice.producer;

import tr.mbt.coupon.coupondata.events.NewCouponRecordEvent;

public interface RecordProducer {

    void send(NewCouponRecordEvent newCouponRecordEvent);
}
