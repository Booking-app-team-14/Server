package com.bookingapp.dtos;

import com.bookingapp.entities.ReviewReport;
import com.bookingapp.enums.ReportStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewReportDTO {

    private Long id;

    private Long accommodationReviewId;

    private String reason;

    private ReportStatus status;

    private LocalDateTime sentAt;

    public ReviewReportDTO(ReviewReport reviewReport) {
        this.id = reviewReport.getId();
        this.accommodationReviewId = reviewReport.getAccommodationReview().getId();
        this.reason = reviewReport.getReason();
        this.status = reviewReport.getStatus();
        this.sentAt = reviewReport.getSentAt();
    }

    public ReviewReportDTO(Long id, Long accommodationReviewId, String reason , ReportStatus status, LocalDateTime sentAt) {
        this.id = id;
        this.accommodationReviewId = accommodationReviewId;
        this.reason = reason;
        this.status = status;
        this.sentAt = sentAt;
    }

}
