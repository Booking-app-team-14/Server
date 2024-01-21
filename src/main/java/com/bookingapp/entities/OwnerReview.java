package com.bookingapp.entities;

import com.bookingapp.enums.ReviewStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class OwnerReview {

    @Id
    private Long id;

    @NotNull
    @OneToOne
    private UserAccount user;

    @NotNull
    @OneToOne
    private UserAccount owner;

    @Min(value = -1)
    private Integer rating;

    @NotEmpty
    private String comment;

    @NotNull
    @Column(nullable = false)
    private ReviewStatus status;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime sentAt;

    public OwnerReview() {

    }

    public OwnerReview(UserAccount user, UserAccount owner, Integer rating, String comment, LocalDateTime sentAt) {
        this.user = user;
        this.owner = owner;
        this.rating = rating;
        this.comment = comment;
        this.sentAt = sentAt;
    }

}
