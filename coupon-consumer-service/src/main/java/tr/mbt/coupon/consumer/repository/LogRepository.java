package tr.mbt.coupon.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.consumer.entity.LogEntity;

@Repository
public interface LogRepository extends CrudRepository<LogEntity,Long> {
}
