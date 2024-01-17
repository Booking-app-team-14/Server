package com.bookingapp.entities;

import com.bookingapp.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class OwnerReviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne( )
    private Review review;

    //@Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private ReportStatus status;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    public OwnerReviewReport() {

    }

    public OwnerReviewReport( Review review, ReportStatus status, LocalDateTime sentAt) {
        this.review = review;
        this.status = status;
        this.sentAt = sentAt;
    }

}

