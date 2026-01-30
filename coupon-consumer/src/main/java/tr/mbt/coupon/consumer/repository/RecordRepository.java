package tr.mbt.coupon.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.coupondata.entity.RecordEntity;

@Repository
public interface RecordRepository extends CrudRepository<RecordEntity, Long> {
}
