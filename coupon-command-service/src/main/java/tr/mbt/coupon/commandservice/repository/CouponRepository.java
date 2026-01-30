package tr.mbt.coupon.commandservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.mbt.coupon.coupondata.data.CouponType;
import tr.mbt.coupon.coupondata.entity.CouponEntity;

import java.time.LocalDate;

@Repository
public interface CouponRepository extends CrudRepository<CouponEntity,String> {


    // Bugünden sonra geçerli olan, belirli tipteki kuponları getir
    Page<CouponEntity> findByCouponTypeAndExpiryDateAfter(CouponType type, LocalDate today, Pageable pageable);


}
