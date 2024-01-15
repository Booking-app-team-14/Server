package com.bookingapp.dtos;

import com.bookingapp.entities.OwnerReviewReport;
import com.bookingapp.enums.ReportStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OwnerReviewReportDTO {

    private Long id;

    private Long reviewId;

    private String reason;

    private ReportStatus status;

    private LocalDateTime sentAt;

    public OwnerReviewReportDTO(OwnerReviewReport reviewReport) {
        this.id = reviewReport.getId();
        this.reviewId = reviewReport.getReview().getId();
        this.reason = reviewReport.getReason();
        this.status = reviewReport.getStatus();
        this.sentAt = reviewReport.getSentAt();
    }

    public OwnerReviewReportDTO( Long reviewId, String reason , ReportStatus status, LocalDateTime sentAt) {

        this.reviewId = reviewId;
        this.reason = reason;
        this.status = status;
        this.sentAt = LocalDateTime.now();
    }

    public OwnerReviewReportDTO() {

    }

}
