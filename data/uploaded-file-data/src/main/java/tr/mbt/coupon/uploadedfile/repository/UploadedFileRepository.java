package tr.mbt.coupon.uploadedfile.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.uploadedfile.entity.UploadedFileEntity;

@Repository
public interface UploadedFileRepository extends CrudRepository<UploadedFileEntity,String> {
}
