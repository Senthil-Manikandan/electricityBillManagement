package com.project.electricityBillManagement.repo;

import com.project.electricityBillManagement.enumeration.BillStatus;
import com.project.electricityBillManagement.model.Bill;
import com.project.electricityBillManagement.payload.wrapper.HistoryWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface BillRepository extends JpaRepository<Bill,Integer> {
    Bill findBillByBillNo(int billNo);

    @Modifying
    @Query("UPDATE Bill b SET b.arrears = ?1,b.fromDate = ?2,b.toDate=?3,b.endDate=?4,b.units=?5,b.status=?6,b.consumerId=?7,b.totalAmount=?8 where b.billNo = ?9")
    int updateBill(double arrears, Date fromDate, Date toDate, Date endDate, double units, BillStatus status, int consumerId, double totalAmt, int billNo);

    @Modifying
    @Query("UPDATE Bill  b SET b.status = ?1,b.paidAmount =?2 WHERE b.billNo = ?3")
    int updateBillStatus(BillStatus status,double paidAmount,int billNo);

    @Query("SELECT new com.project.electricityBillManagement.payload.wrapper.HistoryWrapper(b.billNo,b.arrears,b.totalAmount,b.paidAmount,b.units,b.fromDate,b.toDate,b.endDate,b.status,b.adminId,b.paymentMethod) from Bill b where b.consumerId = ?1 order by b.billNo desc")
    List<HistoryWrapper> findBillsByConsumerId(int consumerId);

    @Query("SELECT new com.project.electricityBillManagement.payload.wrapper.HistoryWrapper(b.billNo,b.arrears,b.totalAmount,b.paidAmount,b.units,b.fromDate,b.toDate,b.endDate,b.status,b.adminId,b.paymentMethod) from Bill b where b.billNo = ?1")
    List<HistoryWrapper> findBillsByBillNo(int BillNo);

    @Query("SELECT new com.project.electricityBillManagement.payload.wrapper.HistoryWrapper(b.billNo,b.arrears,b.totalAmount,b.paidAmount,b.units,b.fromDate,b.toDate,b.endDate,b.status,b.adminId,b.paymentMethod) from Bill b where b.consumerId = ?1 and b.fromDate >= ?2")
    HistoryWrapper findBillByConsumerIdAndFromDateAfterCustom(int consumerId,Date fromDate);

    @Query("SELECT e FROM Bill e WHERE e.fromDate >= ?1 and e.adminId = ?2" )
    List<Bill> findBillsByFromDate(Date startDate, int adminId);

    @Query("SELECT e FROM Bill e WHERE e.fromDate >= ?1 and e.consumerId = ?2" )
    List<Bill> findBillsByFromDateConsumer(Date startDate, int adminId);

    @Modifying
    @Query("DELETE FROM Bill a where a.billNo = ?1")
    void deleteBillByBillNo(int billNo);

    @Query("SELECT e FROM Bill e WHERE e.status = ?1 and e.adminId = ?2 ")
    List<Bill> findBillsByStatusAdmin(BillStatus status, int adminId);

    @Query("SELECT e FROM Bill e WHERE e.status = ?1 and e.consumerId = ?2 ")
    List<Bill> findBillsByStatusConsumer(BillStatus status, int consumerId);
}
