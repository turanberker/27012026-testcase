package tr.mbt.coupon.coupondata.entity;

import jakarta.persistence.*;
import tr.mbt.coupon.coupondata.data.UploadedFileStatus;

@Entity
@Table( name = "Uploaded_file")
public class UploadedFileEntity {

    @Id
    @Column(name = "FILE_NAME",updatable = false)
    private String fileName;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
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
