package com.bookingapp.dtos;

import com.bookingapp.entities.OwnerReviewReport;
import com.bookingapp.enums.ReportStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OwnerReviewReportDTO {

    @NotNull
    private Long id;
    @NotNull
    private Long reviewId;
    @NotEmpty
    private String reason;
    @NotNull
    private ReportStatus status;
    @NotNull
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
