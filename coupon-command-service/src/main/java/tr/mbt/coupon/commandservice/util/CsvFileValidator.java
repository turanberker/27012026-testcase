package tr.mbt.coupon.commandservice.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tr.mbt.coupon.commandservice.exception.ProcessingServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static tr.mbt.coupon.coupondata.constants.UploadedFileConstants.EXPECTED_HEADERS;

@Component
public class CsvFileValidator implements FileValidator{

    @Override
    public void validate (MultipartFile file){
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
    }
}
