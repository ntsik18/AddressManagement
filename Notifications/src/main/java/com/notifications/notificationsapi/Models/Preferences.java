package com.notifications.notificationsapi.Models;

import jakarta.persistence.Embeddable;
import lombok.*;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preferences {

    private boolean optInSMS;
    private boolean optInEmail;
    private boolean optInPromotional;


}
