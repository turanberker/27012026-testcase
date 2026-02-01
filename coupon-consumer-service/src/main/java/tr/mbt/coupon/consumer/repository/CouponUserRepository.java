package tr.mbt.coupon.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.coupondata.entity.CouponUserEntity;

@Repository
public interface CouponUserRepository extends CrudRepository<CouponUserEntity, Long> {
}
