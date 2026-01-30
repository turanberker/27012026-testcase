package tr.mbt.couponwriter.config.job;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import tr.mbt.couponwriter.entity.CouponEntity;

public class CouponJpaWriter {

    public static JpaItemWriter<CouponEntity> writer(EntityManagerFactory emf) {
        JpaItemWriter<CouponEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }
}
