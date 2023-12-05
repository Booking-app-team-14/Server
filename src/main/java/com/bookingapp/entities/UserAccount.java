package com.bookingapp.entities;

import com.bookingapp.dtos.UserDTO;
import com.bookingapp.enums.Role;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @NotBlank
    @Column(nullable = false)
    private String address;

    @NotBlank
    @Pattern(regexp = "^\\+\\d{1,2}\\s?\\d{3}\\s?\\d{3}\\s?\\d{4}$")
    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean isBlocked;

    @Column(nullable = false)
    private int numberOfReports;
    public UserAccount() {

    }

    public UserAccount(Long id, String username, Integer numberOfReports, Boolean blocked) {
        this.Id = id;
        this.username = username;
        this.numberOfReports = numberOfReports;
        this.isBlocked = blocked;
    }

    public UserAccount(UserDTO dto){
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.address = dto.getAddress();
        this.phoneNumber = dto.getPhoneNumber();
        this.role = dto.getRole();
        this.isBlocked = dto.isBlocked();
        this.numberOfReports = dto.getNumberOfReports();
    }

}
