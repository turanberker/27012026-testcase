package tr.mbt.coupon.consumer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.consumer.repository.CouponRepository;
import tr.mbt.coupon.consumer.repository.RecordRepository;
import tr.mbt.coupon.coupondata.entity.CouponEntity;
import tr.mbt.coupon.coupondata.entity.RecordEntity;
import tr.mbt.coupon.coupondata.events.NewCouponRecordEvent;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    private final CouponRepository couponRepository;

    @Override
    @Transactional
    public void saveRecord(NewCouponRecordEvent recordEvent) {

        Optional<CouponEntity> couponOptional = couponRepository.findById(recordEvent.getCouponCode());
        if(couponOptional.isPresent()){
            CouponEntity couponEntity = couponOptional.get();

            RecordEntity recordEntity = new RecordEntity();
            recordEntity.setCoupon(couponEntity);
            recordEntity.setUserId(recordEvent.getUserId());
            recordRepository.save(recordEntity);

            couponEntity.setTotalUsedCount(couponEntity.getTotalUsedCount()+1);
            couponRepository.save(couponEntity);
        }
    }
}
