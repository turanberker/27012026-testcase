package tr.mbt.couponscheduler.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.coupondata.entity.CouponUserEntity;

@Repository
public interface CouponUserRepositoy extends CrudRepository<CouponUserEntity, Long> {

    @Modifying
    @Transactional
    @Query("delete from CouponUserEntity cu where " +
            "cu.coupon.expiryDate < CURRENT_DATE and " +
            "cu.usedDate is null")
    int deleteUnusedCoupons();
}
