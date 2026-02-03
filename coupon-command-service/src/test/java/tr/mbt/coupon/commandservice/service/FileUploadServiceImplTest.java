package tr.mbt.coupon.commandservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import tr.mbt.coupon.commandservice.exception.ProcessingServiceException;
import tr.mbt.coupon.commandservice.repository.UploadedFileRepository;
import tr.mbt.coupon.commandservice.util.FileValidator;
import tr.mbt.coupon.coupondata.entity.UploadedFileEntity;
import tr.mbt.minioclient.FileStorageClient;
import tr.mbt.minioclient.FileStorageException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileUploadServiceImplTest {

    @InjectMocks
    private FileUploadServiceImpl fileUploadService;

    @Mock
    private FileValidator fileValidator;

    @Mock
    private FileStorageClient fileStorageClient;

    @Mock
    private UploadedFileRepository uploadedFileRepository;

    @Test
    public void upload_success() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        final    String fileName = "test.csv";
        when(file.getOriginalFilename()).thenReturn(fileName);
        when(file.getSize()).thenReturn(100L);
        when(file.getContentType()).thenReturn("text/csv");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("data".getBytes()));
        when(uploadedFileRepository.existsById("test.csv")).thenReturn(false);
        when(uploadedFileRepository.save(any(UploadedFileEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String result = fileUploadService.upload(file);

        assertEquals("test.csv", result);
        verify(fileValidator).validate(file);
        verify(uploadedFileRepository).save(any(UploadedFileEntity.class));
        verify(fileStorageClient).upload(
                eq("test.csv"),
                any(InputStream.class),
                eq(100L),
                eq("text/csv")
        );
        assertEquals(fileName,result);
    }

    @Test
    void upload_fileAlreadyExists_throwException() {
        MultipartFile file = mock(MultipartFile.class);

        when(file.getOriginalFilename()).thenReturn("test.csv");
        when(uploadedFileRepository.existsById("test.csv")).thenReturn(true);

        ProcessingServiceException ex = assertThrows(
                ProcessingServiceException.class,
                () -> fileUploadService.upload(file)
        );

        assertEquals("File is uploaded before", ex.getMessage());
        verify(uploadedFileRepository, never()).save(any());
        verify(fileStorageClient, never()).upload(any(), any(), anyLong(), any());
    }

    @Test
    void upload_storageThrowsException_throwProcessingServiceException() throws Exception {
        MultipartFile file = mock(MultipartFile.class);

        when(file.getOriginalFilename()).thenReturn("test.csv");
        when(file.getSize()).thenReturn(100L);
        when(file.getContentType()).thenReturn("text/csv");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("data".getBytes()));

        when(uploadedFileRepository.existsById("test.csv")).thenReturn(false);
        when(uploadedFileRepository.save(any(UploadedFileEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        doThrow(new FileStorageException(
                "MinIO upload failed",
                new RuntimeException("connection refused")
        ))
                .when(fileStorageClient)
                .upload(any(), any(), anyLong(), any());

        ProcessingServiceException ex = assertThrows(
                ProcessingServiceException.class,
                () -> fileUploadService.upload(file)
        );

        assertEquals("File can not uploaded", ex.getMessage());

        // 🔍 Yan etkiler
        verify(fileValidator).validate(file);
        verify(uploadedFileRepository).save(any(UploadedFileEntity.class));
        verify(fileStorageClient).upload(any(), any(), anyLong(), any());
    }
}
