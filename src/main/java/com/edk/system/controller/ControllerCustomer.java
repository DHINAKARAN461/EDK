package com.edk.system.controller;

import com.edk.system.dto.CustomerDto;
import com.edk.system.entity.Customer;
import com.edk.system.service.CustomerService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@Validated
@Slf4j
public class ControllerCustomer {

    private CustomerService customerService;

    ControllerCustomer( CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/sign-in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully signed"),
            @ApiResponse(responseCode = "409",description = "Already available")
    })
    public ResponseEntity<String> createCustomer( @Valid @RequestBody CustomerDto customerDto){

        ResponseEntity<String> response = customerService.createCustomer(customerDto);
        String msg = (response != null)? response.getBody():"";
        return new ResponseEntity<>(msg,response.getStatusCode());
    }

    @GetMapping("/log-in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",description = "There is no user in this user"),
            @ApiResponse(responseCode = "401",description = "password or mail id is miss matching")
    })
    public ResponseEntity<String> login(
            @RequestParam("mail")
            @Email(message = "Enter valid mail address")
            String mailId,
            @RequestParam("password")
            @Size(min = 4,max = 8,message = "Enter minium 4 - 8 character only")
            String pwd){
        ResponseEntity<String> response = customerService.isValidCredential(mailId,pwd);
        String msg = (response!=null)? response.getBody():"";
        return new ResponseEntity<>(msg,response.getStatusCode());
    }

    @GetMapping("/getonpage")
    public ResponseEntity<Page<Customer>> getCustomersOnPage(@RequestParam("page") int page
            ,@RequestParam("size") int size){

        return new ResponseEntity<>(customerService.page(page,size),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<String>home(){
        return ResponseEntity.ok("Home page");
    }

    @GetMapping("/listAll")
    public ResponseEntity<Page<Customer>>listCustomers(){
        return ResponseEntity.ok(customerService.page(0,3)) ;
    }
}
