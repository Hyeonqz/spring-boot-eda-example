package io.github.Hyeonqz.audit.controller;

import io.github.Hyeonqz.audit.entity.AuditLog;
import io.github.Hyeonqz.audit.listener.AuditEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {
    private final AuditEventListener auditEventListener;

    @GetMapping("/logs")
    public ResponseEntity<List<AuditLog>> getAuditLogs() {
        return ResponseEntity.ok(auditEventListener.getAllAuditLogs());
    }
}
