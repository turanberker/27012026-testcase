package tr.mbt.couponwriter.entity;

import jakarta.persistence.*;
import lombok.*;
import tr.mbt.couponwriter.data.CouponType;
import tr.mbt.couponwriter.data.DiscountType;

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

    @Column(name = "COUPON_TYPE",nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType couponType;


    @Column(name = "DISCONT_TYPE",nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name  ="DISCOUNT_AMOUNT",nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "MAX_USAGE",nullable = false)
    private Integer maxUsages;



}
