package tr.mbt.coupon.commandservice.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MegadealCounterRepository extends CrudRepository<MegadealCounterDocument, String> {

}
