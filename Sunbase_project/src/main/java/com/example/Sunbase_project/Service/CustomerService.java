package com.example.Sunbase_project.Service;

import com.example.Sunbase_project.Exception.CustomerNotFoundException;
import com.example.Sunbase_project.Model.Customer;
import com.example.Sunbase_project.Repository.CustomerRepo;
import com.example.Sunbase_project.RequestDTO.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public String addCustomer(CustomerDTO customerDTO) {

        //using builder to create the customer entity from DTO
        Customer customer = Customer.builder()
                .first_name(customerDTO.getFirst_name())
                .last_name(customerDTO.getLast_name())
                .street(customerDTO.getStreet())
                .address(customerDTO.getAddress())
                .city(customerDTO.getCity())
                .email(customerDTO.getEmail())
                .state(customerDTO.getState())
                .phone(customerDTO.getPhone()).build();

        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID());
        }

        customerRepo.save(customer);
        return "Customer has been added!";
    }

    public Customer getById(UUID id) {

        Optional<Customer> optionalCustomer = customerRepo.findById(id);
        if(optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        else{
            throw new CustomerNotFoundException("Please enter a valid id");
        }
    }

    public Customer updateCust(CustomerDTO customerDTO,UUID uuid) {

        Optional<Customer> optionalCustomer = customerRepo.findById(uuid);
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            customer.setAddress(customerDTO.getAddress());
            customer.setCity(customerDTO.getCity());
            customer.setState(customerDTO.getState());
            customer.setStreet(customerDTO.getStreet());
            customer.setFirst_name(customerDTO.getFirst_name());
            customer.setLast_name(customerDTO.getLast_name());
            customer.setEmail(customerDTO.getEmail());
            customer.setPhone(customerDTO.getPhone());
            return customerRepo.save(customer);
        }else {
            throw new CustomerNotFoundException("There is no customer present with given id");
        }
    }

    public void deleteById(UUID uuid) {

        if(customerRepo.existsById(uuid)){
            customerRepo.deleteById(uuid);
        }
    }

    public Page<Customer> getAllCustomers(String search, Pageable paging) {
        Specification<Customer> spec = (root, query, criteriaBuilder) -> {
            if (search != null && !search.isEmpty()) {
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + search.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + search.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + search.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + search.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("state")), "%" + search.toLowerCase() + "%")
                );
            } else {
                return criteriaBuilder.conjunction();
            }
        };
        return customerRepo.findAll(spec,paging);
    }
}
