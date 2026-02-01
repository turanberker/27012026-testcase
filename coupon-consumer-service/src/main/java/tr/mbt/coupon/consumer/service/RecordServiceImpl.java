package tr.mbt.coupon.consumer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.consumer.repository.CouponRepository;
import tr.mbt.coupon.consumer.repository.CouponUserRepository;
import tr.mbt.coupon.coupondata.entity.CouponEntity;
import tr.mbt.coupon.coupondata.entity.CouponUserEntity;
import tr.mbt.coupon.coupondata.events.NewCouponRecordEvent;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final CouponUserRepository recordRepository;

    private final CouponRepository couponRepository;

    @Override
    @Transactional
    public void saveRecord(NewCouponRecordEvent recordEvent) {

        CouponEntity couponReferance = couponRepository.getReferenceById(recordEvent.getCouponCode());

        CouponUserEntity recordEntity = new CouponUserEntity();
        recordEntity.setCoupon(couponReferance);
        recordEntity.setUserId(recordEvent.getUserId());
        recordRepository.save(recordEntity);

    }
}
