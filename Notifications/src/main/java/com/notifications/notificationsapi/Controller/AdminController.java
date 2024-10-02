package com.notifications.notificationsapi.Controller;


import com.notifications.notificationsapi.DTO.CustomerSearchCriteria;
import com.notifications.notificationsapi.SearchUtill.SearchResponseDTO;
import com.notifications.notificationsapi.SearchUtill.SearchService;
import com.notifications.notificationsapi.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final SearchService searchService;
    private final CustomerService customerService;

    @GetMapping("/home")
    public String adminHome(Model model) {
        // You can add attributes to the model if needed
        model.addAttribute("title", "Admin Home Page");
        return "home";
    }

    @GetMapping("/customers/search")
    public String searchUsers(@RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String phone,
                              @RequestParam(required = false) Boolean optInSMS,
                              @RequestParam(required = false) Boolean optInEmail,
                              @RequestParam(required = false) Boolean optInPromotional,
                              Model model) {

        CustomerSearchCriteria criteria = new CustomerSearchCriteria(firstName, lastName, email, phone, optInEmail,
                optInPromotional, optInSMS);
        List<SearchResponseDTO> customers = searchService.getSearchResults(searchService.searchAndFilterCustomers(criteria));
        model.addAttribute("customers", customers);
        return "customerList";
    }

    @GetMapping("/customers/view/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        SearchResponseDTO searchResponseDTO = searchService.getSearchResponse(customerService.getCustomerById(id));
        model.addAttribute("customer", searchResponseDTO);
        return "customerDetails";
    }

    @PostMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("success", "Customer deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting customer: " + e.getMessage());
        }
        return "redirect:/admin/customers/search";
    }


}
