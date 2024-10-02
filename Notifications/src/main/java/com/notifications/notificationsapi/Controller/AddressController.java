package com.notifications.notificationsapi.Controller;

import com.notifications.notificationsapi.DTO.AddressDTOS.*;
import com.notifications.notificationsapi.Service.AddressService;
import com.notifications.notificationsapi.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final CustomerService customerService;
    private final AddressService addressService;

    // Update Email Address, unique per customer
    @PutMapping("/email")
    public ResponseEntity<?> updateEmailAddress(
            @Valid @RequestBody EmailUpdateDTO emailDTO) {
        addressService.updateEmail(emailDTO);
        return ResponseEntity.ok("Email address updated successfully.");
    }

    // Update Phone Number, unique per customer
    @PutMapping("/phone")
    public ResponseEntity<?> updatePhoneNumber(
            @Valid @RequestBody PhoneUpdateDTO phoneDTO) {
        addressService.updatePhone(phoneDTO);
        return ResponseEntity.ok("Phone address updated successfully.");
    }

    // Update Postal Address
    @PutMapping("/{customerId}/postal")
    public ResponseEntity<?> updatePostalAddress(
            @PathVariable Long customerId,
            @Valid @RequestBody PostalDto postalDTO) {
        addressService.updatePostal(postalDTO, customerId);
        return ResponseEntity.ok("Postal address updated successfully.");
    }

    //the endpoint does not include client id as pathVariable for external rest client
    // As customer does not exist without email, this endpoint should also delete associated customer record
    @DeleteMapping("/email")
    public ResponseEntity<String> deleteEmailAddress(
            @Valid @RequestBody EmailDto emailAddressDto) {
        addressService.deleteEmailAddress(emailAddressDto);
        return ResponseEntity.ok(String.format("Customer with email address %s was deleted successfully.", emailAddressDto.getEmail()));
    }

    // As customer does not exist without phone, this endpoint should also delete associated customer record
    @DeleteMapping("/phone")
    public ResponseEntity<String> deletePhoneAddress(
            @Valid @RequestBody PhoneDto phoneNumberDto) {
        addressService.deletePhoneAddress(phoneNumberDto);
        return ResponseEntity.ok(String.format("Customer with phone address %s was deleted successfully.", phoneNumberDto.getPhone()));
    }


    //Postal address is not unique, it has to be deleted based on another unique identifier like customerId, email or phone
    @DeleteMapping("/{customerId}/postal")
    public ResponseEntity<String> deletePostalAddress(
            @PathVariable Long customerId
    ) {
        addressService.deletePostalAddressByCustomerId(customerId);
        return ResponseEntity.ok("Postal address was deleted successfully.");
    }


}
