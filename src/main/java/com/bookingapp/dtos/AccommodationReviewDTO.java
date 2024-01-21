package com.bookingapp.dtos;

import com.bookingapp.entities.AccommodationReview;
import com.bookingapp.entities.Review;
import com.bookingapp.enums.ReviewStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AccommodationReviewDTO {

    @NotNull
    private Long id;
    @NotNull
    private Long accommodationId;
    @NotNull
    private Long userId;

    private Integer rating;

    private String comment;
    @NotNull
    private ReviewStatus status;
    @NotNull
    private LocalDateTime sentAt;

    public AccommodationReviewDTO(AccommodationReview accommodationReview) {
        this.id = accommodationReview.getId();
        this.accommodationId = accommodationReview.getAccommodation().getId();
        this.userId = accommodationReview.getUser().getId();
        this.rating = accommodationReview.getRating();
        this.comment = accommodationReview.getComment();
        this.status = accommodationReview.getStatus();
        this.sentAt = accommodationReview.getSentAt();
    }

    public AccommodationReviewDTO(Long id, Long accommodationId, Long userId, Integer rating, String comment, ReviewStatus status, LocalDateTime sentAt) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
        this.sentAt = sentAt;
    }

    public AccommodationReviewDTO() {

    }

    public static List<AccommodationReviewDTO> convertToDTO(List<AccommodationReview> accommodationReviews) {
        List<AccommodationReviewDTO> accommodationReviewDTOs = new ArrayList<>();
        for (AccommodationReview accommodationReview : accommodationReviews) {
            accommodationReviewDTOs.add(new AccommodationReviewDTO(accommodationReview));
        }
        return accommodationReviewDTOs;
    }

}
