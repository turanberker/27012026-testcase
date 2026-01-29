package tr.mbt.couponwriter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/coupon")
public class CouponRestController {

    public ResponseEntity<BaseResponse<Void>> saveFile(@RequestPart("file") MultipartFile file) {
        return null;
    }
}
