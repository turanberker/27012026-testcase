package tr.mbt.coupon.commandservice.dto;

import lombok.Data;
import tr.mbt.coupon.coupondata.data.CouponType;

@Data
public class CouponRequestDto {

    private CouponType couponType;
}
