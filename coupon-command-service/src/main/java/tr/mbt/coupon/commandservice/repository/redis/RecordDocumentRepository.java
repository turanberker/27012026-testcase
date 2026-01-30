package tr.mbt.coupon.commandservice.repository.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.commandservice.entity.redis.RecordDocument;

@Repository
public interface RecordDocumentRepository extends CrudRepository<RecordDocument, String> {

}
