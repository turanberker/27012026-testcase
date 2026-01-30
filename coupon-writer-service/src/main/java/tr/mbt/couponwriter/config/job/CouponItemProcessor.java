package tr.mbt.couponwriter.config.job;

import org.springframework.batch.item.ItemProcessor;
import tr.mbt.couponwriter.data.CouponType;
import tr.mbt.couponwriter.data.DiscountType;
import tr.mbt.couponwriter.entity.CouponEntity;


public class CouponItemProcessor
        implements ItemProcessor<CouponCsvRow, CouponEntity> {

    @Override
    public CouponEntity process(CouponCsvRow row) {

        CouponEntity couponEntity = new CouponEntity(
                row.getCode(),
                row.getType() != null
                        ? CouponType.valueOf(row.getType())
                        : null,
                DiscountType.valueOf(row.getDiscountType()),
                row.getDiscountAmount(),
                row.getExpiryDate(),
                row.getMaxUsages()
        );
        System.out.println(couponEntity.toString());
        return couponEntity;
    }
}
