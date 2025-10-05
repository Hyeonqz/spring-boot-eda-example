package io.github.Hyeonqz.notification.listener;

import io.github.Hyeonqz.event.PaymentStatusChangedEvent;
import io.github.Hyeonqz.notification.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationEventListener {

    @KafkaListener(topics = KafkaConfig.PAYMENT_STATUS_TOPIC, groupId = "notification-service")
    public void handlePaymentStatusChanged(PaymentStatusChangedEvent event) {
        String message = createNotificationMessage(event);
        sendNotification(event.getUserId(), message);
        
        log.info("Notification sent to user {}: {}", event.getUserId(), message);
    }

    private String createNotificationMessage(PaymentStatusChangedEvent event) {
        return switch (event.getCurrentStatus()) {
            case "COMPLETED" -> "결제가 완료되었습니다. (결제 ID: " + event.getPaymentId() + ")";
            case "FAILED" -> "결제가 실패했습니다. (결제 ID: " + event.getPaymentId() + ")";
            default -> "결제 상태가 변경되었습니다: " + event.getCurrentStatus();
        };
    }

    private void sendNotification(String userId, String message) {
        log.info("Sending notification to user {}: {}", userId, message);
    }
}
