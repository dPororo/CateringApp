package com.enigma.chateringapp.service;

import com.enigma.chateringapp.model.entity.Customer;
import com.enigma.chateringapp.model.request.CustomerRequest;
import com.enigma.chateringapp.model.response.CustomerResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponse create (CustomerRequest customerRequest);
    CustomerResponse getById (String id);
    CustomerResponse update(CustomerRequest customerRequest);
    Page<CustomerResponse> getAllByNameOrAddress(String name, String address, Integer page, Integer size);
    void delete(String id);
    Customer save(Customer customer);
}
