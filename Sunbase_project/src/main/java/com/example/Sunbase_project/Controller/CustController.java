package com.example.Sunbase_project.Controller;

import com.example.Sunbase_project.Exception.CustomerNotFoundException;
import com.example.Sunbase_project.Model.Customer;
import com.example.Sunbase_project.RequestDTO.CustomerDTO;
import com.example.Sunbase_project.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/addcust")
    public String addCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.addCustomer(customerDTO);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity getById(@PathVariable UUID id)throws Exception{
        try{
            Customer customer = customerService.getById(id);
            return new ResponseEntity<>(customer, HttpStatus.FOUND);
        }
        catch (CustomerNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCust(@RequestBody CustomerDTO customerDTO, @PathVariable UUID id)throws Exception{
        try{
            Customer customer1 = customerService.updateCust(customerDTO,id);
            return new ResponseEntity(customer1,HttpStatus.OK);
        }
        catch (CustomerNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable UUID uuid){
        customerService.deleteById(uuid);
    }

    @GetMapping
    public Page<Customer> getAllCustomers(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable paging = PageRequest.of(page, size);
        return customerService.getAllCustomers(search, paging);
    }

}
