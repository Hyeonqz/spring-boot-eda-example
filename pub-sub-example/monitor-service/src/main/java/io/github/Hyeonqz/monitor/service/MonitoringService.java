package io.github.Hyeonqz.monitor.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MonitoringService {
    private final AtomicInteger totalPayments = new AtomicInteger(0);
    private final AtomicInteger completedPayments = new AtomicInteger(0);
    private final AtomicInteger failedPayments = new AtomicInteger(0);

    public void updatePaymentStats(String status) {
        totalPayments.incrementAndGet();
        
        switch (status) {
            case "COMPLETED" -> completedPayments.incrementAndGet();
            case "FAILED" -> failedPayments.incrementAndGet();
        }
    }

    public int getTotalPayments() { return totalPayments.get(); }
    public int getCompletedPayments() { return completedPayments.get(); }
    public int getFailedPayments() { return failedPayments.get(); }
    
    public double getSuccessRate() {
        int total = totalPayments.get();
        return total > 0 ? (double) completedPayments.get() / total * 100 : 0.0;
    }
}
