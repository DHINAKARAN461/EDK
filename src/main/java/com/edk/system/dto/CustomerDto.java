package com.edk.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDto {

    @Pattern(regexp = "^[0-9]{10}$")
    @Schema(title = "mobile number",example = "9870564321")
    private String phone_no;

    @NotBlank(message = "Enter your name")
    @Size(min = 4,max = 15)
    private String customer_name;

    @NotBlank(message = "Enter your address")
    @Size(min = 10)
    private String customer_address;

    @Email(message = "Enter valid mail address")
    private String mailId;

    @Pattern(regexp = "^[a-zA-z0-9]{8}$",message = "give alpha numeric at least 8")
    private String password;


}
