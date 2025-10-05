package io.github.Hyeonqz.monitor.controller;

import io.github.Hyeonqz.monitor.service.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/monitor")
public class MonitorController {
    private final MonitoringService monitoringService;

    public MonitorController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getPaymentStats() {
        Map<String, Object> stats = Map.of(
            "totalPayments", monitoringService.getTotalPayments(),
            "completedPayments", monitoringService.getCompletedPayments(),
            "failedPayments", monitoringService.getFailedPayments(),
            "successRate", String.format("%.2f%%", monitoringService.getSuccessRate())
        );
        
        return ResponseEntity.ok(stats);
    }
}
