package tr.mbt.coupon.uploadedfile.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.uploadedfile.data.UploadedFileStatus;
import tr.mbt.coupon.uploadedfile.entity.UploadedFileEntity;

import java.util.List;

@Repository
public interface UploadedFileRepository extends CrudRepository<UploadedFileEntity,String> {

    List<UploadedFileEntity> findByStatus(UploadedFileStatus status);
}
