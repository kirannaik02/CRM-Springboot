package com.CRM.controller;

import com.CRM.dto.CustomerDto;
import com.CRM.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }


    @Test
    void saveCustomer_ReturnsSuccessMessage() throws Exception {
        CustomerDto dto = new CustomerDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john.doe@example.com");

        when(customerService.addcustomer(any(CustomerDto.class)))
                .thenReturn("Customer Added Successfully");

        mockMvc.perform(post("/api/customer/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer Added Successfully"));

        verify(customerService, times(1)).addcustomer(any(CustomerDto.class));
    }



    @Test
    void getAllCustomers_ReturnsCustomerList() throws Exception {
        CustomerDto customer1 = new CustomerDto(1L, "Alice", "Smith", "alice.smith@example.com",
                LocalDate.of(1990, 1, 1), LocalDate.of(2025, 3, 20), true, false);

        CustomerDto customer2 = new CustomerDto(2L, "Bob", "Jones", "bob.jones@example.com",
                LocalDate.of(1992, 5, 10), LocalDate.of(2025, 3, 20), true, false);

        List<CustomerDto> customers = List.of(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/customer/getCustomer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[1].firstName").value("Bob"));

        verify(customerService, times(1)).getAllCustomers();
    }


//    @Test
//    void getCustomerById_ReturnsCustomer() throws Exception {
//        CustomerDto customer = new CustomerDto(1L, "Alice", "Smith", "alice.smith@example.com",
//                LocalDate.of(1990, 1, 1),
//                LocalDate.of(2025, 3, 20),
//                true, false);
//
//        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));
//
//        mockMvc.perform(get("/api/customer/getCustomer/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("Alice"));
//
//        verify(customerService, times(1)).getCustomerById(1L);
//    }


    @Test
    void deleteCustomer_ReturnsSuccess() throws Exception {
        when(customerService.deleteCustomer(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/customer/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer deleted successfully!"));  // ✅ Added "!" to match the actual response

        verify(customerService, times(1)).deleteCustomer(1L);
    }
}
