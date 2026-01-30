package tr.mbt.coupon.commandservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.coupondata.entity.RecordEntity;
import tr.mbt.coupon.commandservice.redis.RecordDocument;
import tr.mbt.coupon.commandservice.repository.RecordRepository;
import tr.mbt.coupon.commandservice.redis.RecordDocumentRepository;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final RecordDocumentRepository recordDocumentRepository;

    @Override
    @Transactional
    public Long countByCouponCode(String couponCode) {

        RecordDocument recordDocument = getFromCache(couponCode);
        if (recordDocument != null) {
            return recordDocument.getTotalUsage();
        }
        Long count = recordRepository.countByCouponCode(couponCode);

        saveToRedis(couponCode, count);
        return count;
    }

    private RecordDocument getFromCache(String couponCode) {
        return recordDocumentRepository.findById(couponCode).orElse(null);
    }

    private void saveToRedis(String couponCode, Long count) {
        RecordDocument document = new RecordDocument(couponCode, count);
        recordDocumentRepository.save(document);
    }


    @Override
    @Transactional
    public void increaseTotalUsage(String couponCode){
        RecordDocument fromCache = getFromCache(couponCode);
        if (fromCache == null) {
            saveToRedis(couponCode, 1L);
        } else {
            fromCache.setTotalUsage(fromCache.getTotalUsage() + 1);
            recordDocumentRepository.save(fromCache);
        }
    }
}
