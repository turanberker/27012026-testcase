package tr.mbt.coupon.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.entity.CouponTypeLogEntity;

@Repository
public interface CouponTypeLogRepository extends CrudRepository<CouponTypeLogEntity, CouponType> {
}
