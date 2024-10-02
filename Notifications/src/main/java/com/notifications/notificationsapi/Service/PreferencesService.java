package com.notifications.notificationsapi.Service;

import com.notifications.notificationsapi.Exceptions.ResourceNotFoundException;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Models.Preferences;
import com.notifications.notificationsapi.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferencesService {

    private final CustomerRepository customerRepository;

    @Transactional
    public void updatePreferences(Long customerId, Preferences preferences) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        customer.setPreferences(preferences);
        customerRepository.save(customer);
    }

    public Preferences getPreferences(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return customer.getPreferences();
    }
}
