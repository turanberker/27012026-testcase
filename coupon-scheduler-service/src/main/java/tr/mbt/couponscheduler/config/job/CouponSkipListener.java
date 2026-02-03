package tr.mbt.couponscheduler.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;
import tr.mbt.coupon.coupondata.entity.CouponEntity;
import tr.mbt.couponscheduler.entity.CouponErrorEntity;
import tr.mbt.couponscheduler.repository.CouponErrorRepository;

@Component
@RequiredArgsConstructor
public class CouponSkipListener implements SkipListener<CouponCsvRow, CouponEntity> {

    private final CouponErrorRepository errorRepository;
    private StepExecution stepExecution;

    public void setStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public void onSkipInRead(Throwable t) {
        saveError(null, t);
    }

    @Override
    public void onSkipInProcess(CouponCsvRow item, Throwable t) {
        saveError(item, t);
    }

    @Override
    public void onSkipInWrite(CouponEntity item, Throwable t) {
        saveError(item, t);
    }

    private void saveError(Object item, Throwable t) {
        CouponErrorEntity error = new CouponErrorEntity();
        String fileName = stepExecution
                .getJobParameters()
                .getString("fileName");
        error.setFilaname(fileName);
        error.setRawLine(item != null ? item.toString() : "UNKNOWN");
        error.setErrorMessage(t.getMessage());
        errorRepository.save(error);
    }
}
