package tr.mbt.couponscheduler.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tr.mbt.coupon.loggingaop.CouponLog;
import tr.mbt.couponscheduler.repository.CouponUserRepositoy;

@Component
@RequiredArgsConstructor
public class CouponPurger {

    private final CouponUserRepositoy recordRepositoy;

    @CouponLog(logArgs = true, logResult = true,logException = true)
    @Scheduled(fixedDelay = 600000)
    public void couponPurger() {
        System.out.println("Record purging job running...");

        int deletedCount = recordRepositoy.deleteUnusedCoupons();
        System.out.printf("%s Records purged...", deletedCount);

    }
}
