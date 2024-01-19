package com.bookingapp.dtos;

import com.bookingapp.entities.Review;
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
public class ReviewDTO {

    @NotNull
    private Long id;
    @NotEmpty
    private String comment;
    @Min(value = -1)
    private int rating;
    @NotNull
    private LocalDateTime timestamp;
    private boolean reported;
    private boolean approved;
    @NotNull
    private Long senderId;
    @NotNull
    private Long recipientId;

    public ReviewDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public ReviewDTO(String comment, int rating, Long senderId, Long recipientId) {
        this.comment = comment;
        this.rating = rating;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.timestamp = LocalDateTime.now();
        this.reported = false;
        this.approved = false;
    }

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.timestamp = review.getTimestamp();
        this.reported = review.isReported();
        this.approved = review.isApproved();
        this.senderId = review.getSender().getId();
        this.recipientId = review.getRecipient().getId();
    }

    public static List<ReviewDTO> convertToDTO(List<Review> reviews) {
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOs.add(new ReviewDTO(review));
        }
        return reviewDTOs;
    }

}

