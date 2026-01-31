package tr.mbt.couponscheduler.config.job;

import org.springframework.batch.item.ItemProcessor;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.data.DiscountType;
import tr.mbt.coupon.coupondata.entity.CouponEntity;


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
        couponEntity.setDiscountAmount(row.getDiscountAmount());
        couponEntity.setExpiryDate(row.getExpiryDate());
        couponEntity.setMaxUsages(row.getMaxUsages());


        System.out.println(couponEntity.toString());
        return couponEntity;
    }
}
