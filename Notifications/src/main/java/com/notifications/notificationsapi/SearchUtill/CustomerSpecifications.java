package com.notifications.notificationsapi.SearchUtill;


import com.notifications.notificationsapi.Models.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CustomerSpecifications {
    public static Specification<Customer> firstNameContains(String firstName) {
        return (root, query, criteriaBuilder) -> {
            if (firstName == null || firstName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
        };
    }

    public static Specification<Customer> lastNameContains(String lastName) {
        return (root, query, criteriaBuilder) -> {
            if (lastName == null || lastName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
        };
    }

    public static Specification<Customer> emailContains(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.join("addresses").get("email"), "%" + email + "%");
        };
    }

    public static Specification<Customer> phoneContains(String phone) {
        return (root, query, criteriaBuilder) -> {
            if (phone == null || phone.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.join("addresses").get("phone"), "%" + phone + "%");
        };
    }

    public static Specification<Customer> streetContains(String street) {
        return (root, query, criteriaBuilder) -> {
            if (street == null || street.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            return criteriaBuilder.like(root.join("addresses").get("street"), "%" + street + "%");
        };
    }

    public static Specification<Customer> cityContains(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            return criteriaBuilder.like(root.join("addresses").get("city"), "%" + city + "%");
        };
    }

    public static Specification<Customer> postalCodeContains(String postalCode) {
        return (root, query, criteriaBuilder) -> {
            if (postalCode == null || postalCode.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            return criteriaBuilder.like(root.join("addresses").get("postalCode"), "%" + postalCode + "%");
        };
    }

    public static Specification<Customer> countryContains(String country) {
        return (root, query, criteriaBuilder) -> {
            if (country == null || country.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            return criteriaBuilder.like(root.join("addresses").get("country"), "%" + country + "%");
        };
    }

    public static Specification<Customer> optInSMS(Boolean optInSMS) {
        return (root, query, criteriaBuilder) -> {
            if (optInSMS == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("preferences").get("optInSMS"), optInSMS);
        };
    }

    public static Specification<Customer> optInEmail(Boolean optInEmail) {
        return (root, query, criteriaBuilder) -> {
            if (optInEmail == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("preferences").get("optInEmail"), optInEmail);
        };
    }

    public static Specification<Customer> optInPromotional(Boolean optInPromotional) {
        return (root, query, criteriaBuilder) -> {
            if (optInPromotional == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("preferences").get("optInPromotional"), optInPromotional);
        };
    }


}
