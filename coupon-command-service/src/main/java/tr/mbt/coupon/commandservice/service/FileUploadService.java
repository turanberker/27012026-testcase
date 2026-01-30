package tr.mbt.coupon.commandservice.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String upload(@NotNull MultipartFile file);
}
