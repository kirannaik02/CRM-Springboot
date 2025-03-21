package com.CRM.service;

import com.CRM.dto.CustomerDto;
import com.CRM.entity.Customer;
import com.CRM.exception.CustomerNotFoundException;
import com.CRM.repository.CustomerRepo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger= LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public String addcustomer(CustomerDto customerDto) {

        Customer customer = new Customer();

        //Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setRegistrationDate(customerDto.getRegistrationDate());
        customer.setActive(true);

        customerRepo.save(customer);

        return "Customer Added Successfully";
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers.stream().map(customer ->
                new CustomerDto(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail(),
                        customer.getDateOfBirth(),
                        customer.getRegistrationDate(),
                        customer.isActive(),
                        customer.isDeleted()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public List<Customer> getCustomerByFirstName(String firstName) {
        return customerRepo.findByFirstNameContainingIgnoreCase(firstName);
    }

    @Override
    public List<Customer> getCustomerByLastName(String lastName) {
        return customerRepo.findByLastNameContainingIgnoreCase(lastName);
    }

    @Override
    public Customer updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setRegistrationDate(customerDto.getRegistrationDate());
        return customerRepo.save(customer);
    }

    @Override
    public boolean deleteCustomer(long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setActive(false);  // Soft delete
        customerRepo.save(customer);
        return true;
        }

    @Override
    public Customer getCustomerById(Long id) {
        logger.info("Fetching customer with ID: {}", id);

        return customerRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Customer with ID {} not found", id);
                    return new CustomerNotFoundException("Customer with ID " + id + " not found");
                });
    }


}



