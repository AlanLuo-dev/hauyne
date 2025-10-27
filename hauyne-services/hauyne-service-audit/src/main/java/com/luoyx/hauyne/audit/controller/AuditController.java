package com.luoyx.hauyne.audit.controller;

import com.luoyx.hauyne.audit.api.AuditAPI;
import com.luoyx.hauyne.audit.feignclient.UAAFeignClient;
import com.luoyx.hauyne.audit.query.AuditQuery;
import com.luoyx.hauyne.audit.response.AuditChangeVO;
import com.luoyx.hauyne.audit.service.AuditService;
import com.luoyx.hauyne.web.exception.ValidateException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审计日志 控制器
 *
 * @author Alan.Luo
 */
@Tag(name = "AuditController", description = "审计日志")
@RestController
@RequiredArgsConstructor
public class AuditController implements AuditAPI {

    private final AuditService auditService;

    private final UAAFeignClient UAAFeignClient;

    @Override
    @GetMapping("/testCallAudit")
    public Map<String, String> testCallAudit() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        System.out.println("请求头Authorization: " + request.getHeader("Authorization"));

        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("audit", "这是来自Audit的数据");

//        return stringStringMap;
        throw new ValidateException("Audit 端发生异常");
    }

    /**
     * 查询快照
     *
     * @param query 查询参数
     * @return 快照列表
     */
    @Operation(summary = "查询快照")
    @GetMapping("/snapshots")
    public String findSnapshots(@Validated AuditQuery query) {
        return auditService.findSnapshots(query);
    }

    @Operation(summary = "查询影子")
    @GetMapping("/shadows")
    public String findShadows(@Validated AuditQuery query) {
        return auditService.findShadows(query);
    }

    @Operation(summary = "查询变更日志")
    @GetMapping(value = "/changes", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findChanges(@Validated AuditQuery query) {
        return auditService.findChanges(query);
    }

    /**
     * 查询分组后的变更日志
     *
     * @param query 查询参数
     * @return 变更日志列表
     */
    @Operation(summary = "查询分组后的变更日志")
    @GetMapping("/grouped-changes")
    public List<AuditChangeVO> groupChangesByCommit(@Validated AuditQuery query) {
        return auditService.groupChangesByCommit(query);
    }
}

