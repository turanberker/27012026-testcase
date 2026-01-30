package tr.mbt.couponwriter.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
import tr.mbt.coupon.coupondata.data.UploadedFileStatus;
import tr.mbt.coupon.coupondata.entity.UploadedFileEntity;
import tr.mbt.couponwriter.repository.UploadedFileRepository;

@Component
@RequiredArgsConstructor
public class CouponJobListener implements JobExecutionListener {

    private final UploadedFileRepository uploadedFileRepository;

    @Override
    public void afterJob(JobExecution jobExecution) {

        String fileName = jobExecution
                .getJobParameters()
                .getString("fileName");

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            UploadedFileEntity uploadedFileEntity = uploadedFileRepository.findById(fileName).get();
            uploadedFileEntity.setStatus(UploadedFileStatus.COMPLETED);
            uploadedFileRepository.save(uploadedFileEntity);
        } else {
            UploadedFileEntity uploadedFileEntity = uploadedFileRepository.findById(fileName).get();
            uploadedFileEntity.setStatus(UploadedFileStatus.NEW);
            uploadedFileRepository.save(uploadedFileEntity);
        }
    }
}
