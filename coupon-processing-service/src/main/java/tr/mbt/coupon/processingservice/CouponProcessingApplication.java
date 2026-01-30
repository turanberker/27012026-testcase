package tr.mbt.coupon.processingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tr.mbt.minioclient.configuration.EnableMinioConfiguration;

@SpringBootApplication
@EnableMinioConfiguration
public class CouponProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponProcessingApplication.class, args);
    }
}
