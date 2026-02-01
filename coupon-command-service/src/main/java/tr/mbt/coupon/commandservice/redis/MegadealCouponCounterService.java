package tr.mbt.coupon.commandservice.redis;

public interface MegadealCouponCounterService {

    boolean tryAcquire();
}
