package com.notifications.notificationsapi.Models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.notifications.notificationsapi.Models.Address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Embedded
    @JsonDeserialize
    private Preferences preferences;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Address> addresses;

}


