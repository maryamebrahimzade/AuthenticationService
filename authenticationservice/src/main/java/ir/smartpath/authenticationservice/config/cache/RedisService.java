package ir.smartpath.authenticationservice.config.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${redis.prefix}")
    private String redisPrefix;

    public <V> void set(String key, V value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(redisPrefix + key, value);
        redisTemplate.expire(redisPrefix + key, timeout, timeUnit);
    }

    public <V> V get(String cacheName) {
        return (V) redisTemplate.opsForValue().get(cacheName);
    }
}
