package com.notifications.notificationsapi.Service;


import com.notifications.notificationsapi.DTO.AddressDTOS.EmailUpdateDTO;
import com.notifications.notificationsapi.DTO.AddressDTOS.PhoneUpdateDTO;
import com.notifications.notificationsapi.DTO.CustomerRequestDTO;
import com.notifications.notificationsapi.DTO.CustomerUpdateDTO;
import com.notifications.notificationsapi.Exceptions.DuplicateAddressException;
import com.notifications.notificationsapi.Exceptions.InvalidCustomerContactException;
import com.notifications.notificationsapi.Exceptions.ResourceNotFoundException;
import com.notifications.notificationsapi.Models.Address.Address;
import com.notifications.notificationsapi.Models.Address.Email;
import com.notifications.notificationsapi.Models.Address.Phone;
import com.notifications.notificationsapi.Models.AddressType;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Repository.AddressRepository;
import com.notifications.notificationsapi.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final PreferencesService preferencesService;


    public Customer addCustomer(CustomerRequestDTO customerRequest) {
        validateContactInformation(customerRequest);
        Customer customer = getCustomerFromDTO(customerRequest);
        List<Address> addresses = new ArrayList<>();
        addresses.add(addressService.createPhone(customerRequest.getPhone(), customer));
        addresses.add(addressService.createEmail(customerRequest.getEmail(), customer));
        if (customerRequest.getPostal() != null) {  //Postal address is not mandatory to be added
            addresses.add(addressService.createPostal(customerRequest.getPostal(), customer));
        }
        customer.setAddresses(addresses);
        try {
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateAddressException("Customer already exists.");
        }
        return customer;
    }


    public Customer updateCustomer(Long customerId, CustomerUpdateDTO updateRequest) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        checkForFieldsToUpdate(updateRequest, customerId, existingCustomer);
        customerRepository.save(existingCustomer);
        return existingCustomer;
    }


    public void deleteCustomer(Long customerId) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        customerRepository.delete(existingCustomer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    private void checkForFieldsToUpdate(CustomerUpdateDTO updateRequest, Long customerId, Customer existingCustomer) {
        if (updateRequest.getFirstName() != null) {
            existingCustomer.setFirstName(updateRequest.getFirstName());
        }

        if (updateRequest.getLastName() != null) {
            existingCustomer.setLastName(updateRequest.getLastName());
        }

        if (updateRequest.getPreferences() != null) {
            preferencesService.updatePreferences(customerId, updateRequest.getPreferences());
        }
        if (updateRequest.getEmail() != null) {
            String email = ((Email) findAddressByType(existingCustomer.getAddresses(), AddressType.EMAIL)).getEmail();
            addressService.updateEmail(new EmailUpdateDTO(
                    email, updateRequest.getEmail().getEmail()));
        }
        if (updateRequest.getPhone() != null) {
            String phone = ((Phone) findAddressByType(existingCustomer.getAddresses(), AddressType.PHONE)).getPhone();
            addressService.updatePhone(new PhoneUpdateDTO(phone, updateRequest.getPhone().getPhone()));
        }
        if (updateRequest.getPostal() != null) {
            addressService.updatePostal(updateRequest.getPostal(), customerId);
        }
    }


    private Customer getCustomerFromDTO(CustomerRequestDTO customerRequest) {
        Customer customer = Customer.builder().
                firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .preferences(customerRequest.getPreferences())
                .build();
        return customer;
    }

    private void validateContactInformation(CustomerRequestDTO customerRequest) {
        if ((customerRequest.getEmail() == null || customerRequest.getEmail().getEmail() == null) &&
                (customerRequest.getPhone() == null || customerRequest.getPhone().getPhone() == null)) {
            throw new InvalidCustomerContactException("At least one of email or phone number must be provided.");
        }
    }

    public Address findAddressByType(List<Address> addresses, AddressType addressType) {
        return addresses.stream()
                .filter(address -> address.getAddressType().equals(addressType))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Address of type " + addressType + " not found"));
    }


}

