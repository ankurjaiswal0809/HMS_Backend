package com.hms.service.impl;

import com.hms.entity.Bill;
import com.hms.repository.BillRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillRepository billRepository;
    private final PatientRepository patientRepository;

    @Override
    public Bill generateBill(Long patientId,
                             Double amount,
                             String description) {

        Bill bill = new Bill();
        bill.setPatient(patientRepository.findById(patientId).orElseThrow());
        bill.setAmount(amount);
        bill.setDescription(description);
        bill.setBillDate(LocalDateTime.now());

        return billRepository.save(bill);
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }
}
