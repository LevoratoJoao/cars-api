package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.dto.customer.CustomerRequestDTO;
import com.car_dealership.cars_api.dto.customer.CustomerResponseDTO;
import com.car_dealership.cars_api.models.customer.Customer;
import com.car_dealership.cars_api.repositories.CustomerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private @NonNull CustomerRepository customerRepository;

    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        return allCustomers.stream().map(customer -> new CustomerResponseDTO(
            customer.getCustomer_id(),
            customer.getFirst_name(),
            customer.getLast_name(),
            customer.getPhone_number(),
            customer.getEmail()
        )).toList();
    }

    public CustomerResponseDTO getCustomerById(Integer id) {
        Optional<Customer> customerExists = customerRepository.findById(id);
        if (customerExists.isPresent()) {
            return new CustomerResponseDTO(
                customerExists.get().getCustomer_id(),
                customerExists.get().getFirst_name(),
                customerExists.get().getLast_name(),
                customerExists.get().getPhone_number(),
                customerExists.get().getEmail()
            );
        }
        System.out.println("Customer with id { " + id + " } was not found");
        return null;
    }

    public CustomerResponseDTO saveCustomer(CustomerRequestDTO customer) {
        Customer newCustomer = new Customer(
            customer.first_name(),
            customer.last_name(),
            customer.phone_number(),
            customer.email()
        );
        customerRepository.save(newCustomer);
        return new CustomerResponseDTO(
            newCustomer.getCustomer_id(),
            newCustomer.getFirst_name(),
            newCustomer.getLast_name(),
            newCustomer.getPhone_number(),
            newCustomer.getEmail()
        );
    }
}
