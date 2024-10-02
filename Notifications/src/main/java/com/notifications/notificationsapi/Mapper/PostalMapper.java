package com.notifications.notificationsapi.Mapper;

import com.notifications.notificationsapi.DTO.AddressDTOS.PostalDto;
import com.notifications.notificationsapi.Models.Address.Postal;
import com.notifications.notificationsapi.Models.AddressType;
import org.springframework.stereotype.Service;

@Service
public class PostalMapper {

    public Postal requestToPostal(PostalDto requestDto) {
        Postal postal = Postal.builder()
                .city(requestDto.getCity())
                .postalCode(requestDto.getPostalCode())
                .country(requestDto.getCountry())
                .street(requestDto.getStreet())
                .build();
        postal.setAddressType(AddressType.POSTAL);
        return postal;

    }
}
