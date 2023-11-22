package com.bookingapp.models.users;

import com.bookingapp.models.users.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false)
    private boolean isBlocked;


}
