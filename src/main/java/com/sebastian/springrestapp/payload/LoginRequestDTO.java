package com.sebastian.springrestapp.payload;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;


@Builder
@Value
public class LoginRequestDTO {

    @NotBlank(message = "You need to pass user name")
    String user;
    @NotBlank(message = "Password can't be empty")
    String password;

}
