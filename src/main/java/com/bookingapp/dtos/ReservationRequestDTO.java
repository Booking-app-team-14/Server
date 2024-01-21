package com.bookingapp.dtos;

import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.UserAccountService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.io.IOException;
import java.time.LocalDate;

@Getter
@Setter
public class ReservationRequestDTO {

    @NotNull
    private Long id;
    @NotNull
    private Long guestId;
    @NotNull
    private Long accommodationId;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Min(value = 1)
    private int numberOfGuests;
    @Min(value = 1)
    private double totalPrice;
    @NotNull
    private RequestStatus requestStatus;
    @Size(min = 5, max = 100)
    private String name;
    @NotEmpty
    private String type;
    private String userImageType;
    private String userProfilePictureBytes;
    @NotEmpty
    private String userUsername;
    @NotEmpty
    private String dateRequested; // date requested, (epoch seconds)
    @Min(value = -1)
    private int stars;
    private String imageType; // accommodation main picture type (jpg, png, etc.)
    private String mainPictureBytes;

    public ReservationRequestDTO(Long guestId,Long accommodationId, double totalPrice, LocalDate startDate, LocalDate endDate, int numberOfGuests, String ownerUsername){
        this.guestId = guestId;
        this.accommodationId = accommodationId;
        this.totalPrice = totalPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;

        this.id = -1L;
        this.requestStatus = RequestStatus.SENT;
        this.name = "AAAAAAAAAA";
        this.type = "AAAAAAAAAA";
        this.userImageType = "AAAAAAAAAA";
        this.userProfilePictureBytes = "AAAAAAAAAA";
        this.userUsername = ownerUsername;
        this.dateRequested = "AAAAAAAAAA";
        this.stars = -1;
        this.imageType = "AAAAAAAAAA";
        this.mainPictureBytes = "AAAAAAAAAA";
    }

    public ReservationRequestDTO() {

    }

    public ReservationRequestDTO(ReservationRequest r) {
        this.id = r.getId();
        this.guestId = r.getUserId();
        this.accommodationId = r.getAccommodationId();
        this.requestStatus = r.getRequestStatus();
        this.totalPrice = r.getTotalPrice();
        this.numberOfGuests = r.getNumberOfGuests();
        this.startDate = r.getStartDate();
        this.endDate = r.getEndDate();
        this.dateRequested = r.getDateRequested();
        this.name = r.getName();
        this.type = r.getType();
        this.userUsername =r.getUserUsername();
        this.stars = r.getStars();
    }

}

