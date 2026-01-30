package tr.mbt.coupon.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.coupondata.entity.CouponEntity;

@Repository
public interface CouponRepository extends CrudRepository<CouponEntity, String> {
}
