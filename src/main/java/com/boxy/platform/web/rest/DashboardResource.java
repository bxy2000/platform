package com.boxy.platform.web.rest;

import com.boxy.platform.service.DashboardService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DashboardResource {
    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard/every-quantity")
    public ResponseEntity<List<Long>> findEveryQuantity() {
        List<Long> result = dashboardService.findEveryQuantity();

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @GetMapping("/dashboard/table-finished")
    public ResponseEntity<List<Map<String, Object>>> findTableFinished() {
        List<Map<String, Object>> result = dashboardService.findTableFinished();

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @GetMapping("/dashboard/field-finished")
    public ResponseEntity<List<Map<String, Object>>> findFieldFinished() {
        List<Map<String, Object>> result = dashboardService.findFieldFinished();

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
}
