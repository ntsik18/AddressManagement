package com.notifications.notificationsapi.SearchUtill;


import com.notifications.notificationsapi.DTO.CustomerSearchCriteria;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Repository.AddressRepository;
import com.notifications.notificationsapi.Repository.CustomerRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final CustomerRepository customerRepository;
   // private final AddressRepository addressRepository;
    private final SearchMapper searchMapper;


    public List<SearchResponseDTO> getSearchResults(List<Customer> customers) {
        List<SearchResponseDTO> searchResults = new ArrayList<>();
        for (Customer customer : customers) {
            searchResults.add(searchMapper.toSearchResponse(customer));
        }

        return searchResults;
    }

    public SearchResponseDTO getSearchResponse(Customer customer) {
        return searchMapper.toSingleSearchResponse(customer);
    }


    public List<Customer> searchAndFilterCustomers(CustomerSearchCriteria criteria) {
        Specification<Customer> spec = Specification.where(CustomerSpecifications.firstNameContains(criteria.getFirstName()))
                .and(CustomerSpecifications.lastNameContains(criteria.getLastName()))
                .and(CustomerSpecifications.emailContains(criteria.getEmail()))
                .and(CustomerSpecifications.phoneContains(criteria.getPhone()))
//                .and(CustomerSpecifications.streetContains(criteria.getCi))
//                .and(CustomerSpecifications.cityContains(criteria.getCity()))
//                .and(CustomerSpecifications.postalCodeContains(criteria.getPostalCode()))
//                .and(CustomerSpecifications.countryContains(criteria.getCountry()));
                .and(CustomerSpecifications.optInEmail(criteria.getOptInEmail()))
                .and(CustomerSpecifications.optInPromotional(criteria.getOptInPromotional()))
                .and(CustomerSpecifications.optInSMS(criteria.getOptInSMS()));

        return customerRepository.findAll(spec);
    }

    public List<Customer> filterCustomers(Boolean optInSMS, Boolean optInEmail, Boolean optInPromotional) {
        if (optInSMS == null && optInEmail == null && optInPromotional == null) {
            return customerRepository.findAll(); // This returns all customers
        }

        return customerRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (optInSMS != null) {
                predicates.add(criteriaBuilder.equal(root.get("preferences").get("optInSMS"), optInSMS));
            }
            if (optInEmail != null) {
                predicates.add(criteriaBuilder.equal(root.get("preferences").get("optInEmail"), optInEmail));
            }
            if (optInPromotional != null) {
                predicates.add(criteriaBuilder.equal(root.get("preferences").get("optInPromotional"), optInPromotional));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }


}
