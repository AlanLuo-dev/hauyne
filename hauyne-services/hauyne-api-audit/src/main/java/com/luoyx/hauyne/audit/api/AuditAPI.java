package com.luoyx.hauyne.audit.api;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

public interface AuditAPI {

    @GetMapping("/testCallAudit")
    Map<String, String> testCallAudit();
}
