package com.notifications.notificationsapi.Repository;

import com.notifications.notificationsapi.Models.Address.Postal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostalRepository extends JpaRepository<Postal, Long> {
}
