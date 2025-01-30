package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.dto.customer.CustomerRequestDTO;
import com.car_dealership.cars_api.dto.customer.CustomerResponseDTO;
import com.car_dealership.cars_api.models.Customer;
import com.car_dealership.cars_api.repositories.CustomerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<CustomerResponseDTO> saveCustomers(List<CustomerRequestDTO> customersRequest) {
        List<CustomerResponseDTO> customersResponse = new ArrayList<>();
        for (CustomerRequestDTO customer : customersRequest) {
            customersResponse.add(saveCustomer(customer));
        }
        return customersResponse;
    }

    public List<CustomerResponseDTO> getFilteredCustomers(int page,
                                                          int size,
                                                          String first_name,
                                                          String last_name,
                                                          String email,
                                                          String phone_number) {
        Pageable pageable = PageRequest.of(page, size);

        // Ajustar valores vazios para null
//        first_name = first_name.isBlank() ? null : first_name;
//        last_name = last_name.isBlank() ? null : last_name;
//        email = email.isBlank() ? null : email;
//        phone_number = phone_number.isBlank() ? null : phone_number;

        Page<Customer> filteredCustomers = customerRepository.findFilteredCustomer(pageable, first_name, last_name, email, phone_number);
        return filteredCustomers.stream().map(customer -> new CustomerResponseDTO(
                    customer.getCustomer_id(),
                    customer.getFirst_name(),
                    customer.getLast_name(),
                    customer.getPhone_number(),
                    customer.getEmail())
        ).toList();
    }
}
