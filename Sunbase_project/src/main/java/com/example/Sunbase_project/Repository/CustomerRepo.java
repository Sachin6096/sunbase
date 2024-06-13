package com.example.Sunbase_project.Repository;

import com.example.Sunbase_project.Model.Customer;
import com.example.Sunbase_project.RequestDTO.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CustomerRepo extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
}
