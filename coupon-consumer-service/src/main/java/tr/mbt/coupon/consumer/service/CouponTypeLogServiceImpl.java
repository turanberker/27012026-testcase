package tr.mbt.coupon.consumer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.consumer.repository.CouponTypeLogRepository;
import tr.mbt.coupon.coupondata.entity.CouponTypeLogEntity;
import tr.mbt.coupon.coupondata.events.CouponLogEvent;
import tr.mbt.coupon.loggingaop.CouponLog;

@Service
@RequiredArgsConstructor
public class CouponTypeLogServiceImpl implements CouponTypeLogService {

    private final CouponTypeLogRepository repository;

    @Override
    @Transactional
    @CouponLog(logArgs = true)
    public void saveRecord(CouponLogEvent recordEvent) {

        CouponTypeLogEntity log = repository.findById(recordEvent.getCouponType())
                .orElse(new CouponTypeLogEntity(recordEvent.getCouponType()));

        if (CouponLogEvent.EventType.USED.equals(recordEvent.getType())) {
            log.setUsedCount(log.getUsedCount() + 1);
        } else {
            log.setCreatedCount(log.getCreatedCount() + 1);
        }
        repository.save(log);
    }
}
