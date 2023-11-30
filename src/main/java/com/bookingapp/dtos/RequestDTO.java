package com.bookingapp.dtos;

import com.bookingapp.enums.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestDTO {


        private Long guestId;
        private LocalDate startDate;
        private LocalDate endDate;
        private int numberOfGuests;
        private RequestStatus requestStatus;

        public RequestDTO(Long id, LocalDate startDate, LocalDate endDate, int numberOfGuests, RequestStatus status){
            this.guestId = id;
            this.startDate = startDate;
            this.endDate = endDate;
            this.numberOfGuests = numberOfGuests;
            this.requestStatus = status;
        }

    public RequestDTO() {

    }
}

