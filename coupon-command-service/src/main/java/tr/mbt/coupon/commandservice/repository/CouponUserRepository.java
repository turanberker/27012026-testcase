package tr.mbt.coupon.commandservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.entity.CouponUserEntity;

import java.util.Optional;

@Repository
public interface CouponUserRepository extends CrudRepository<CouponUserEntity, Long> {

    Long countByCouponCode(String couponCode);

    @Query("select cu from CouponUserEntity cu where cu.coupon.expiryDate < CURRENT_DATE " +
            "and cu.userId=:userId and cu.coupon.code=:couponCode "+
            "and cu.usedDate is null order by cu.coupon.expiryDate Limit 1")
    Optional<CouponUserEntity> getAvailableCoupon(@Param("userId") String userId, @Param("couponCode") String couponCode);


}
