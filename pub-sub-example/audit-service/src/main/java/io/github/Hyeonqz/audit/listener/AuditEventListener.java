package io.github.Hyeonqz.audit.listener;

import io.github.Hyeonqz.audit.config.KafkaConfig;
import io.github.Hyeonqz.audit.entity.AuditLog;
import io.github.Hyeonqz.event.PaymentStatusChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class AuditEventListener {
    private final List<AuditLog> auditLogs = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = KafkaConfig.PAYMENT_STATUS_TOPIC, groupId = "audit-service")
    public void handlePaymentStatusChanged(PaymentStatusChangedEvent event) {
        AuditLog auditLog = new AuditLog(
                1L,
                event.getPaymentId(),
                event.getPreviousStatus(),
                event.getCurrentStatus(),
                event.getUserId(),
                LocalDateTime.now()
        );

        auditLogs.add(auditLog);

        log.info("Audit log created - Payment ID: {}, Status: {} -> {}, User: {}",
                event.getPaymentId(),
                event.getPreviousStatus(),
                event.getCurrentStatus(),
                event.getUserId());
    }

    public List<AuditLog> getAllAuditLogs() {
        return auditLogs;
    }
}
