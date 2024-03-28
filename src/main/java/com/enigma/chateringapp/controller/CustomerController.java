package com.enigma.chateringapp.controller;

import com.enigma.chateringapp.model.entity.Customer;
import com.enigma.chateringapp.model.entity.Menu;
import com.enigma.chateringapp.model.request.CustomerRequest;
import com.enigma.chateringapp.model.response.CommonResponse;
import com.enigma.chateringapp.model.response.CustomerResponse;
import com.enigma.chateringapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    private ResponseEntity<?> create(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.create(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.<CustomerResponse>builder().statusCode(HttpStatus.CREATED.value()).message("Successfully created new customer!").data(customerResponse).build());
    }

    @PutMapping
    private ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.update(customerRequest);
        return ResponseEntity.ok(CommonResponse.<CustomerResponse>builder().statusCode(HttpStatus.OK.value()).message("Successfully updated customer").data(customerResponse).build());
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.builder().statusCode(HttpStatus.CREATED.value()).message("Successfully get customer by id").data(customerResponse).build());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
        return ResponseEntity.ok(CommonResponse.builder().statusCode(HttpStatus.OK.value()).message("Successfully deleted customer").build());
    }

    @GetMapping("/search")
    private ResponseEntity<?> getAllCustomerPage(@RequestParam(required = false) String name, @RequestParam(required = false) String address, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "3") Integer size) {
        return ResponseEntity.ok(CommonResponse.builder().statusCode(HttpStatus.OK.value()).message("Data customer halaman " + page).data(customerService.getAllByNameOrAddress(name, address, page, size)).build());
    }

}
