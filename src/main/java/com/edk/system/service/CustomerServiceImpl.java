package com.edk.system.service;

import com.edk.system.dto.CustomerDto;
import com.edk.system.entity.Customer;
import com.edk.system.exception.MailIdException;
import com.edk.system.exception.PhoneNumberException;
import com.edk.system.repository.CustomerRepository;
import com.edk.system.security.SecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService, UserDetailsService {


    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkPhoneMail(String phone,String mailID){
       return customerRepository.isPhoneAndMailExists(phone, mailID) == 1;
    }

    @Override
    public boolean isAvailableMail(String mail){
        return customerRepository.isMailExist(mail)==1;
    }

    @Override
    public ResponseEntity<String> isValidCredential(String mail,String pwd){

        if (customerRepository.isValidCredential(mail,passwordEncoder.encode(pwd))==1)
            return new ResponseEntity<>(" Log in successfully",HttpStatus.OK);
        return new ResponseEntity<>("invalid mail or password",HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> createCustomer(CustomerDto customerDto){
        if(customerRepository.existsById(customerDto.getPhone_no()))
            throw new PhoneNumberException("This number "+customerDto.getPhone_no()+" is already registered");

        if(customerRepository.isMailExist(customerDto.getMailId())==1)
            throw new MailIdException("This mail id "+customerDto.getMailId()+" is already exits");
        Customer customerDetails = new Customer();
        customerDetails.setPhone_no(customerDto.getPhone_no());
        customerDetails.setCustomername(customerDto.getCustomer_name());
        customerDetails.setCustomer_address(customerDto.getCustomer_address());
        customerDetails.setMailId(customerDto.getMailId());
        customerDetails.setPassword(passwordEncoder.encode(customerDto.getPassword()).substring(1,8));
        return new ResponseEntity<>(customerRepository.save(customerDetails).getCustomername(),HttpStatus.CREATED);
    }

    @Override
    public Page<Customer> page(int page,int size){
        Pageable customerPage =PageRequest.of(page,size);

        return customerRepository.findAll(customerPage);
    }

    public String log_in(){

        return "";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByCustomername(username)
                .orElseThrow(()->new UsernameNotFoundException(username));
        return new User(customer.getCustomername(), customer.getPassword(), List.of());
    }
}

