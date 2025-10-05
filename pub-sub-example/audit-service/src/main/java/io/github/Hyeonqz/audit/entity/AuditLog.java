package io.github.Hyeonqz.audit.entity;

import java.time.LocalDateTime;

public record AuditLog(
        Long id,
        Long paymentId,
        String previousStatus,
        String currentStatus,
        String userId,
        LocalDateTime timestamp
) {

}
