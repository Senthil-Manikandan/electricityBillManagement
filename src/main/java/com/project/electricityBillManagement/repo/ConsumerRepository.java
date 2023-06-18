package com.project.electricityBillManagement.repo;

import com.project.electricityBillManagement.enumeration.Status;
import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.payload.wrapper.ConsumerWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ConsumerRepository extends JpaRepository <Consumer,Integer>{
    Optional<Consumer> findByConsumerId(int consumerId);

    Consumer findByEmail(String email);


    @Query("select new com.project.electricityBillManagement.payload.wrapper.ConsumerWrapper(c.firstName,c.lastName,c.email,c.phoneNo,c.address,c.meterNo,c.tariff,c.status) from Consumer c")
    List<ConsumerWrapper> findAllByWrapper();

    @Modifying
    @Query("UPDATE Consumer c SET c.status = ?1 where c.consumerId =?2")
    void changeStatus(@Param("status")Status status, @Param("consumerId") int consumerId);

    @Modifying
    @Query("UPDATE Consumer c SET c.arrears = ?1 where c.consumerId = ?2")
    int updateArrears(double Arrears,int consumerNo);
}
