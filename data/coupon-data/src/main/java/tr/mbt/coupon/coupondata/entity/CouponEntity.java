package tr.mbt.coupon.coupondata.entity;

import jakarta.persistence.*;
import lombok.*;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.data.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CouponEntity {

    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "COUPON_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Column(name = "DISCONT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "DISCOUNT_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "MAX_USAGE", nullable = false)
    private Long maxUsages;

    @Column(name = "TOTAL_USED_COUNT", nullable = false,columnDefinition = "integer default 0")
    private Integer totalUsedCount = 0;

    @Column(name = "AVAILABLE")
    private Boolean available;

    @PrePersist
    protected void prePersist() {
        if (maxUsages > totalUsedCount && expiryDate.isBefore(LocalDate.now())) {
            available = Boolean.TRUE;
        } else {
            available = Boolean.FALSE;
        }
    }
}
