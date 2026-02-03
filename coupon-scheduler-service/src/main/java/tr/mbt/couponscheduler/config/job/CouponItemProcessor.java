package tr.mbt.couponscheduler.config.job;

import org.springframework.batch.item.ItemProcessor;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.data.DiscountType;
import tr.mbt.coupon.coupondata.entity.CouponEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static tr.mbt.coupon.coupondata.constants.UploadedFileConstants.EXPIRY_DATE;


public class CouponItemProcessor
        implements ItemProcessor<CouponCsvRow, CouponEntity> {

    @Override
    public CouponEntity process(CouponCsvRow row) {

        CouponEntity couponEntity = new CouponEntity(
        );

        couponEntity.setCode(row.getCode());
        couponEntity.setCouponType(row.getType() != null
                ? CouponType.valueOf(row.getType())
                : null);
        couponEntity.setDiscountType(DiscountType.valueOf(row.getDiscountType()));
        couponEntity.setDiscountAmount(new BigDecimal(row.getDiscountAmount()));
        couponEntity.setExpiryDate(LocalDate.parse(row.getExpiryDate()));
        couponEntity.setMaxUsages(Long.valueOf(row.getMaxUsages()));

        return couponEntity;
    }
}
