package tr.mbt.couponscheduler.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponStepListener implements StepExecutionListener {

    private final CouponSkipListener skipListener;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        skipListener.setStepExecution(stepExecution);
    }

}
