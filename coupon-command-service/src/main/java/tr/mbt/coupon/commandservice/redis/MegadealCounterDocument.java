package tr.mbt.coupon.commandservice.redis;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;


@RedisHash(value = MegadealCounterDocument.HASH_VALUE)
public class MegadealCounterDocument {

    public static final String HASH_VALUE = "coupon:megadeal:counter";

    @Id
    private String id;

    @TimeToLive
    private Long ttl = 1L;

    public MegadealCounterDocument(UUID id) {
        this.id = id.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
