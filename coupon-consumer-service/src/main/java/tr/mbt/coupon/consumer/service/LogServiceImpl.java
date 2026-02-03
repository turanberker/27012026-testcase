package tr.mbt.coupon.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.mbt.coupon.consumer.entity.LogEntity;
import tr.mbt.coupon.consumer.repository.LogRepository;
import tr.mtb.coupon.logdata.MethodLogEvent;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final ObjectMapper objectMapper;
    private final LogRepository logRepository;

    @Override
    @Transactional
    public void saveLog(MethodLogEvent event) {

        LogEntity logEntity = LogEntity.builder()
                .serviceName(event.getServiceName())
                .className(event.getClassName())
                .methodName(event.getMethodName())
                .argumentsJson(toJson(event.getArguments()))
                .resultJson(toJson(event.getResult()))
                .durationMs(event.getDurationMs())
                .success(event.isSuccess())
                .exceptionMessage(event.getExceptionMessage())
                .date(event.getDate())
                .build();
        logRepository.save(logEntity);

    }

    private String toJson(Object value) {
        if (value == null) return "null";
        try {
            if (value.getClass().isArray() || value instanceof Collection) {
                return objectMapper.writeValueAsString(value);
            } else {
                // Tekil değerleri array içine al
                return objectMapper.writeValueAsString(new Object[]{value});
            }
        } catch (Exception e) {
            return "\"<serialization_failed>\"";
        }
    }
}
