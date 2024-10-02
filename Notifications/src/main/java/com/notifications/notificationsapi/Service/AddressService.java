package com.notifications.notificationsapi.Service;


import com.notifications.notificationsapi.DTO.AddressDTOS.*;
import com.notifications.notificationsapi.Exceptions.DuplicateAddressException;
import com.notifications.notificationsapi.Exceptions.ResourceNotFoundException;
import com.notifications.notificationsapi.Mapper.EmailMapper;
import com.notifications.notificationsapi.Mapper.PhoneMapper;
import com.notifications.notificationsapi.Mapper.PostalMapper;
import com.notifications.notificationsapi.Models.Address.Address;
import com.notifications.notificationsapi.Models.Address.Email;
import com.notifications.notificationsapi.Models.Address.Phone;
import com.notifications.notificationsapi.Models.Address.Postal;
import com.notifications.notificationsapi.Models.AddressType;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Repository.AddressRepository;
import com.notifications.notificationsapi.Repository.CustomerRepository;
import com.notifications.notificationsapi.Repository.PostalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final EmailMapper emailMapper;
    private final PhoneMapper phoneMapper;
    private final PostalMapper postalMapper;
    private final CustomerRepository customerRepository;
    private final PostalRepository postalRepository;


    public Address createEmail(EmailDto emailDTO, Customer customer) {
        Email email = emailMapper.requestToEmail(emailDTO);
        email.setCustomer(customer);
        return email;

    }

    public void updateEmail(EmailUpdateDTO emailDTO) {
        // Find the existing email by the new email address
        Email existingEmail = addressRepository.findByEmail(emailDTO.getExistingEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email not found: " + emailDTO.getExistingEmail()));

        // Validate the new email against existing emails
        if (addressRepository.findByEmail(emailDTO.getNewEmail()).isPresent()) {
            throw new DuplicateAddressException("Email already exists: " + emailDTO.getNewEmail());
        }
        // Update the email
        existingEmail.setEmail(emailDTO.getNewEmail());
        addressRepository.save(existingEmail);  // Save the updated email to the database
    }


    public Address createPhone(PhoneDto phoneDTO, Customer customer) {
        Phone phone = phoneMapper.requestToPhone(phoneDTO);
        phone.setCustomer(customer);
        return phone;

    }

    public void updatePhone(PhoneUpdateDTO phoneDTO) {
        // Find the existing phone by the new phone address
        Phone existingPhone = addressRepository.findByPhone(phoneDTO.getExistingPhone())
                .orElseThrow(() -> new ResourceNotFoundException("Phone not found: " + phoneDTO.getExistingPhone()));

        // Validate the new phone against existing phone
        if (addressRepository.findByPhone(phoneDTO.getNewPhone()).isPresent()) {
            throw new DuplicateAddressException("Phone already exists: " + phoneDTO.getNewPhone());
        }
        // Update the phone
        existingPhone.setPhone(phoneDTO.getNewPhone());
        addressRepository.save(existingPhone);  // Save the updated email to the database
    }

    public Address createPostal(PostalDto requestDto, Customer customer) {
        Postal postal = postalMapper.requestToPostal(requestDto);
        postal.setCustomer(customer);
        return postal;
    }

    public void updatePostal(PostalDto postalDTO, Long customerId) {
        Customer customer = findCustomer(customerId);
        Postal existingPostal = (Postal) customer.getAddresses().stream()
                .filter(address -> address.getAddressType().equals(AddressType.POSTAL))  // Check for POSTAL type
                .findFirst()
                .orElse(null);

        if (existingPostal != null) {
            existingPostal.setStreet(postalDTO.getStreet());
            existingPostal.setCity(postalDTO.getCity());
            existingPostal.setPostalCode(postalDTO.getPostalCode());
            existingPostal.setCountry(postalDTO.getCountry());
            addressRepository.save(existingPostal);
        } else {
            Postal newPostal = postalMapper.requestToPostal(postalDTO);
            customer.getAddresses().add(newPostal);
            addressRepository.save(newPostal);
        }
    }

    public void deleteEmailAddress(EmailDto emailAddressDto) {
        Email email = addressRepository.findByEmail(emailAddressDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email not found: " + emailAddressDto.getEmail()));
        customerRepository.delete(email.getCustomer());
        addressRepository.delete(email);

    }

    public void deletePhoneAddress(PhoneDto phoneNumberDto) {
        Phone phone = addressRepository.findByPhone(phoneNumberDto.getPhone())
                .orElseThrow(() -> new ResourceNotFoundException("Phone not found: " + phoneNumberDto.getPhone()));
        customerRepository.delete(phone.getCustomer());
        addressRepository.delete(phone);

    }

    public void deletePostalAddressByCustomerId(Long customerId) {
        List<Address> addresses = getAllAddresses(customerId);
        Postal postal = (Postal) findAddressByTypeAS(addresses, AddressType.POSTAL);
        addresses.remove(postal);
        postalRepository.delete(postal);

    }


    private Customer findCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        return customer;
    }

    public List<Address> getAllAddresses(Long customerId) {
        Customer customer = findCustomer(customerId);
        return customer.getAddresses();
    }

    public List<Address> displayAllAddresses() {
        return addressRepository.findAll();
    }

    private Address findAddressByTypeAS(List<Address> addresses, AddressType addressType) {
        return addresses.stream()
                .filter(address -> address.getAddressType().equals(addressType))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Address of type " + addressType + " not found"));
    }

}
