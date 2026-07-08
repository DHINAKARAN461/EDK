package com.edk.system.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Table(name = "customer_details")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Column(unique = true,nullable = false)
    @Schema(name = "Phone no : ",example = "9842198321")
    String phone_no;

    @Column(name = "customer_name",nullable = false,length = 15)
    @Schema(name = "Customer name",example = "dhina")
    String customername;

    @Column(nullable = false)
    String customer_address;

    @Column(unique = true,nullable = false)
    @Schema(name = "Email",example = "abc@gmail.com")
    String mailId;

    @Column(nullable = false,length = 8)
    @Schema(name = "Password",example = "a2b5c@42")
    String password;

}
