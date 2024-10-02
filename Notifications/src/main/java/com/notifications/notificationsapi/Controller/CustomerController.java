package com.notifications.notificationsapi.Controller;


import com.notifications.notificationsapi.DTO.CustomerRequestDTO;
import com.notifications.notificationsapi.DTO.CustomerUpdateDTO;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")

public class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody CustomerRequestDTO customerRequest) {
        Customer customer = customerService.addCustomer(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerUpdateDTO updateRequest) {
        Customer updatedCustomer = customerService.updateCustomer(id, updateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }


}
