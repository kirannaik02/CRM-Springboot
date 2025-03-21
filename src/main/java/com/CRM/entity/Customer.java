package com.CRM.entity;

import com.CRM.dto.CustomerDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

@Entity
@Table(name="customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDate dateOfBirth;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    public void add(CustomerDto customerDto) {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }



}




