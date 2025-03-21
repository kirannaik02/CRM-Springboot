package com.CRM.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private LocalDate registrationDate;
    private boolean isActive;
    private boolean deleted = false;

//    public String addcustomer(CustomerDto customer) {
//        return "";
//    }
}
