package io.github.Hyeonqz.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentStatusRequest {
    private String status;
    private String userId;

    public PaymentStatusRequest(String status, String userId) {
        this.status = status;
        this.userId = userId;
    }
}
