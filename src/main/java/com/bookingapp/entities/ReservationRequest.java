package com.bookingapp.entities;

import com.bookingapp.dtos.ReservationRequestDTO;
import com.bookingapp.enums.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ReservationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = true)
    private Long reservationId;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    @NotNull
    @Column(nullable = false)
    private Long accommodationId;

    @NotNull
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate endDate;

    @Min(value = 1)
    @Column(nullable = false)
    private int numberOfGuests;

    @Min(value = 1)
    @Column(nullable = false)
    private double totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus requestStatus;

    @Size(min = 5, max = 50)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String type;

    @NotEmpty
    @Column(nullable = false)
    private String userImageType;

    @NotEmpty
    @Column(nullable = false)
    @Lob
    private String userProfilePictureBytes;

    @Size(min = 5, max = 50)
    @Column(nullable = false)
    private String userUsername;

    @NotEmpty
    @Column(nullable = false)
    private String dateRequested; // date requested, (epoch seconds)

    @Min(value = -1)
    @Column(nullable = false)
    private int stars;

    @NotEmpty
    @Column(nullable = false)
    private String imageType; // accommodation main picture type (jpg, png, etc.)

    @NotEmpty
    @Column(nullable = false)
    @Lob
    private String mainPictureBytes;

    public ReservationRequest() {

    }

    public ReservationRequest(ReservationRequestDTO rdto){
        this.userId = rdto.getGuestId();
        this.accommodationId = rdto.getAccommodationId();
        this.requestStatus = rdto.getRequestStatus();
        this.startDate = rdto.getStartDate();
        this.endDate = rdto.getEndDate();
        this.numberOfGuests = rdto.getNumberOfGuests();
        this.totalPrice = rdto.getTotalPrice();
        this.dateRequested = rdto.getDateRequested();
        this.imageType = rdto.getImageType();
        this.mainPictureBytes = rdto.getMainPictureBytes();
        this.stars = rdto.getStars();
        this.name = rdto.getName();
        this.type = rdto.getType();
        this.userUsername = rdto.getUserUsername();
        this.userImageType = rdto.getUserImageType();
        this.userProfilePictureBytes= rdto.getUserProfilePictureBytes();
    }

}
