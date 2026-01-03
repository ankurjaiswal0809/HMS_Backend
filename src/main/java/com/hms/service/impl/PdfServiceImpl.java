package com.hms.service.impl;

import com.hms.entity.Bill;
import com.hms.entity.Discharge;
import com.hms.entity.Patient;
import com.hms.repository.BillRepository;
import com.hms.repository.DischargeRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.PdfService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    private final PatientRepository patientRepository;
    private final BillRepository billRepository;
    private final DischargeRepository dischargeRepository;

    public byte[] generateDischargePdf(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Bill bill = billRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        Discharge discharge = dischargeRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Discharge record not found"));

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();

            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font valueFont = new Font(Font.FontFamily.HELVETICA, 12);

            document.add(new Paragraph("Hospital Management System", titleFont));
            document.add(new Paragraph("DISCHARGE SUMMARY\n\n", titleFont));

            document.add(new Paragraph("Patient Details", labelFont));
            document.add(new Paragraph("Name: " + patient.getName(), valueFont));
            document.add(new Paragraph("Age: " + patient.getAge(), valueFont));
            document.add(new Paragraph("Gender: " + patient.getGender(), valueFont));
            document.add(new Paragraph("Phone: " + patient.getPhone(), valueFont));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Discharge Details", labelFont));
            document.add(new Paragraph(
                    "Discharge Date: " +
                    discharge.getDischargeDate().format(DateTimeFormatter.ISO_DATE),
                    valueFont
            ));
            document.add(new Paragraph(
                    "Summary: " + discharge.getSummary(),
                    valueFont
            ));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Billing Details", labelFont));
            document.add(new Paragraph("Bill ID: " + bill.getId(), valueFont));
            document.add(new Paragraph("Total Amount: ₹" + bill.getAmount(), valueFont));
            document.add(new Paragraph(
                    "Bill Date: " +
                    bill.getBillDate().format(DateTimeFormatter.ISO_DATE),
                    valueFont
            ));

            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("Doctor Signature: ____________________", valueFont));
            document.add(new Paragraph("Hospital Seal", valueFont));

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating discharge PDF", e);
        }
    }
}
