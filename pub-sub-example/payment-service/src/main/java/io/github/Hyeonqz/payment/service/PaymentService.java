package io.github.Hyeonqz.payment.service;

import io.github.Hyeonqz.event.PaymentStatusChangedEvent;
import io.github.Hyeonqz.payment.config.KafkaConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentService {
    private final KafkaTemplate<String, PaymentStatusChangedEvent> kafkaTemplate;
    private final Map<Long, String> paymentStatuses = new ConcurrentHashMap<>();

    public PaymentService(KafkaTemplate<String, PaymentStatusChangedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        paymentStatuses.put(1L, "PENDING");
        paymentStatuses.put(2L, "PENDING");
    }

    public void changePaymentStatus(Long paymentId, String newStatus, String userId) {
        String previousStatus = paymentStatuses.get(paymentId);
        if (previousStatus == null) {
            throw new IllegalArgumentException("Payment not found: " + paymentId);
        }

        paymentStatuses.put(paymentId, newStatus);
        
        PaymentStatusChangedEvent event = new PaymentStatusChangedEvent(
            paymentId, previousStatus, newStatus, userId
        );
        
        kafkaTemplate.send(KafkaConfig.PAYMENT_STATUS_TOPIC, event);
    }

    public String getPaymentStatus(Long paymentId) {
        return paymentStatuses.get(paymentId);
    }
}
