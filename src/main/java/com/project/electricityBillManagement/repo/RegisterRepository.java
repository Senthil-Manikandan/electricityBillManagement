package com.project.electricityBillManagement.repo;

import com.project.electricityBillManagement.model.Register;
import com.project.electricityBillManagement.payload.wrapper.RegisterWrapper;
import org.hibernate.annotations.NamedQuery;
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
public interface RegisterRepository  extends JpaRepository<Register,Integer> {
    Register findRegisterByEmail(String email);
    Register findRegisterByPhoneNo(long phoneNo);

    Optional<Register> findRegisterByRegisterId(int registerId);



    @Modifying
    @Query("UPDATE Register r SET r.password = ?1 where r.registerId =?2")
    Integer updatePassword(@Param("password") String password, @Param("registerId") int registerId);
//
    @Query("select new com.project.electricityBillManagement.payload.wrapper.RegisterWrapper(r.registerId,r.firstname,r.lastname,r.email,r.phoneNo,r.role) from Register r")
    List<RegisterWrapper> findAllByRegisterWrapper();
}
