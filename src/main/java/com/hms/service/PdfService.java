package com.hms.service;

public interface PdfService {
	
	byte[] generateDischargePdf(Long patientId);
}
