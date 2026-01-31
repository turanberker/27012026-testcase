package tr.mbt.couponscheduler.config.job;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.InputStream;

public class MinioResourceFactory {

    public static Resource from(InputStream inputStream) {
        return new InputStreamResource(inputStream);
    }
}
