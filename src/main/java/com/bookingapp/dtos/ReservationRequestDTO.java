package com.bookingapp.dtos;

import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.enums.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationRequestDTO {


        private Long guestId;
        private Long accommodationId;
        private LocalDate startDate;
        private LocalDate endDate;
        private int numberOfGuests ;
        private double totalPrice;
        private RequestStatus requestStatus;
        private String name;
        private String type;
        private String userImageType;
        private String userProfilePictureBytes;
        private String userUsername;
        private String dateRequested; // date requested, (epoch seconds)
        private int stars;
        private String imageType; // accommodation main picture type (jpg, png, etc.)
        private String mainPictureBytes;

        public ReservationRequestDTO(Long guestId,Long accommodationId,double totalPrice, LocalDate startDate, LocalDate endDate, int numberOfGuests, RequestStatus status){
            this.guestId = guestId;
            this.accommodationId = accommodationId;
            this.totalPrice = totalPrice;
            this.startDate = startDate;
            this.endDate = endDate;
            this.numberOfGuests = numberOfGuests;
            this.requestStatus = status;
        }

    public ReservationRequestDTO() {

    }

    public ReservationRequestDTO(ReservationRequest r) {
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

