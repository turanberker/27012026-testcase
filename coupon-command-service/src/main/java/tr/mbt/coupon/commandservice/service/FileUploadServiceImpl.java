package tr.mbt.coupon.commandservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tr.mbt.coupon.commandservice.exception.ProcessingServiceException;
import tr.mbt.coupon.commandservice.repository.UploadedFileRepository;
import tr.mbt.coupon.coupondata.constants.UploadedFileConstants;
import tr.mbt.coupon.coupondata.entity.UploadedFileEntity;
import tr.mbt.minioclient.FileStorageClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static tr.mbt.coupon.coupondata.constants.UploadedFileConstants.EXPECTED_HEADERS;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileStorageClient fileStorageClient;
    private final UploadedFileRepository repository;

    @Override
    @Transactional
    public String upload(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ProcessingServiceException("File is empty");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                (!contentType.equals("text/csv")
                        && !contentType.equals("application/csv")
                        && !contentType.equals("application/vnd.ms-excel"))) {
            throw new ProcessingServiceException("Only CSV files are allowed");
        }

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            String headerLine = reader.readLine();

            if (headerLine == null) {
                throw new ProcessingServiceException("CSV header is missing");
            }

            // BOM temizliği (Excel sık yapar)
            headerLine = headerLine.replace("\uFEFF", "").trim();

            headerLine = headerLine.replace("\uFEFF", "").trim();

            List<String> headers = Arrays.stream(headerLine.split(","))
                    .map(String::trim)
                    .toList();

            if (!headers.equals(List.of(EXPECTED_HEADERS))) {
                throw new IllegalArgumentException(
                        "Invalid CSV header. Expected: " + Arrays.toString(EXPECTED_HEADERS)
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("CSV file read error", e);
        }

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
