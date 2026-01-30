package tr.mbt.coupon.commandservice.service;

public interface RecordService {

    Long countByCouponCode(String couponCode);

    void increaseTotalUsage(String couponCode);
}
