package tr.mbt.coupon.commandservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tr.mbt.coupon.commandservice.exception.ProcessingServiceException;
import tr.mbt.coupon.commandservice.repository.UploadedFileRepository;
import tr.mbt.coupon.commandservice.util.FileValidator;
import tr.mbt.coupon.coupondata.entity.UploadedFileEntity;
import tr.mbt.coupon.loggingaop.CouponLog;
import tr.mbt.minioclient.FileStorageClient;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileStorageClient fileStorageClient;
    private final UploadedFileRepository repository;
    private final FileValidator fileValidator;

    @Override
    @Transactional
    @CouponLog(logArgs = true, logResult = true, logException = true)
    public String upload(MultipartFile file) {

        fileValidator.validate(file);

        boolean exists = repository.existsById(file.getOriginalFilename());
        if (exists) {
            throw new ProcessingServiceException("File is uploaded before");
        }
        UploadedFileEntity uploadedFileEntity = new UploadedFileEntity(file.getOriginalFilename());
        uploadedFileEntity = repository.save(uploadedFileEntity);

        try {
            fileStorageClient.upload(file.getOriginalFilename(), file.getInputStream(), file.getSize(), file.getContentType());
        } catch (Exception e) {
            throw new ProcessingServiceException("File can not uploaded");
        }

        return file.getOriginalFilename();
    }
}
