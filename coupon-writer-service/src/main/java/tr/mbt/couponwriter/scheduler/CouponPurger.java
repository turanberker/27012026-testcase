package tr.mbt.couponwriter.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tr.mbt.couponwriter.repository.CouponRepository;

@Component
@RequiredArgsConstructor
public class CouponPurger {

    private final CouponRepository couponRepository;

    @Scheduled(fixedDelay = 600000)
    public void couponPurger() {
        System.out.println("Coupon purging job running...");

        int deletedCount = couponRepository.deleteUnusedCoupons();
        System.out.printf("%s Coupon purged...", deletedCount);

    }
}
