package tr.mbt.couponscheduler.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.coupondata.entity.RecordEntity;

@Repository
public interface RecordRepositoy extends CrudRepository<RecordEntity,Long> {

    @Modifying
    @Transactional
    @Query("delete from RecordEntity r where r.used = false and r.coupon.expiryDate < CURRENT_DATE")
    int deleteUnusedCoupons();
}
