package tr.mbt.coupon.commandservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tr.mbt.coupon.commandservice.exception.ProcessingServiceException;
import tr.mbt.coupon.commandservice.repository.UploadedFileRepository;
import tr.mbt.coupon.coupondata.entity.UploadedFileEntity;
import tr.mbt.minioclient.FileStorageClient;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileStorageClient fileStorageClient;
    private final UploadedFileRepository repository;

    @Override
    @Transactional
    public String upload(MultipartFile file) {

        boolean exists = repository.existsById(file.getName());
        if (exists) {
            throw new ProcessingServiceException("File is uploaded before");
        }
        UploadedFileEntity uploadedFileEntity = new UploadedFileEntity(file.getName());
        uploadedFileEntity = repository.save(uploadedFileEntity);

        try {
            fileStorageClient.upload(file.getName(), file.getInputStream(), file.getSize(), file.getContentType());
        } catch (IOException e) {
            throw new ProcessingServiceException("File can not uploaded");
        }

        return uploadedFileEntity.getFileName();
    }
}
