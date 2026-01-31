package tr.mbt.coupon.commandservice.controller;

import com.mbt.servicecommon.BaseResponse;
import com.mbt.servicecommon.ErrorDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.mbt.coupon.commandservice.dto.CouponRequestDto;
import tr.mbt.coupon.commandservice.service.CouponRequestService;

@RestController
@RequestMapping(path = "/coupon-request")
@RequiredArgsConstructor
@Validated
public class CouponRequestRestController {

    private final CouponRequestService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> request(@RequestBody @Valid CouponRequestDto requestDto) {

        String cuponCode = service.request(requestDto);
        if (Strings.isBlank(cuponCode)) {
            return ResponseEntity.ok(BaseResponse.error(ErrorDetail.builder().detail("No Available").build()));
        } else {
            return ResponseEntity.ok(BaseResponse.success(cuponCode));
        }

    }
}
