package com.bookingapp.dtos;

import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.UserAccountService;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.io.IOException;
import java.time.LocalDate;

@Getter
@Setter
public class ReservationRequestDTO {

    private ImagesRepository imagesRepository = new ImagesRepository();

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

    public ReservationRequestDTO(ReservationRequest r, UserAccountService userAccountService, AccommodationService accommodationService) {
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

        String accommodationImagePath = accommodationService.findAccommodationImageName(this.accommodationId);
        //User user = (User) userAccountService.findByUsername(userUsername);

        try {
            this.mainPictureBytes = imagesRepository.getImageBytes(accommodationImagePath);
            this.imageType = imagesRepository.getImageType(this.mainPictureBytes);

            this.userProfilePictureBytes = userAccountService.getUserImage(this.guestId);
            this.userImageType = imagesRepository.getImageType(this.userProfilePictureBytes);
        } catch (IOException ignored) { }
    }
}

