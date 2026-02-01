package tr.mbt.coupon.commandservice.dto;

import lombok.Data;
import tr.mbt.coupon.coupondata.data.DiscountType;

import java.math.BigDecimal;

@Data
public class RedeemCouponResponse {

    private BigDecimal discount;

    private DiscountType discountType;
}
