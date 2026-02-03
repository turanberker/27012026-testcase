package tr.mbt.couponscheduler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_error", schema = "coupon")
@Data
public class CouponErrorEntity {

    @Id

    @Column(name = "ID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "default_seq_gen"
    )
    @SequenceGenerator(
            name = "default_seq_gen",
            sequenceName = "coupon_error_seq",
            allocationSize = 50
    )
    private Long id;

    @Column(name = "RAW_LINE",columnDefinition = "text")
    private String rawLine;

    @Column(name = "ERROR_MESSAGE",columnDefinition = "text")
    private String errorMessage;

    @NotBlank
    @Column(name = "FILE_NAME")
    private String filaname;

    private LocalDateTime createdAt = LocalDateTime.now();
}
