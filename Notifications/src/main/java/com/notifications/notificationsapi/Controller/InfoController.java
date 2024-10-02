package com.notifications.notificationsapi.Controller;


import com.notifications.notificationsapi.DTO.CustomerSearchCriteria;
import com.notifications.notificationsapi.Models.Address.Address;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Models.Preferences;
import com.notifications.notificationsapi.SearchUtill.SearchService;
import com.notifications.notificationsapi.Service.AddressService;
import com.notifications.notificationsapi.Service.CustomerService;
import com.notifications.notificationsapi.Service.PreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/open-data")
@RequiredArgsConstructor
public class InfoController {
    private final AddressService addressService;
    private final CustomerService customerService;
    private final PreferencesService preferencesService;
    private final SearchService searchCustomers;

    // Get All Addresses for All Customer
    @GetMapping("/addresses/all")
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(addressService.displayAllAddresses());
    }

    @GetMapping("/addresses/{customerId}")
    public ResponseEntity<List<Address>> getCustomerAddress(@PathVariable Long customerId) {
        List<Address> addresses = addressService.getAllAddresses(customerId);
        return ResponseEntity.ok(addresses);
    }

    //     Display current notification preferences
    @GetMapping("/customers/{customerId}/preferences")
    public ResponseEntity<Preferences> getPreferences(@PathVariable Long customerId) {
        Preferences preferences = preferencesService.getPreferences(customerId);
        return ResponseEntity.ok(preferences);
    }

    @GetMapping("/customers/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Boolean SMS,
            @RequestParam(required = false) Boolean optEmail,
            @RequestParam(required = false) Boolean promotional
    ) {
        CustomerSearchCriteria criteria = new CustomerSearchCriteria(firstName, lastName, email,
                phone, SMS, optEmail, promotional);
        List<Customer> customers = searchCustomers.searchAndFilterCustomers(criteria);
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
