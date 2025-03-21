package com.CRM.service;

import com.CRM.dto.CustomerDto;
import com.CRM.entity.Customer;

import java.util.List;

public interface CustomerService {
    String addcustomer(CustomerDto customerDto);


    List<CustomerDto> getAllCustomers();

    List<Customer> getCustomerByFirstName(String firstName);

    List<Customer> getCustomerByLastName(String lastName);

    Customer updateCustomer(Long id, CustomerDto customerDto);

    boolean deleteCustomer(long id);


    public Customer getCustomerById(Long id) ;



}
