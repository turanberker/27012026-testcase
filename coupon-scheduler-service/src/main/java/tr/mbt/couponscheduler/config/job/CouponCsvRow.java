package tr.mbt.couponscheduler.config.job;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CouponCsvRow {

    private String code;
    private String type;
    private String discountType;
    private String discountAmount;
    private String expiryDate;
    private String maxUsages;
}
