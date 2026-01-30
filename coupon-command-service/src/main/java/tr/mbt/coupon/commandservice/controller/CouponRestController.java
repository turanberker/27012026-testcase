package tr.mbt.coupon.commandservice.controller;

import com.mbt.servicecommon.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tr.mbt.coupon.commandservice.service.FileUploadService;


@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponRestController {

    private final FileUploadService fileUploadService;

    @PostMapping(path = "/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<BaseResponse<String>> saveFile(@RequestPart("file") MultipartFile file) {

        String uploadedFileName = fileUploadService.upload(file);
        BaseResponse<String> success = BaseResponse.success(uploadedFileName);
        return ResponseEntity.ok(success);
    }
}
