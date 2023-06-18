package com.project.electricityBillManagement.model;

import com.project.electricityBillManagement.enumeration.BillStatus;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "Bill")
public class Bill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billNo")
    int billNo;

    @NotNull
    @Column(name ="arrears")
    double arrears;

    @NotNull
    @Column(name = "units")
    double units;

    @NotNull
    @Column(name = "fromDate")
    Date fromDate;

    @NotNull
    @Column(name= "toDate")
    Date toDate;

    @NotNull
    @Column(name = "endDate")
    Date endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    BillStatus status;

    @NotNull
    @Column(name = "totalAmount")
    double totalAmount;

    @NotNull
    @Column(name = "paidAmount")
    double paidAmount;

    @NotNull
    @Column(name = "consumerId")
    int consumerId;

    @NotNull
    @Column(name = "adminId")
    int adminId;

    @Override
    public String toString() {
        return "Bill{" +
                "billNo=" + billNo +
                ", arrears=" + arrears +
                ", units=" + units +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", paidAmount=" + paidAmount +
                ", consumerId=" + consumerId +
                ", adminId=" + adminId +
                '}';
    }
}
