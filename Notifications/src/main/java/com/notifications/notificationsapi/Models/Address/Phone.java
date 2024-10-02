package com.notifications.notificationsapi.Models.Address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Phone extends Address {
    @NotBlank
    @Column(unique = true)
    private String phone;
}
