package com.edk.system.repository;

import com.edk.system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,String> {

    @Query(value = "select EXISTS(select 1 from customer_details where mail_id = :mailId)",nativeQuery = true)
    int isMailExist(@Param("mailId") String mailId);
    @Query(value = "select EXISTS(select 1 from customer_details where phone_no =:phone and mail_id = :mail)",nativeQuery = true)
    int isPhoneAndMailExists(@Param("phone")String phone,@Param("mail")String mail);
    @Query(value = "select EXISTS(select 1 from customer_details where password =:pwd)",nativeQuery = true)
    int isCorrectPassword(@Param("pwd") String pwd);
    @Query(value = "select EXISTS(select 1 from customer_details where mail_id = :mail and password = :pwd)",nativeQuery = true)
    int isValidCredential(@Param("mail")String mailId,@Param("pwd") String pwd);

    Optional<Customer> findByCustomername(String customername);
}
