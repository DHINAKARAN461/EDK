package com.edk.system.service;

import com.edk.system.dto.CustomerDto;
import com.edk.system.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {

    boolean checkPhoneMail(String phone, String mailID);
    boolean isAvailableMail(String mail);
    ResponseEntity<String> isValidCredential(String mail, String pwd);
    ResponseEntity<String> createCustomer(CustomerDto customerDto);
    Page<Customer> page(int page, int size);
}
