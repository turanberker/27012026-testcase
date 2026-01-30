package tr.mbt.coupon.commandservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.entity.CouponEntity;

@Repository
public interface CouponRepository extends CrudRepository<CouponEntity,String> {


    @Query("select c from CouponEntity c where c.couponType=:type and c.expiryDate>CURRENT_DATE and c.maxUsages>c.totalUsedCount")
    Page<CouponEntity> findAvailableCoupons(@Param("type") CouponType type, Pageable pageable);


}
