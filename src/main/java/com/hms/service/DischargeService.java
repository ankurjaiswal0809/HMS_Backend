package com.hms.service;

import com.hms.entity.Discharge;

import java.io.OutputStream;

public interface DischargeService {

    Discharge dischargePatient(Long patientId, String summary);

    void generateDischargePdf(Long dischargeId,
                              OutputStream outputStream) throws Exception;
}
