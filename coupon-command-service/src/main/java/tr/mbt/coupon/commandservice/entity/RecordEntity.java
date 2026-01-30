package tr.mbt.coupon.commandservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tr.mbt.coupon.coupondata.entity.CouponEntity;

@Entity
@Table(name = "RECORD")
@Getter
@Setter
public class RecordEntity {

    @Id
    @Column(name = "ID")
/*Liquibase e taşındıktan sonra
CREATE SEQUENCE IF NOT EXISTS coupon.RECORDT_seq;
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "default_seq_gen"
    )
    @SequenceGenerator(
            name = "default_seq_gen",
            sequenceName = "coupon_seq",
            allocationSize = 50
    )*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "COUPON_CODE",updatable = false)
    private String couponCode;

    //FIXME Liquibase den sonra sil
    @ManyToOne(targetEntity = CouponEntity.class)
    @JoinColumn(name = "COUPON_CODE", updatable = false,insertable = false)
    private CouponEntity coupon;

    private String userId;
}
