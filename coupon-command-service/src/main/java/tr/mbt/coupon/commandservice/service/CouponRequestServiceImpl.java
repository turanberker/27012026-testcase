package tr.mbt.coupon.commandservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.commandservice.producer.RecordProducer;
import tr.mbt.coupon.commandservice.repository.CouponRepository;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.entity.CouponEntity;
import tr.mbt.coupon.coupondata.events.NewCouponRecordEvent;

@Service
@RequiredArgsConstructor
public class CouponRequestServiceImpl implements CouponRequestService {

    private final CouponRepository couponRepository;

    private final RecordService recordService;

    private final RecordProducer recordProducer;

    @Override
    @Transactional
    public String request(final CouponType requestedType, String userId) {

        CouponType type = requestedType == null ? CouponType.STANDARD : requestedType;
        int pageIndex = 0;
        PageRequest pageRequest = PageRequest.of(pageIndex, 10, Sort.by(Sort.Direction.ASC, "totalUsedCount"));
        Page<CouponEntity> page =
                couponRepository.findAvailableCoupons(type, pageRequest);

        CouponEntity selectedCoupon = null;

        while (selectedCoupon == null && page.hasContent()) {
            for (CouponEntity couponEntity : page) {
                Long totalUsedCount = recordService.countByCouponCode(couponEntity.getCode());
                if (totalUsedCount < couponEntity.getMaxUsages()) {
                    selectedCoupon = couponEntity;
                    break;
                }
            }
            if (selectedCoupon == null) {
                pageRequest = PageRequest.of(pageIndex++, 10,Sort.by(Sort.Direction.ASC, "totalUsedCount"));
                page = couponRepository.findAvailableCoupons(type, pageRequest);
            }
        }

        if (selectedCoupon == null) {
            return null;
        } else {
            recordService.increaseTotalUsage(selectedCoupon.getCode());

            recordProducer.send(new NewCouponRecordEvent(userId, selectedCoupon.getCode()));
            return selectedCoupon.getCode();
        }
    }


}
