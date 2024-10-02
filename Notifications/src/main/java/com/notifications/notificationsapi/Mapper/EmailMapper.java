package com.notifications.notificationsapi.Mapper;

import com.notifications.notificationsapi.DTO.AddressDTOS.EmailDto;
import com.notifications.notificationsapi.Models.Address.Email;
import com.notifications.notificationsapi.Models.AddressType;
import org.springframework.stereotype.Service;

@Service
public class EmailMapper {
    public Email requestToEmail(EmailDto emailAddressRequestDto) {
        Email email = Email.builder()
                .email(emailAddressRequestDto.getEmail())

                .build();
        email.setAddressType(AddressType.EMAIL);

        return email;

    }
}
