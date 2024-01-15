package com.bookingapp.entities;

import com.bookingapp.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ReviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne(cascade = CascadeType.ALL)
    private AccommodationReview accommodationReview;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private ReportStatus status;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    public ReviewReport() {

    }

    public ReviewReport(AccommodationReview accommodationReview, ReportStatus status, LocalDateTime sentAt) {
        this.accommodationReview = accommodationReview;
        this.status = status;
        this.sentAt = sentAt;
    }

}
