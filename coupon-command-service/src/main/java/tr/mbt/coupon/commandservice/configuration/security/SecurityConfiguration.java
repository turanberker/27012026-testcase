package tr.mbt.coupon.commandservice.configuration.security;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tr.mbt.coupon.commandservice.util.UserGetter;
import tr.mbt.coupon.security.configuration.EnableSecurityConfigurations;

@Configuration
@ConditionalOnProperty(
        prefix = "spring.security",
        name = "enabled",
        havingValue = "true"
)
@EnableSecurityConfigurations
public class SecurityConfiguration implements InitializingBean {


    @Bean
    public UserGetter userName() {
        return (UserGetter) hasUserId -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return "Anonymous";
            } else {
                return authentication.getName();
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Security is enabled. Use Basic Authentication with predefined users and passwords");
        System.out.println("UserId in requests will be ignored and gets user from Context");
    }
}
