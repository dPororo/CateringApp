package com.enigma.chateringapp.service.impl;

import com.enigma.chateringapp.model.entity.Customer;
import com.enigma.chateringapp.model.request.CustomerRequest;
import com.enigma.chateringapp.model.response.CustomerResponse;
import com.enigma.chateringapp.repository.CustomerRepository;
import com.enigma.chateringapp.service.CustomerService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .id(customerRequest.getId())
                .name(customerRequest.getName())
                .address(customerRequest.getAddress())
                .phone(customerRequest.getPhone())
                .build();
        customerRepository.save(customer);
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .build();
    }
    @Override
    public CustomerResponse getById(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .build();
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        Customer currentCustomer = customerRepository.findById(customerRequest.getId()).orElse(null);
        if (currentCustomer != null){
            Customer customer = Customer.builder()
                    .id(customerRequest.getId())
                    .name(customerRequest.getName())
                    .address(customerRequest.getAddress())
                    .phone(customerRequest.getPhone())
                    .build();
            customerRepository.save(customer);
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .address(customer.getAddress())
                    .phone(customer.getPhone())
                    .build();
        }
        return null;
    }

    @Override
    public Page<CustomerResponse> getAllByNameOrAddress(String name, String address, Integer page, Integer size) {
        Specification<Customer> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (address != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + address.toLowerCase() + "%"));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });

        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customers = customerRepository.findAll(specification, pageable);

        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers.getContent()) {
            customerResponses.add(CustomerResponse.builder()
                            .id(customer.getId())
                            .name(customer.getName())
                            .address(customer.getAddress())
                            .phone(customer.getPhone())
                            .build());
        }
        return new PageImpl<>(customerResponses, pageable, customers.getTotalElements());
    }

    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
