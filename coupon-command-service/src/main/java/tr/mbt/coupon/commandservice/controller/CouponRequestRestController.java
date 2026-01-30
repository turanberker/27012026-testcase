package tr.mbt.coupon.commandservice.controller;

import com.mbt.servicecommon.BaseResponse;
import com.mbt.servicecommon.ErrorDetail;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.mbt.coupon.commandservice.service.CouponRequestService;
import tr.mbt.coupon.coupondata.data.CouponType;

@RestController
@RequestMapping(path = "/coupon-request")
@RequiredArgsConstructor
public class CouponRequestRestController {

    private final CouponRequestService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> request(@RequestParam("coupon-type") CouponType type, @RequestParam("user-id") String userId) {

        String cuponCode = service.request(type, userId);
        if(Strings.isBlank(cuponCode)){
            return ResponseEntity.ok(BaseResponse.error(ErrorDetail.builder().detail("No Available").build()));
        }else{
            return ResponseEntity.ok(BaseResponse.success(cuponCode));
        }

    }
}
