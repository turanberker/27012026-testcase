package tr.mbt.coupon.security.configuration;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@ComponentScan(basePackageClasses = SecurityAutoConfiguration.class)
public @interface EnableSecurityConfigurations {
}
