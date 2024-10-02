package com.notifications.notificationsapi.Models.Address;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.notifications.notificationsapi.Models.AddressType;
import com.notifications.notificationsapi.Models.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
