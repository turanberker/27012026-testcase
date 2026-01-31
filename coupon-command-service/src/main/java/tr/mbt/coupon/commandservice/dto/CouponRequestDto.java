package tr.mbt.coupon.commandservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import tr.mbt.coupon.coupondata.data.CouponType;

@Data
public class CouponRequestDto {

    @NotBlank
    private String userId;

    private CouponType couponType;
}
