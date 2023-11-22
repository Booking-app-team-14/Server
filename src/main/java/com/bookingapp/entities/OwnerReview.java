package com.bookingapp.entities;

import com.bookingapp.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class OwnerReview {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private UserAccount user;

    @OneToOne(cascade = CascadeType.ALL)
    private UserAccount owner;

    private Integer rating;

    private String comment;

    @Column(nullable = false)
    private ReviewStatus status;

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
