package tr.mbt.coupon.coupondata.entity;

import jakarta.persistence.*;
import lombok.*;
import tr.mbt.coupon.coupondata.data.CouponType;

@Entity
@Table(name = "coupon_type_log")
@Getter
@Setter
@NoArgsConstructor
public class CouponTypeLogEntity {

    @Id
    @Column(name = "COUPON_TYPE", nullable = false,updatable = false)
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Column(name = "CREATED_COUNT", nullable = false)
    private Long createdCount=0L;

    @Column(name = "USED_COUNT", nullable = false)
    private Long usedCount=0L;

    public CouponTypeLogEntity(CouponType couponType) {
        this.couponType = couponType;
    }
}
