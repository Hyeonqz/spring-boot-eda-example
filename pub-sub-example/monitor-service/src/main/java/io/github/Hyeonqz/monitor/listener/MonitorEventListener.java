package io.github.Hyeonqz.monitor.listener;

import io.github.Hyeonqz.event.PaymentStatusChangedEvent;
import io.github.Hyeonqz.monitor.config.KafkaConfig;
import io.github.Hyeonqz.monitor.service.MonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MonitorEventListener {
    private static final Logger logger = LoggerFactory.getLogger(MonitorEventListener.class);
    private final MonitoringService monitoringService;

    public MonitorEventListener(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @KafkaListener(topics = KafkaConfig.PAYMENT_STATUS_TOPIC, groupId = "monitor-service")
    public void handlePaymentStatusChanged(PaymentStatusChangedEvent event) {
        monitoringService.updatePaymentStats(event.getCurrentStatus());
        
        logger.info("Monitor updated - Payment ID: {}, Status: {}, Success Rate: {}%", 
            event.getPaymentId(), 
            event.getCurrentStatus(),
            String.format("%.2f", monitoringService.getSuccessRate()));
    }
}
