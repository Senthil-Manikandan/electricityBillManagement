package com.project.electricityBillManagement.service.impl;

import com.project.electricityBillManagement.enumeration.BillStatus;
import com.project.electricityBillManagement.enumeration.Tariff;
import com.project.electricityBillManagement.exception.CustomException;
import com.project.electricityBillManagement.jwt.JwtAuthenticationFilter;
import com.project.electricityBillManagement.model.Admin;
import com.project.electricityBillManagement.model.Announcement;
import com.project.electricityBillManagement.model.Bill;
import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.payload.request.*;
import com.project.electricityBillManagement.payload.wrapper.HistoryWrapper;
import com.project.electricityBillManagement.repo.AdminRepository;
import com.project.electricityBillManagement.repo.BillRepository;
import com.project.electricityBillManagement.repo.ConsumerRepository;
import com.project.electricityBillManagement.service.inter.IBillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BillServiceImpl implements IBillService {

    private final BillRepository billRepository;
    private final AdminRepository adminRepository;
    private final ConsumerRepository consumerRepository;
    private final JwtAuthenticationFilter filter;
    private final ConsumerServiceImpl csi;
    
    public double calculateBill(Tariff tariff,double units,double arrears){
        int pricePerUnit;
        if(tariff == Tariff.single)
            pricePerUnit = 10;
        else if (tariff == Tariff.three)
            pricePerUnit = 15;
        else if (tariff == Tariff.govtBuilding)
            pricePerUnit = 8;
        else if (tariff == Tariff.industrial)
            pricePerUnit = 18;
        else if (tariff == Tariff.agriculture)
            pricePerUnit = 3;
        else
            pricePerUnit = 20;

        double totalAmt = arrears + units*pricePerUnit;
        return totalAmt;
    }

    @Override
    public String createBill(BillRequest request) {
        try{
           if(filter.IsAdmin()){
               String userName = filter.getUserName();
               Admin ad = adminRepository.findAdminByEmail(userName);
               Consumer c = csi.getConsumer(request.getConsumerId());
               double totalAmt = calculateBill(c.getTariff(),request.getUnits(), c.getArrears());

               Bill bill = Bill.builder()
                       .arrears(c.getArrears())
                       .fromDate(request.getFromDate())
                       .toDate(request.getToDate())
                       .endDate(request.getEndDate())
                       .units(request.getUnits())
                       .totalAmount(totalAmt)
                       .adminId(ad.getAdminId())
                       .consumerId(request.getConsumerId())
                       .status(BillStatus.NotPaid)
                       .build();

               billRepository.save(bill);

               return "generated";
           }else{
                throw  new CustomException("Not a admin cannot generate bill");
           }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public String editBill(EditBillRequest request) {
        try{
            if(filter.IsAdmin()){
                Bill b = billRepository.findBillByBillNo(request.getBillNo());
                if( b != null){
                    Consumer c = csi.getConsumer(request.getConsumerId());
                    double totalAmt = calculateBill(c.getTariff(),request.getUnits(), c.getArrears());
                    int i = billRepository.updateBill(c.getArrears(),
                            request.getFromDate(),
                            request.getToDate(),
                            request.getEndDate(),
                            request.getUnits(),
                            request.getStatus(),
                            request.getConsumerId(),
                            totalAmt,
                            request.getBillNo());
                    if (i>0)
                        return "generated";
                    else
                        return "error during update";
                }else{
                    throw new CustomException("bill no not found");
                }
            }else{
                throw new CustomException("not a admin");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public String payBill(PayBillRequest request) {
        try{
            if(!filter.IsAdmin()){
                Bill b = billRepository.findBillByBillNo(request.getBillNo());
                Consumer c = csi.getConsumer(request.getCustomerNo());
                double dif = request.getPaidAmt() -b.getTotalAmount() ;
                if(dif >= 0){
                    int i;

                    if(request.getPaidAmt() > b.getTotalAmount())
                        i = billRepository.updateBillStatus(BillStatus.Paid,b.getTotalAmount(), request.getBillNo());
                    else
                        i = billRepository.updateBillStatus(BillStatus.Paid,b.getPaidAmount(), request.getBillNo());

                    int j = consumerRepository.updateArrears(dif, request.getCustomerNo());
                    if(i>0 && j>0)
                        return "payment successful";
                    else
                        return "error during update";
                }else{
                    int i = billRepository.updateBillStatus(BillStatus.PartiallyPaid, request.getPaidAmt(), request.getBillNo());
                    int j = consumerRepository.updateArrears(dif, request.getCustomerNo());
                    if(i>0 && j>0)
                        return "payment is partially done remit the balance during next month";
                    else
                        return "error during update";
                }
            }else{
                throw new CustomException("Not a consumer");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            throw  new CustomException(ex.getMessage());
        }
    }

    @Override
    public List<HistoryWrapper> paymentHistory(PaymentRequest request) {
        try{
            if(!filter.IsAdmin()){
                List<HistoryWrapper> result = billRepository.findBillsByConsumerId(request.getConsumerId());
                return result;
            }else{
                throw new CustomException("not a consumer");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public List<HistoryWrapper> generateReport(GenerateBillRequest request) {
        try{
            List<HistoryWrapper> result = billRepository.findBillsByBillNo(request.getBillNo());
            return result;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage());

        }
    }
}
