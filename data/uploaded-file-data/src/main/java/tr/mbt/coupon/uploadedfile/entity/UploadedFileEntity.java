package tr.mbt.coupon.uploadedfile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tr.mbt.coupon.uploadedfile.data.UploadedFileStatus;

@Entity
@Table( name = "Uploaded_file")
public class UploadedFileEntity {

    @Id
    @Column(name = "FILE_NAME",updatable = false)
    private String fileName;

    @Column(name = "STATUS")
    private UploadedFileStatus status=UploadedFileStatus.NEW;

    public UploadedFileEntity(String fileName) {
        this.fileName = fileName;
    }

    public UploadedFileEntity() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UploadedFileStatus getStatus() {
        return status;
    }

    public void setStatus(UploadedFileStatus status) {
        this.status = status;
    }
}
