package tr.mtb.coupon.logdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodLogEvent {

    private String serviceName;
    private String className;
    private String methodName;

    private Object[] arguments;
    private Object result;

    private long durationMs;

    private boolean success;
    private String exceptionMessage;

    private LocalDateTime date;

}
