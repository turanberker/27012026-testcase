package tr.mbt.coupon.processingservice.configuration;

import org.springframework.context.annotation.Configuration;
import tr.mbt.coupon.uploadedfile.configuration.EnableUploadedFileDataConfiguration;
import tr.mbt.minioclient.configuration.EnableMinioConfiguration;

@Configuration
@EnableUploadedFileDataConfiguration
@EnableMinioConfiguration
public class CommandServiceConfiguration {
}
