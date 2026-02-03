package tr.mbt.coupon.loggingaop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CouponLog {

    boolean logArgs() default true;

    boolean logResult() default false;

    boolean logException() default true;
}
