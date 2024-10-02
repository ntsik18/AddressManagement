package com.notifications.notificationsapi.Mapper;

import com.notifications.notificationsapi.DTO.AddressDTOS.PhoneDto;
import com.notifications.notificationsapi.Models.Address.Phone;
import com.notifications.notificationsapi.Models.AddressType;
import org.springframework.stereotype.Service;

@Service
public class PhoneMapper {

    public Phone requestToPhone(PhoneDto phoneNumberAddressRequestDto) {
        Phone phone = Phone.builder()
                .phone(phoneNumberAddressRequestDto.getPhone())
                .build();
        phone.setAddressType(AddressType.PHONE);
        return phone;

    }
}
