package tr.mbt.coupon.commandservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RedeemCouponDto  implements HasUserId{

     private String userId;

    @NotBlank
    private String couponCode;
}
