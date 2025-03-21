package com.CRM.service;

import com.CRM.dto.CustomerDto;
import com.CRM.entity.Customer;
import com.CRM.exception.CustomerNotFoundException;
import com.CRM.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCustomer_ShouldReturnSuccessMessage() {
        CustomerDto dto = new CustomerDto();
        dto.setFirstName("Alice");
        dto.setLastName("Smith");
        dto.setEmail("alice@example.com");

        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());

        when(customerRepo.save(any(Customer.class))).thenReturn(customer);

        String result = customerService.addcustomer(dto);

        assertEquals("Customer Added Successfully", result);
        verify(customerRepo, times(1)).save(any(Customer.class));
    }

    @Test
    void getCustomerById_ShouldReturnCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(customerRepo, times(1)).findById(1L);
    }

    @Test
    void getCustomerById_ThrowsException_WhenNotFound() {
        when(customerRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(1L));
        verify(customerRepo, times(1)).findById(1L);
    }
}
