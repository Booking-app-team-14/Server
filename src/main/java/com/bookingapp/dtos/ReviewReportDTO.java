package com.bookingapp.dtos;

import com.bookingapp.entities.ReviewReport;
import com.bookingapp.enums.ReportStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewReportDTO {

    @NotNull
    private Long id;
    @NotNull
    private Long accommodationReviewId;
    @NotEmpty
    private String reason;
    @NotNull
    private ReportStatus status;
    @NotNull
    private LocalDateTime sentAt;

    public ReviewReportDTO(ReviewReport reviewReport) {
        this.id = reviewReport.getId();
        this.accommodationReviewId = reviewReport.getAccommodationReview().getId();
        this.reason = reviewReport.getReason();
        this.status = reviewReport.getStatus();
        this.sentAt = reviewReport.getSentAt();
    }

    public ReviewReportDTO( Long accommodationReviewId, String reason , ReportStatus status, LocalDateTime sentAt) {

        this.accommodationReviewId = accommodationReviewId;
        this.reason = reason;
        this.status = status;
        this.sentAt = LocalDateTime.now();
    }

    public ReviewReportDTO() {

    }

}
