package com.bookingapp.entities;

import com.bookingapp.dtos.ReservationRequestDTO;
import com.bookingapp.enums.RequestStatus;
import jakarta.persistence.*;
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

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
 //   private Guest from;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long accommodationId;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int numberOfGuests;

    @Column(nullable = false)
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus requestStatus;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String userImageType;
    @Column(nullable = false)
    @Lob
    private String userProfilePictureBytes;
    @Column(nullable = false)
    private String userUsername;
    @Column(nullable = false)
    private String dateRequested; // date requested, (epoch seconds)
    @Column(nullable = false)
    private int stars;
    @Column(nullable = false)
    private String imageType; // accommodation main picture type (jpg, png, etc.)
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
