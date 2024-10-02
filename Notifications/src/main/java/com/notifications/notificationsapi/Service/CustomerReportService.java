package com.notifications.notificationsapi.Service;

import com.notifications.notificationsapi.DTO.CustomerOptInReportDTO;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerReportService {

    private final CustomerRepository customerRepository;

    public CustomerOptInReportDTO generateCustomerOptInReport() {
        List<Customer> customers = customerRepository.findAll();

        int totalCustomers = customers.size();
        long SMS = customers.stream().filter(c -> c.getPreferences().isOptInSMS()).count();
        long email = customers.stream().filter(c -> c.getPreferences().isOptInEmail()).count();
        long promotional = customers.stream().filter(c -> c.getPreferences().isOptInPromotional()).count();

        double optedInSMS = totalCustomers > 0 ? (double) SMS / totalCustomers * 100 : 0;
        double optedInEmail = totalCustomers > 0 ? (double) email / totalCustomers * 100 : 0;
        double optedInPromotional = totalCustomers > 0 ? (double) promotional / totalCustomers * 100 : 0;

        return new CustomerOptInReportDTO(totalCustomers, optedInSMS, optedInEmail, optedInPromotional);
    }
}
