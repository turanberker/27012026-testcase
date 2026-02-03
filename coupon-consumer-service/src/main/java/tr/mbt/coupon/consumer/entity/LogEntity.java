package tr.mbt.coupon.consumer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "log"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LogEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "default_seq_gen"
    )
    @SequenceGenerator(
            name = "default_seq_gen",
            sequenceName = "log_seq",
            allocationSize = 50
    )
    private Long id;


    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "method_name", nullable = false)
    private String methodName;

    /**
     * Method arguments as JSON
     */

    @Column(name = "arguments_json", columnDefinition = "TEXT")
    private String argumentsJson;

    /**
     * Method result as JSON
     */

    @Column(name = "result_json", columnDefinition = "TEXT")
    private String resultJson;

    @Column(name = "duration_ms")
    private long durationMs;

    @Column(name = "success")
    private boolean success;

    @Column(name = "exception_message", columnDefinition = "TEXT")
    private String exceptionMessage;

    @Column(name = "log_date", nullable = false)
    private LocalDateTime date;

}
