package tr.mbt.coupon.coupondata.events;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCouponRecordEvent implements Serializable {

    @NotBlank
    private String userId;

    @NotBlank
    private String couponCode;
}
