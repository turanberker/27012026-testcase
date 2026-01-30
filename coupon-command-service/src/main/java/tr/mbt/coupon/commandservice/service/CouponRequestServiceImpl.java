package tr.mbt.coupon.commandservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tr.mbt.coupon.commandservice.repository.CouponRepository;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.entity.CouponEntity;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CouponRequestServiceImpl implements CouponRequestService {

    private final CouponRepository couponRepository;

    private final RecordService recordService;

    @Override
    public String request(final CouponType requestedType, String userId) {

        CouponType type = requestedType == null ? CouponType.STANDARD : requestedType;
        int pageIndex = 0;
        PageRequest pageRequest = PageRequest.of(pageIndex, 10);
        Page<CouponEntity> page =
                couponRepository.findByCouponTypeAndExpiryDateAfter(type, LocalDate.now(), pageRequest);

        CouponEntity selectedCoupon = null;

        while (selectedCoupon == null && page.hasContent()) {
            for (CouponEntity couponEntity : page) {
                Long totalUsedCount = recordService.countByCouponCode(couponEntity.getCode());
                if (totalUsedCount < couponEntity.getMaxUsages()) {
                    selectedCoupon = couponEntity;
                    break;
                }
            }
            pageRequest = PageRequest.of(pageIndex++, 10);
            page = couponRepository.findByCouponTypeAndExpiryDateAfter(type, LocalDate.now(), pageRequest);
        }

        if (selectedCoupon == null) {
            return null;
        } else {
            recordService.increaseTotalUsage(selectedCoupon.getCode());
            recordService.redeem(selectedCoupon.getCode(), userId);
            return selectedCoupon.getCode();
        }
    }


}
