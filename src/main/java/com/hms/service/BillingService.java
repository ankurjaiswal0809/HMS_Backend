package com.hms.service;

import com.hms.entity.Bill;

import java.util.List;

public interface BillingService {

    Bill generateBill(Long patientId,
                      Double amount,
                      String description);

    List<Bill> getAllBills();
}
