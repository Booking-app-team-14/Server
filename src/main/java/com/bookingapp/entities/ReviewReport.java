package com.bookingapp.entities;

import com.bookingapp.enums.ReportStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @OneToOne
    private AccommodationReview accommodationReview;

    private String reason;

    @NotNull
    @Column(nullable = false)
    private ReportStatus status;

    @NotNull
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
