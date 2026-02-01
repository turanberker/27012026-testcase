package tr.mbt.coupon.commandservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.entity.CouponEntity;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CouponRepository extends CrudRepository<CouponEntity, String> {


    @Query("select c from CouponEntity c where c.couponType=:type and c.expiryDate>CURRENT_DATE and c.maxUsages>c.totalUsedCount" +
            " order by c.totalUsedCount asc Limit 1")
    Optional<CouponEntity> findAvailableCoupons(@Param("type") CouponType type);

    @Query("""
            select c
            from CouponEntity c
            where c.couponType = tr.mbt.coupon.coupondata.data.CouponType.MEGADEAL
              and c.expiryDate > CURRENT_DATE
              and not exists (
                  select 1
                  from CouponUserEntity cu
                  where cu.coupon = c
                    and cu.usedDate is null
                  group by cu.coupon
                  having count(cu) >= 5
              ) order by c.totalUsedCount asc Limit 1
            """)
    Optional<CouponEntity> findAvailableMegadealCoupon();

}
