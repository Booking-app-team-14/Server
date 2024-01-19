package com.bookingapp.entities;

import com.bookingapp.dtos.UserDTO;
import com.bookingapp.enums.NotificationType;
import com.bookingapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long Id;

    @Size(min = 5, max = 100)
    @Email
    @Column(nullable = false, unique = true)
    protected String username;

    @NotEmpty
    @Size(min = 8)
    @Column(nullable = false)
    protected String password;

    @Size(min = 5, max = 50)
    @Column(nullable = false)
    protected String firstName;

    @Size(min = 5, max = 50)
    @Column(nullable = false)
    protected String lastName;

    @NotEmpty
    @Column(nullable = false)
    protected String address;

    @NotEmpty
    @Column(nullable = false)
    protected String profilePicturePath;

    @NotEmpty
    @Pattern(regexp = "^\\+\\d{1,2}\\s?\\d{3}\\s?\\d{3}\\s?\\d{4}$")
    @Column(nullable = false)
    protected String phoneNumber;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Role role;

    @Column(nullable = false)
    protected boolean isBlocked;

    @Column(name = "verified", nullable = false, columnDefinition = "boolean default false")
    private boolean verified;

    @Min(value = 0)
    @Column(nullable = false)
    protected int numberOfReports;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Set<NotificationType> notWantedNotificationTypes;

    public UserAccount() {

    }

    public UserAccount(Long id, String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role, boolean isBlocked, boolean verified, int numberOfReports, String profilePicturePath) {
        this.Id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isBlocked = isBlocked;
        this.verified=verified;
        this.numberOfReports = numberOfReports;
        this.profilePicturePath = profilePicturePath;
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
        this.verified=dto.isVerified();
        this.numberOfReports = dto.getNumberOfReports();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
