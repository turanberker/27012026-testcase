package tr.mbt.coupon.commandservice.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import tr.mbt.coupon.coupondata.data.CouponType;

@Validated
public interface CouponRequestService {

    //FIXME After security remove userId
    String request(CouponType type, @NotBlank String userId);
}
