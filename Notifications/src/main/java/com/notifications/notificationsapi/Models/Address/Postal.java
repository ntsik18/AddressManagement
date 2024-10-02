package com.notifications.notificationsapi.Models.Address;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Postal extends Address {

    @NotBlank
    private String street;  // Street name and number

    @NotBlank
    private String city;

    @NotBlank
    private String postalCode;
    @NotBlank
    private String country;


}
