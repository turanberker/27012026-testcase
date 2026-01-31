package tr.mbt.couponscheduler.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.coupondata.data.UploadedFileStatus;
import tr.mbt.coupon.coupondata.entity.UploadedFileEntity;

import java.util.List;

@Repository
public interface UploadedFileRepository extends CrudRepository<UploadedFileEntity, String> {

    List<UploadedFileEntity> findByStatus(UploadedFileStatus status);
}
