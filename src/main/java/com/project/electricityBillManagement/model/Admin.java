package com.project.electricityBillManagement.model;

import com.project.electricityBillManagement.enumeration.AdminStatus;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "admin",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email") })
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adminId")
    int adminId;

    @Column(name="firstName")
    @NotNull
    String firstName;

    @Column(name="lastName")
    @NotNull
    String lastName;

    @NotNull
    @Column(name = "email")
    String email;

    @NotNull
    @Column(name="phoneNo")
    long phoneNo;

    @Column(name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    AdminStatus adminStatus;

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo=" + phoneNo +
                ", adminStatus=" + adminStatus +
                '}';
    }
}
