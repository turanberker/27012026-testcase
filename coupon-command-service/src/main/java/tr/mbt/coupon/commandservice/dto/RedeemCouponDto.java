package tr.mbt.coupon.commandservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RedeemCouponDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String couponCode;
}
