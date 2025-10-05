package io.github.Hyeonqz.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusChangedEvent {
    private Long paymentId;
    private String previousStatus;
    private String currentStatus;
    private String userId;
}
