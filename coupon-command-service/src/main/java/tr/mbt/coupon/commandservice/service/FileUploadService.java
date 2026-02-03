package tr.mbt.coupon.commandservice.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import tr.mbt.coupon.loggingaop.CouponLog;

public interface FileUploadService {

    String upload(@NotNull MultipartFile file);
}
