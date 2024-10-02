package com.notifications.notificationsapi.BatchProcessor;


import com.notifications.notificationsapi.Models.Address.Address;
import com.notifications.notificationsapi.Models.Address.Email;
import com.notifications.notificationsapi.Models.Address.Phone;
import com.notifications.notificationsapi.Models.AddressType;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Models.Preferences;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class CustomerBatchProcessor implements ItemProcessor<CustomerBatchDTO, Customer> {
    @Override
    public Customer process(CustomerBatchDTO item) throws Exception {
        Preferences preferences = Preferences.builder()
                .optInPromotional(item.isOptInPromotional())
                .optInEmail(item.isOptInEmail())
                .optInSMS(item.isOptInSMS())
                .build();
        List<Address> addressList = new ArrayList<>();
        Email email = Email.builder()
                .email(item.getEmail())
                .build();
        email.setAddressType(AddressType.EMAIL);
        Phone phone = Phone.builder()
                .phone(item.getPhone())
                .build();
        phone.setAddressType(AddressType.PHONE);
        addressList.add(phone);
        addressList.add(email);

        Customer customer = Customer.builder()
                .firstName(item.getFirstName())
                .lastName(item.getLastName())
                .preferences(preferences)
                .addresses(addressList)
                .build();
        phone.setCustomer(customer);
        email.setCustomer(customer);
        return customer;

    }
}
