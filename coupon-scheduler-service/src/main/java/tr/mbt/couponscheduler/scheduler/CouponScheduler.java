package tr.mbt.couponscheduler.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tr.mbt.coupon.coupondata.data.UploadedFileStatus;
import tr.mbt.coupon.coupondata.entity.UploadedFileEntity;
import tr.mbt.coupon.loggingaop.CouponLog;
import tr.mbt.couponscheduler.repository.UploadedFileRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final UploadedFileRepository uploadedFileRepository;
    private final JobLauncher jobLauncher;
    private final Job couponImportJob;

    @CouponLog(logArgs = true, logResult = true,logException = true)
    @Scheduled(fixedDelay = 60000)
    public void runJob() {
        System.out.println("Coupon writer job running...");

        List<UploadedFileEntity> byStatus = uploadedFileRepository.findByStatus(UploadedFileStatus.NEW);

        byStatus.forEach(this::processFile);
    }

    private void processFile(UploadedFileEntity uploadedFileEntity) {

     try{
         JobParameters params = new JobParametersBuilder()
                 .addString("fileName", uploadedFileEntity.getFileName())
                 .addLong("run.id", System.currentTimeMillis())
                 .toJobParameters();
         jobLauncher.run(couponImportJob, params);
     }
     catch (Exception e){
         e.printStackTrace();
     }


    }
}
