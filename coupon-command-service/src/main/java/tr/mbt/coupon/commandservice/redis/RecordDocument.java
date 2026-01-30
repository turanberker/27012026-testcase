package tr.mbt.coupon.commandservice.redis;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("record")
public class RecordDocument {

    @Id
    private String couponCode;

    private Long totalUsage;

}
