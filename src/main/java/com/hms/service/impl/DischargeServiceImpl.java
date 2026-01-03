package com.hms.service.impl;

import com.hms.entity.Discharge;
import com.hms.repository.DischargeRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.DischargeService;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DischargeServiceImpl implements DischargeService {

    private final DischargeRepository dischargeRepository;
    private final PatientRepository patientRepository;

    @Override
    public Discharge dischargePatient(Long patientId, String summary) {

        Discharge discharge = new Discharge();
        discharge.setPatient(patientRepository.findById(patientId).orElseThrow());
        discharge.setSummary(summary);
        discharge.setDischargeDate(LocalDateTime.now());

        return dischargeRepository.save(discharge);
    }

    @Override
    public void generateDischargePdf(Long dischargeId,
                                     OutputStream outputStream) throws Exception {

        Discharge discharge = dischargeRepository.findById(dischargeId)
                .orElseThrow();

        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();
        document.add(new Paragraph("DISCHARGE SUMMARY"));
        document.add(new Paragraph("Patient: "
                + discharge.getPatient().getName()));
        document.add(new Paragraph("Summary: "
                + discharge.getSummary()));
        document.add(new Paragraph("Date: "
                + discharge.getDischargeDate()));
        document.close();
    }
}
