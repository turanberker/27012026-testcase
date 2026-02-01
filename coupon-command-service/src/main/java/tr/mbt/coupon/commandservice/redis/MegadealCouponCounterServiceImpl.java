package tr.mbt.coupon.commandservice.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MegadealCouponCounterServiceImpl implements MegadealCouponCounterService {

    private final StringRedisTemplate redisTemplate;
    private final MegadealCounterRepository repository;


    private static final String LOCK_KEY = "coupon:megadeal:lock";

    private static final int MAX_ACTIVE = 10;

    private static final Duration LOCK_TTL = Duration.ofMinutes(1);


    public boolean tryAcquire() {

        // Check lock if exists
        Boolean locked = redisTemplate.hasKey(LOCK_KEY);
        if (Boolean.TRUE.equals(locked)) {
            log.info("Megadeal Request is locked");
            return false;
        }

        if (getActiveCount() >= MAX_ACTIVE) {
            log.info("Megadeal Request locked added");
            redisTemplate.opsForValue()
                    .set(LOCK_KEY, "1", LOCK_TTL);
            return false;
        } else {
            repository.save(new MegadealCounterDocument(UUID.randomUUID()));
            return true;
        }
    }


    private long getActiveCount() {

        Set<String> keys = redisTemplate.keys(MegadealCounterDocument.HASH_VALUE + "*");
        return keys == null ? 0 : keys.size();

    }


}