package com.bookingapp.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotNull
    private Long id;
    @Size(min = 5, max = 50)
    private String username;
    @Size(min = 8)
    private String password;
    @Size(min = 5, max = 50)
    private String firstname;
    @Size(min = 5, max = 50)
    private String lastname;
    @Size(min = 5, max = 50)
    private String email;

}

