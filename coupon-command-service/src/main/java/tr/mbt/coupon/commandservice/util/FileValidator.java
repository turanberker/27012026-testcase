package tr.mbt.coupon.commandservice.util;

import org.springframework.web.multipart.MultipartFile;

public interface FileValidator {

    void validate (MultipartFile file);
}
