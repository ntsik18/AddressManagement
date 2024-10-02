package com.notifications.notificationsapi.SearchUtill;


import com.notifications.notificationsapi.Models.Address.Address;
import com.notifications.notificationsapi.Models.Address.Email;
import com.notifications.notificationsapi.Models.Address.Phone;
import com.notifications.notificationsapi.Models.Address.Postal;
import com.notifications.notificationsapi.Models.AddressType;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchMapper {
    private final CustomerService customerService;

    public SearchResponseDTO toSearchResponse(Customer customer) {
        List<Address> addresses = customer.getAddresses();

        Email existingEmail = (Email) customerService.findAddressByType(addresses, AddressType.EMAIL);
        Phone existingPhone = (Phone) customerService.findAddressByType(addresses, AddressType.PHONE);

        return SearchResponseDTO.builder()
                .id(customer.getId())
                .lastName(customer.getLastName())
                .firstName(customer.getFirstName())
                .email(existingEmail.getEmail())
                .phone(existingPhone.getPhone())
                .city(null)
                .country(null)
                .postalCode(null)
                .street(null)
                .optInEmail(customer.getPreferences().isOptInEmail())
                .optInPromotional(customer.getPreferences().isOptInPromotional())
                .optInSMS(customer.getPreferences().isOptInSMS())
                .build();


    }

    public SearchResponseDTO toSingleSearchResponse(Customer customer) {
        Postal existingPosal = (Postal) customer.getAddresses().stream()
                .filter(address -> address.getAddressType().equals(AddressType.POSTAL))  // Check for PHONE type
                .findFirst()
                .orElse(null);
        if (existingPosal == null) {
            return toSearchResponse(customer);
        } else {
            List<Address> addresses = customer.getAddresses();
            Email existingEmail = (Email) customerService.findAddressByType(addresses, AddressType.EMAIL);
            Phone existingPhone = (Phone) customerService.findAddressByType(addresses, AddressType.PHONE);
            return SearchResponseDTO.builder()
                    .id(customer.getId())
                    .lastName(customer.getLastName())
                    .firstName(customer.getFirstName())
                    .email(existingEmail.getEmail())
                    .phone(existingPhone.getPhone())
                    .city(existingPosal.getCity())
                    .country(existingPosal.getCountry())
                    .postalCode(existingPosal.getPostalCode())
                    .street(existingPosal.getStreet())
                    .optInEmail(customer.getPreferences().isOptInEmail())
                    .optInPromotional(customer.getPreferences().isOptInPromotional())
                    .optInSMS(customer.getPreferences().isOptInSMS())
                    .build();
        }
    }

}
