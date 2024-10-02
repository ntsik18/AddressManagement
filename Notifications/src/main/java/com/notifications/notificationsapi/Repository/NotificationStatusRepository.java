package com.notifications.notificationsapi.Repository;

import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Models.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationStatusRepository extends JpaRepository<NotificationStatus, Long> {

    Optional<List<NotificationStatus>> findByCustomer (Customer customer);
}
