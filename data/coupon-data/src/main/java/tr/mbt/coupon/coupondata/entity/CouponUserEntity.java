package tr.mbt.coupon.coupondata.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "COUPON_USER")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CouponUserEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "default_seq_gen"
    )
    @SequenceGenerator(
            name = "default_seq_gen",
            sequenceName = "coupon_user_seq",
            allocationSize = 50
    )
    private Long id;


    @NotNull
    @ManyToOne(targetEntity = CouponEntity.class)
    @JoinColumn(name = "COUPON_CODE", updatable = false)
    private CouponEntity coupon;

    @NotBlank
    @Column(name = "USER_ID", updatable = false)
    private String userId;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "USED_DATE", insertable = false)
    private LocalDateTime usedDate;
}
