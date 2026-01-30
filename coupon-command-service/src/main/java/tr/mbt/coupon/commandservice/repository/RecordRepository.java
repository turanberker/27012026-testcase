package tr.mbt.coupon.commandservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.commandservice.entity.RecordEntity;

@Repository
public interface RecordRepository extends CrudRepository<RecordEntity, Long> {

    Long countByCouponCode(String couponCode);
}
