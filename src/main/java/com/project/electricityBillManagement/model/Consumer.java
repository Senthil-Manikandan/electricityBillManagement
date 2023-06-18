package com.project.electricityBillManagement.model;

import com.project.electricityBillManagement.enumeration.Status;
import com.project.electricityBillManagement.enumeration.Tariff;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "consumer",
uniqueConstraints = {
        @UniqueConstraint(columnNames = "email") })
public class Consumer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private int consumerId;
    @Column(name = "firstName")
    @NotNull
    private String firstName;

    @Column(name = "lastName")
    @NotNull
    private String lastName;

    @Column(name = "email")
    @NotNull
    private String email;
    @Column(name = "phoneno")
    @NotNull
    private long phoneNo;
    @Column(name = "address")
    @NotNull
    private String address;
    @Column(name = "meterno")
    @NotNull
    private String meterNo;
    @Column(name = "tariff")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Tariff tariff;
    @Column(name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Column(name = "arrears")
    double arrears;

    @Override
    public String toString() {
        return "Consumer{" +
                "consumerId=" + consumerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo=" + phoneNo +
                ", address='" + address + '\'' +
                ", meterNo='" + meterNo + '\'' +
                ", tariff='" + tariff + '\'' +
                ", status=" + status +
                ", arrears=" + arrears +
                '}';
    }
}
