package tr.mbt.couponwriter.config.job;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CouponCsvRow {

    private String code;
    private String type;
    private String discountType;
    private BigDecimal discountAmount;
    private LocalDate expiryDate;
    private Integer maxUsages;
}
