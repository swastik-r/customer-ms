package application.service;

import application.model.Customer;
import application.model.MembershipTier;
import org.springframework.stereotype.Service;
import application.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean checkMobileExists(String mobile) {
        return customerRepository.findByMobile(mobile).isPresent();
    }
    public Customer login(String mobile, short kpin) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobile(mobile);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if (customer.getKpin() != kpin) {
                // Incorrect KPIN
                throw new IllegalArgumentException("Incorrect KPIN");
            }
            return customer;
        } else {
            // Mobile number not found, so register a new customer
            return register(mobile, firstName, lastName, kpin);
        }
    }

    public Customer register(String mobile, String firstName, String lastName, short kpin) {
        Customer customer = new Customer();
        customer.setKpin(kpin);
        customer.setMobile(mobile);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setMembershipTier(MembershipTier.BRONZE);

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}

