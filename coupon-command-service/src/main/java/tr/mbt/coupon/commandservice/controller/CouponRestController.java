package tr.mbt.coupon.commandservice.controller;

import com.mbt.servicecommon.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tr.mbt.coupon.commandservice.dto.CouponRequestDto;
import tr.mbt.coupon.commandservice.dto.RedeemCouponDto;
import tr.mbt.coupon.commandservice.dto.RedeemCouponResponse;
import tr.mbt.coupon.commandservice.service.CouponService;
import tr.mbt.coupon.commandservice.service.FileUploadService;
import tr.mbt.coupon.coupondata.data.CouponType;


@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponRestController {

    private final FileUploadService fileUploadService;
    private final CouponService service;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<String>> saveFile(@RequestPart("file") MultipartFile file) {

        String uploadedFileName = fileUploadService.upload(file);
        BaseResponse<String> success = BaseResponse.success(uploadedFileName);
        return ResponseEntity.ok(success);
    }


    //@PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/request", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> request(@RequestBody @Valid CouponRequestDto requestDto) {

        String cuponCode = null;
        if (CouponType.MEGADEAL.equals(requestDto.getCouponType())) {
            cuponCode = service.requestMegadealCoupon(requestDto);
        } else {
            cuponCode = service.requestNonMegadealCoupon(requestDto);
        }
        return ResponseEntity.ok(BaseResponse.success(cuponCode));
    }

   // @PreAuthorize("hasRole('USER')")
    @PutMapping(path = "/redeem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<RedeemCouponResponse>> redeem(@RequestBody @Valid RedeemCouponDto redeemCouponRequest) {
        return ResponseEntity.ok(BaseResponse.success(service.redeemCoupon(redeemCouponRequest)));
    }
}
