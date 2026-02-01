package tr.mbt.coupon.coupondata.events;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.mbt.coupon.coupondata.data.CouponType;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponLogEvent implements Serializable {

    @NotBlank
    private CouponType couponType;

    @NotBlank
    private EventType type;

    public enum EventType {
        USED,
        ALLOCATED
    }
}
