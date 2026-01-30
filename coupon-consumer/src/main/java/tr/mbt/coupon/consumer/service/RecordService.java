package tr.mbt.coupon.consumer.service;

import org.springframework.validation.annotation.Validated;
import tr.mbt.coupon.coupondata.events.NewCouponRecordEvent;

@Validated
public interface RecordService {

    void saveRecord(NewCouponRecordEvent recordEvent);
}
