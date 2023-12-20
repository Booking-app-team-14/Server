package com.bookingapp.dtos;

import com.bookingapp.entities.Request;
import com.bookingapp.enums.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestDTO {


        private Long guestId;
        private Long accommodationId;
        private LocalDate startDate;
        private LocalDate endDate;
        private int numberOfGuests ;
        private double totalPrice;
        private RequestStatus requestStatus;

        public RequestDTO(Long guestId,Long accommodationId,double totalPrice, LocalDate startDate, LocalDate endDate, int numberOfGuests, RequestStatus status){
            this.guestId = guestId;
            this.accommodationId = accommodationId;
            this.totalPrice = totalPrice;
            this.startDate = startDate;
            this.endDate = endDate;
            this.numberOfGuests = numberOfGuests;
            this.requestStatus = status;
        }

    public RequestDTO() {

    }

    public RequestDTO(Request r) {
            this.guestId = r.getUserId();
            this.accommodationId = r.getAccommodationId();
            this.requestStatus = r.getRequestStatus();
            this.totalPrice = r.getTotalPrice();
            this.numberOfGuests = r.getNumberOfGuests();
            this.startDate = r.getStartDate();
            this.endDate = r.getEndDate();
    }
}

