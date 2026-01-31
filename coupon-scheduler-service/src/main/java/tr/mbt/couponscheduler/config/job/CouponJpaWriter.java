package tr.mbt.couponscheduler.config.job;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import tr.mbt.coupon.coupondata.entity.CouponEntity;

public class CouponJpaWriter {

    public static JpaItemWriter<CouponEntity> writer(EntityManagerFactory emf) {
        JpaItemWriter<CouponEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }
}
