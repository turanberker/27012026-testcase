package tr.mbt.coupon.commandservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.commandservice.dto.CouponRequestDto;
import tr.mbt.coupon.commandservice.dto.HasUserId;
import tr.mbt.coupon.commandservice.dto.RedeemCouponDto;
import tr.mbt.coupon.commandservice.dto.RedeemCouponResponse;
import tr.mbt.coupon.commandservice.exception.ProcessingServiceException;
import tr.mbt.coupon.commandservice.producer.RecordProducer;
import tr.mbt.coupon.commandservice.redis.MegadealCouponCounterService;
import tr.mbt.coupon.commandservice.repository.CouponRepository;
import tr.mbt.coupon.commandservice.repository.CouponUserRepository;
import tr.mbt.coupon.commandservice.util.UserGetter;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.entity.CouponEntity;
import tr.mbt.coupon.coupondata.entity.CouponUserEntity;
import tr.mbt.coupon.coupondata.events.CouponLogEvent;
import tr.mbt.coupon.loggingaop.CouponLog;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    private final CouponUserRepository couponUserRepository;
    private final MegadealCouponCounterService megadealCouponCounterService;

    private final RecordProducer recordProducer;
    private final UserGetter userGetter;

    @Override
    @Transactional
    @CouponLog(logArgs = true, logResult = true, logException = true)
    public String requestNonMegadealCoupon(CouponRequestDto requestDto) {
        if (CouponType.MEGADEAL.equals(requestDto.getCouponType())) {
            throw new IllegalArgumentException("This method can not be called for MEGADEAL Coupon Type");
        }
        CouponType type = requestDto.getCouponType() == null ? CouponType.STANDARD : requestDto.getCouponType();

        Optional<CouponEntity> selectedCoupon = couponRepository.findAvailableCoupons(type);
        String userName = userGetter.getUserId(requestDto);
        return saveCouponUSer(selectedCoupon, userName);
    }

    @Override
    @Transactional
    @CouponLog(logArgs = true, logResult = true, logException = true)
    public String requestMegadealCoupon(HasUserId hasUserId) {
        if (!megadealCouponCounterService.tryAcquire()) {
            throw new ProcessingServiceException("Total Megadeal Coupon request exceed to 10");
        }
        String userName = userGetter.getUserId(hasUserId);


        Optional<CouponEntity> megadealCouponOp = couponRepository.findAvailableMegadealCoupon();
        return saveCouponUSer(megadealCouponOp, userName);
    }

    private String saveCouponUSer(Optional<CouponEntity> selectedCoupon, String userName) {
        if (selectedCoupon.isEmpty()) {
            throw new ProcessingServiceException("No Available Coupon");
        } else {
            CouponUserEntity couponUser = new CouponUserEntity();
            couponUser.setUserId(userName);
            couponUser.setCoupon(selectedCoupon.get());
            couponUserRepository.save(couponUser);

            String code = selectedCoupon.get().getCode();

            recordProducer.send(new CouponLogEvent(selectedCoupon.get().getCouponType(), CouponLogEvent.EventType.ALLOCATED));
            return code;
        }
    }

    @Override
    @Transactional
    @CouponLog(logArgs = true, logResult = true, logException = true)
    public RedeemCouponResponse redeemCoupon(RedeemCouponDto redeemCouponDto) {

        String userName = userGetter.getUserId(redeemCouponDto);
        CouponUserEntity availableCoupon =
                couponUserRepository.getAvailableCoupon(userName, redeemCouponDto.getCouponCode())
                        .orElseThrow(() -> new ProcessingServiceException("Don't have coupon"));

        availableCoupon.setUsedDate(LocalDateTime.now());
        couponUserRepository.save(availableCoupon);

        CouponEntity coupon = availableCoupon.getCoupon();
        coupon.setMaxUsages(coupon.getMaxUsages() + 1);
        couponRepository.save(coupon);

        recordProducer.send(new CouponLogEvent(availableCoupon.getCoupon().getCouponType(), CouponLogEvent.EventType.USED));

        RedeemCouponResponse redeemCouponResponse = new RedeemCouponResponse();
        redeemCouponResponse.setDiscount(coupon.getDiscountAmount());
        redeemCouponResponse.setDiscountType(coupon.getDiscountType());
        return redeemCouponResponse;

    }
}
