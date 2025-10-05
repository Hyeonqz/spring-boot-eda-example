package io.github.Hyeonqz.payment.controller;

import io.github.Hyeonqz.payment.dto.PaymentStatusRequest;
import io.github.Hyeonqz.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{paymentId}/status")
    public ResponseEntity<String> changePaymentStatus(
            @PathVariable Long paymentId,
            @RequestBody PaymentStatusRequest request) {
        
        paymentService.changePaymentStatus(paymentId, request.getStatus(), request.getUserId());
        return ResponseEntity.ok("Payment status changed successfully");
    }

    @GetMapping("/{paymentId}/status")
    public ResponseEntity<String> getPaymentStatus(@PathVariable Long paymentId) {
        String status = paymentService.getPaymentStatus(paymentId);
        return ResponseEntity.ok(status);
    }
}
