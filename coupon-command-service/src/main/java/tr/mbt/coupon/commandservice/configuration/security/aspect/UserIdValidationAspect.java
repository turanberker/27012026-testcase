package tr.mbt.coupon.commandservice.configuration.security.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tr.mbt.coupon.commandservice.dto.HasUserId;
import tr.mbt.coupon.commandservice.exception.ProcessingServiceException;

@Aspect
@Component
@ConditionalOnProperty(prefix = "spring.security",
        name = "enabled",
        havingValue = "false")
public class UserIdValidationAspect {

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void validateUserId(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof HasUserId hasUserId) {
                if (hasUserId.getUserId() == null) {
                    throw new ProcessingServiceException("UserId cannot be null when security is disabled");
                }

            }
        }
    }
}
