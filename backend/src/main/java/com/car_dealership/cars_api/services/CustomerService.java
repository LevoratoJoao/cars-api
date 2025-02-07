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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private @NonNull CustomerRepository customerRepository;

    @Async
    public CompletableFuture<List<CustomerResponseDTO>> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        return CompletableFuture.completedFuture(allCustomers.stream().map(customer -> new CustomerResponseDTO(
                customer.getCustomer_id(),
                customer.getFirst_name(),
                customer.getLast_name(),
                customer.getPhone_number(),
                customer.getEmail()
        )).toList());
    }

    @Async
    public CompletableFuture<CustomerResponseDTO> getCustomerById(Integer id) {
        Optional<Customer> customerExists = customerRepository.findById(id);
        if (customerExists.isPresent()) {
            return CompletableFuture.completedFuture(new CustomerResponseDTO(
                    customerExists.get().getCustomer_id(),
                    customerExists.get().getFirst_name(),
                    customerExists.get().getLast_name(),
                    customerExists.get().getPhone_number(),
                    customerExists.get().getEmail()
            ));
        }
        System.out.println("Customer with id { " + id + " } was not found");
        return CompletableFuture.completedFuture(null);
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

    @Async
    public CompletableFuture<List<CustomerResponseDTO>> getFilteredCustomers(Integer page,
                                                                             Integer size,
                                                                             String first_name,
                                                                             String last_name,
                                                                             String email,
                                                                             String phone_number) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> filteredCustomers = customerRepository.findFilteredCustomer(pageable, first_name, last_name, email, phone_number);
        return CompletableFuture.completedFuture(filteredCustomers.stream().map(customer -> new CustomerResponseDTO(
                customer.getCustomer_id(),
                customer.getFirst_name(),
                customer.getLast_name(),
                customer.getPhone_number(),
                customer.getEmail())
        ).toList());
    }

    @Transactional
    public CompletableFuture<Customer> updateCustomer(Integer customerId, CustomerRequestDTO updateCustomer) throws Exception {
        try {
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("Customer not found"));

            customer.setFirst_name(updateCustomer.first_name());
            customer.setLast_name(updateCustomer.last_name());
            customer.setPhone_number(updateCustomer.phone_number());
            customer.setEmail(updateCustomer.email());

            Customer savedCustomer = customerRepository.save(customer);

            return CompletableFuture.completedFuture(savedCustomer);
        } catch (Exception e) {
            throw new Exception("The customer was updated by another user. Please reload and try again.");
        }

    }
}
