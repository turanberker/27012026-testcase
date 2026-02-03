package tr.mbt.couponscheduler.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.couponscheduler.entity.CouponErrorEntity;

@Repository
public interface CouponErrorRepository extends CrudRepository<CouponErrorEntity,Long> {
}
