package com.notifications.notificationsapi.Controller;


import com.notifications.notificationsapi.Models.Preferences;
import com.notifications.notificationsapi.Service.PreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class PreferencesController {
    private final PreferencesService preferencesService;


    @PutMapping("/{customerId}/preferences")
    public ResponseEntity<Void> updatePreferences(
            @PathVariable Long customerId,
            @RequestBody Preferences preferences) {
        preferencesService.updatePreferences(customerId, preferences);
        return ResponseEntity.noContent().build();
    }


}
