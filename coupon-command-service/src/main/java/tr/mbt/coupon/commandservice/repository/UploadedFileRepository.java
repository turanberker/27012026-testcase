package tr.mbt.coupon.commandservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.coupondata.entity.UploadedFileEntity;

@Repository
public interface UploadedFileRepository extends CrudRepository<UploadedFileEntity,String> {
}
