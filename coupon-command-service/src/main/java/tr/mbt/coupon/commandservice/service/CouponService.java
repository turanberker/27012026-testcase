package tr.mbt.coupon.commandservice.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import tr.mbt.coupon.commandservice.dto.CouponRequestDto;
import tr.mbt.coupon.commandservice.dto.RedeemCouponDto;
import tr.mbt.coupon.commandservice.dto.RedeemCouponResponse;

@Validated
public interface CouponService {

    //FIXME After security remove userId
    String requestNonMegadealCoupon(@Valid CouponRequestDto requestDto);

    String requestMegadealCoupon();

    RedeemCouponResponse redeemCoupon(RedeemCouponDto redeemCouponDto);
}
