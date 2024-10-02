package com.notifications.notificationsapi.Repository;

import com.notifications.notificationsapi.Models.Address.Address;
import com.notifications.notificationsapi.Models.Address.Email;
import com.notifications.notificationsapi.Models.Address.Phone;
import com.notifications.notificationsapi.Models.Address.Postal;
import com.notifications.notificationsapi.Models.AddressType;
import com.notifications.notificationsapi.Models.Customer;
import org.hibernate.jpa.internal.ExceptionMapperLegacyJpaImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository  extends JpaRepository<Address, Long> {

    Optional<Email  > findByEmail (String email);
    Optional<Phone> findByPhone (String phone);
    Optional<Address> findByAddressTypeAndCustomer(AddressType type, Customer customer);

}
