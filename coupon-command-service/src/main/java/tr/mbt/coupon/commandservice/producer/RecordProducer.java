package tr.mbt.coupon.commandservice.producer;

import tr.mbt.coupon.coupondata.events.CouponLogEvent;

public interface RecordProducer {

    void send(CouponLogEvent newCouponRecordEvent);
}
