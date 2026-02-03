package tr.mbt.coupon.loggingaop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;
import tr.mbt.coupon.loggingaop.producer.KafkaLogProducer;
import tr.mtb.coupon.logdata.MethodLogEvent;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@RequiredArgsConstructor
public class LogAspect {

    private final KafkaLogProducer producer;


    @Around("@annotation(couponLog)")
    public Object logToKafka(ProceedingJoinPoint pjp, CouponLog couponLog) throws Throwable {

        long start = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        MethodLogEvent.MethodLogEventBuilder eventBuilder =
                MethodLogEvent.builder()
                        .serviceName(signature.getMethod().getName())
                        .className(method.getDeclaringClass().getName())
                        .methodName(method.getName())
                        .date(LocalDateTime.now());

        if (couponLog.logArgs()) {
            Object[] args = pjp.getArgs();

            Object[] safeArgs = Arrays.stream(args)
                    .map(arg -> {
                        if (arg instanceof MultipartFile file) {
                            return file.getOriginalFilename();  // sadece filename
                        } else {
                            return arg;
                        }
                    })
                    .toArray();

            eventBuilder.arguments(safeArgs);
        }

        try {
            Object result = pjp.proceed();

            long duration = System.currentTimeMillis() - start;

            eventBuilder
                    .success(true)
                    .durationMs(duration);

            if (couponLog.logResult()) {
                eventBuilder.result(result);
            }

            producer.send(eventBuilder.build());

            return result;

        } catch (Throwable ex) {

            long duration = System.currentTimeMillis() - start;

            eventBuilder
                    .success(false)
                    .durationMs(duration);

            if (couponLog.logException()) {
                eventBuilder.exceptionMessage(ex.getMessage());
            }

            producer.send(eventBuilder.build());

            throw ex;
        }
    }
}
