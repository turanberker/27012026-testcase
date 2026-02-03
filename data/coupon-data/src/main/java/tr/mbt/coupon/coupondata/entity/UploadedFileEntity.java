package tr.mbt.coupon.coupondata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tr.mbt.coupon.coupondata.data.UploadedFileStatus;

@Entity
@Table(name = "Uploaded_file")
@Getter
@Setter
public class UploadedFileEntity {

    @Id
    @Column(name = "FILE_NAME", updatable = false)
    private String fileName;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private UploadedFileStatus status = UploadedFileStatus.NEW;

    public UploadedFileEntity(String fileName) {
        this.fileName = fileName;
    }

    public UploadedFileEntity() {
    }
}
