package tr.mbt.coupon.commandservice.configuration;

import com.mbt.servicecommon.BaseResponse;
import com.mbt.servicecommon.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.mbt.coupon.commandservice.exception.ProcessingServiceException;

@RestControllerAdvice
public class CommandControllerAdvice {

    @ExceptionHandler(ProcessingServiceException.class)
    public ResponseEntity<BaseResponse<ErrorDetail>> handleIllegalState(
            ProcessingServiceException ex
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(BaseResponse.error(ErrorDetail.builder().detail(ex.getMessage()).build()));
    }
}
