package tr.mbt.coupon.commandservice.configuration.security;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import tr.mbt.coupon.commandservice.util.UserGetter;


@Configuration
@ConditionalOnProperty(
        prefix = "spring.security",
        name = "enabled",
        havingValue = "false"
)
public class DisabledSecurityConfiguration implements InitializingBean {

    @Bean
    public SecurityFilterChain disabledSecurity(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public UserGetter userName() {
        return (UserGetter) hasUserId -> {
            if (hasUserId == null || Strings.isBlank(hasUserId.getUserId())) {
                return "Anonymous";
            } else {
                return hasUserId.getUserId();
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Security is disabled. Add userId to the required Requests");
    }
}
