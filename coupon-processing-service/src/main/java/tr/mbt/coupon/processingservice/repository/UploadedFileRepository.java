package tr.mbt.coupon.processingservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.processingservice.entity.UploadedFileEntity;

@Repository
public interface UploadedFileRepository extends CrudRepository< UploadedFileEntity,String> {
}
