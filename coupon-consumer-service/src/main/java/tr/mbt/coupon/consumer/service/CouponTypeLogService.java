package tr.mbt.coupon.consumer.service;

import org.springframework.validation.annotation.Validated;
import tr.mbt.coupon.coupondata.events.CouponLogEvent;

@Validated
public interface CouponTypeLogService {

    void saveRecord(CouponLogEvent recordEvent);
}
