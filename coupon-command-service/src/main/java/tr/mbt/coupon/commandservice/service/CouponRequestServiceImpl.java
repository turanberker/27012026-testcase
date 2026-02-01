package tr.mbt.coupon.commandservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.commandservice.dto.CouponRequestDto;
import tr.mbt.coupon.commandservice.dto.RedeemCouponDto;
import tr.mbt.coupon.commandservice.dto.RedeemCouponResponse;
import tr.mbt.coupon.commandservice.exception.ProcessingServiceException;
import tr.mbt.coupon.commandservice.producer.RecordProducer;
import tr.mbt.coupon.commandservice.redis.MegadealCouponCounterService;
import tr.mbt.coupon.commandservice.repository.CouponRepository;
import tr.mbt.coupon.commandservice.repository.CouponUserRepository;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.entity.CouponEntity;
import tr.mbt.coupon.coupondata.entity.CouponUserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponRequestServiceImpl implements CouponRequestService {

    private final CouponRepository couponRepository;

    private final CouponUserRepository couponUserRepository;
    private final MegadealCouponCounterService megadealCouponCounterService;

    private final RecordProducer recordProducer;

    @Override
    @Transactional
    public String requestNonMegadealCoupon(CouponRequestDto requestDto) {
        if (CouponType.MEGADEAL.equals(requestDto.getCouponType())) {
            throw new IllegalArgumentException("This method can not be called for MEGADEAL Coupon Type");
        }
        CouponType type = requestDto.getCouponType() == null ? CouponType.STANDARD : requestDto.getCouponType();

        Optional<CouponEntity> selectedCoupon = couponRepository.findAvailableCoupons(type);

        return saveCouponUSer(selectedCoupon, requestDto.getUserId());
    }

    @Override
    @Transactional
    public String requestMegadealCoupon(String userId) {
        if(!megadealCouponCounterService.tryAcquire()){
            throw new ProcessingServiceException("Total Megadeal Coupon request exceed to 10");
        }

        Optional<CouponEntity> megadealCouponOp = couponRepository.findAvailableMegadealCoupon();
        return saveCouponUSer(megadealCouponOp, userId);
    }

    private String saveCouponUSer(Optional<CouponEntity> selectedCoupon, String userId) {
        if (selectedCoupon.isEmpty()) {
            throw new ProcessingServiceException("No Available Coupon");
        } else {
            CouponUserEntity couponUser = new CouponUserEntity();
            couponUser.setUserId(userId);
            couponUser.setCoupon(selectedCoupon.get());
            couponUserRepository.save(couponUser);

            return selectedCoupon.get().getCode();
        }
    }

    @Override
    public RedeemCouponResponse redeemCoupon(RedeemCouponDto redeemCouponDto) {
        CouponUserEntity availableCoupon =
                couponUserRepository.getAvailableCoupon(redeemCouponDto.getUserId(), redeemCouponDto.getCouponCode())
                        .orElseThrow(() -> new ProcessingServiceException("Don't have coupon"));

        availableCoupon.setUsedDate(LocalDateTime.now());
        couponUserRepository.save(availableCoupon);

        CouponEntity coupon = availableCoupon.getCoupon();
        RedeemCouponResponse redeemCouponResponse = new RedeemCouponResponse();
        redeemCouponResponse.setDiscount(coupon.getDiscountAmount());
        redeemCouponResponse.setDiscountType(coupon.getDiscountType());
        return redeemCouponResponse;

    }
}
