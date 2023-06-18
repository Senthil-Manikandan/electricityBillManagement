package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.payload.request.*;
import com.project.electricityBillManagement.payload.wrapper.HistoryWrapper;

import java.util.List;

public interface IBillService {
    String createBill(BillRequest request);

    String editBill(EditBillRequest request);

    String payBill(PayBillRequest request);

    List<HistoryWrapper> paymentHistory(PaymentRequest request);

    List<HistoryWrapper> generateReport(GenerateBillRequest request);
}
