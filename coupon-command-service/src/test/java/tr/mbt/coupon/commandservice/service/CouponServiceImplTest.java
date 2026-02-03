package tr.mbt.coupon.commandservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTest {

    @InjectMocks
    private CouponServiceImpl couponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponUserRepository couponUserRepository;

    @Mock
    private MegadealCouponCounterService megadealCouponCounterService;

    @Mock
    private RecordProducer recordProducer;

    @Mock
    private UserGetter userGetter;

    /* ---------------- NON MEGADEAL ---------------- */

    @Test
    void requestNonMegadealCoupon_success() {
        CouponRequestDto dto = new CouponRequestDto();
        dto.setCouponType(CouponType.STANDARD);
        final String couponCode = "CODE123";
        CouponEntity coupon = new CouponEntity();
        coupon.setCode(couponCode);
        coupon.setCouponType(CouponType.STANDARD);

        when(couponRepository.findAvailableCoupons(CouponType.STANDARD))
                .thenReturn(Optional.of(coupon));
        when(userGetter.getUserId(dto)).thenReturn("user1");

        String code = couponService.requestNonMegadealCoupon(dto);

        assertEquals(couponCode, code);
        verify(couponUserRepository).save(any(CouponUserEntity.class));
        verify(recordProducer).send(any(CouponLogEvent.class));
    }

    @Test
    void requestNonMegadealCoupon_noCoupon_throwException() {
        CouponRequestDto dto = new CouponRequestDto();
        dto.setCouponType(CouponType.STANDARD);

        when(couponRepository.findAvailableCoupons(CouponType.STANDARD))
                .thenReturn(Optional.empty());

        assertThrows(
                ProcessingServiceException.class,
                () -> couponService.requestNonMegadealCoupon(dto)
        );
    }

    /* ---------------- MEGADEAL ---------------- */

    @Test
    void requestMegadealCoupon_limitExceeded_throwException() {
        HasUserId hasUserId = mock(HasUserId.class);

        when(megadealCouponCounterService.tryAcquire()).thenReturn(false);

        assertThrows(
                ProcessingServiceException.class,
                () -> couponService.requestMegadealCoupon(hasUserId)
        );
    }

    @Test
    void requestMegadealCoupon_success() {
        HasUserId hasUserId = mock(HasUserId.class);
        final String couponCode = "MEGA123";
        CouponEntity coupon = new CouponEntity();
        coupon.setCode(couponCode);
        coupon.setCouponType(CouponType.MEGADEAL);

        when(megadealCouponCounterService.tryAcquire()).thenReturn(true);
        when(userGetter.getUserId(hasUserId)).thenReturn("user1");
        when(couponRepository.findAvailableMegadealCoupon())
                .thenReturn(Optional.of(coupon));

        String code = couponService.requestMegadealCoupon(hasUserId);

        assertEquals(couponCode, code);
        verify(couponUserRepository).save(any(CouponUserEntity.class));
        verify(recordProducer).send(any(CouponLogEvent.class));
    }

    /* ---------------- REDEEM ---------------- */

    @Test
    void redeemCoupon_success() {
        RedeemCouponDto dto = new RedeemCouponDto();
        dto.setCouponCode("CODE123");
        BigDecimal discountAmount = new BigDecimal(100);
        CouponEntity coupon = new CouponEntity();
        coupon.setDiscountAmount(discountAmount);
        coupon.setCouponType(CouponType.STANDARD);
        coupon.setMaxUsages(1L);

        CouponUserEntity couponUser = new CouponUserEntity();
        couponUser.setCoupon(coupon);

        when(userGetter.getUserId(dto)).thenReturn("user1");
        when(couponUserRepository.getAvailableCoupon("user1", "CODE123"))
                .thenReturn(Optional.of(couponUser));

        RedeemCouponResponse response = couponService.redeemCoupon(dto);

        assertEquals(discountAmount, response.getDiscount());
        verify(couponUserRepository).save(couponUser);
        verify(couponRepository).save(coupon);
        verify(recordProducer).send(any(CouponLogEvent.class));
    }

    @Test
    void redeemCoupon_noCoupon_throwException() {
        RedeemCouponDto dto = new RedeemCouponDto();
        dto.setCouponCode("INVALID");

        when(userGetter.getUserId(dto)).thenReturn("user1");
        when(couponUserRepository.getAvailableCoupon("user1", "INVALID"))
                .thenReturn(Optional.empty());

        assertThrows(
                ProcessingServiceException.class,
                () -> couponService.redeemCoupon(dto)
        );
    }
}
