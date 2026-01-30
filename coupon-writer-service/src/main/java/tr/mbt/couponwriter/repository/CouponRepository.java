package tr.mbt.couponwriter.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.coupondata.entity.CouponEntity;

import java.util.List;

@Repository
public interface CouponRepository extends CrudRepository<CouponEntity,String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CouponEntity c WHERE c.expiryDate < CURRENT_DATE and totalUsedCount=0")
    int deleteUnusedCoupons();
}
