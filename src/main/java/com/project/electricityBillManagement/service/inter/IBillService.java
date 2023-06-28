package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.model.Bill;
import com.project.electricityBillManagement.payload.request.*;
import com.project.electricityBillManagement.payload.wrapper.HistoryWrapper;

import java.util.List;

public interface IBillService {
    String createBill(BillRequest request);

    String editBill(EditBillRequest request);

    String payBill(PayBillRequest request);

    List<HistoryWrapper> paymentHistory();

    List<HistoryWrapper> generateReport(GenerateBillRequest request);

    List<Bill> getBill();

    String deleteBill(DeleteBillRequest request);

    List<Bill> getBillbyStatusAdmin(BillStatusRequest request);

    List<Bill> getBillbyStatusConsumer(BillStatusRequest request);

    Bill getBillByBillNo(DeleteBillRequest request);
}
