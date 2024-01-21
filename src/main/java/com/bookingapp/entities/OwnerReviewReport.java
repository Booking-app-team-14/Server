package com.bookingapp.entities;

import com.bookingapp.enums.ReportStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @OneToOne
    private Review review;

    @NotEmpty
    private String reason;

    @NotNull
    @Column(nullable = false)
    private ReportStatus status;

    @NotNull
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

