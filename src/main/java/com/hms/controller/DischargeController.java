package com.hms.controller;

import com.hms.dto.DischargeRequest;
import com.hms.entity.Discharge;
import com.hms.service.DischargeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discharge")
@RequiredArgsConstructor
public class DischargeController {

    private final DischargeService dischargeService;

    @PostMapping
    public Discharge discharge(@Valid @RequestBody DischargeRequest request) {
        return dischargeService.dischargePatient(
                request.getPatientId(),
                request.getSummary()
        );
    }

    @GetMapping("/{id}/pdf")
    public void downloadPdf(@PathVariable Long id,
                            HttpServletResponse response) throws Exception {

        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=discharge_" + id + ".pdf"
        );

        dischargeService.generateDischargePdf(
                id,
                response.getOutputStream()
        );
    }
}
