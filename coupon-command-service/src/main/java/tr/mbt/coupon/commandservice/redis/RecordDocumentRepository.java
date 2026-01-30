package tr.mbt.coupon.commandservice.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordDocumentRepository extends CrudRepository<RecordDocument, String> {

}
