package com.bookingapp.dtos;

import com.bookingapp.entities.UserReport;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserReportDTO {

    @NotNull
    private Long id;
    @NotNull
    private Long reportingUserId;
    @NotNull
    private Long reportedUserId;
    @Min(value = 0)
    private Integer reportedUserNumberOfReports;
    @NotEmpty
    private String description;
    @NotNull
    private LocalDateTime sentAt;

    public UserReportDTO(UserReport report) {
        this.id = report.getId();
        this.reportingUserId = report.getReportingUser().getId();
        this.reportedUserId = report.getReportedUser().getId();
        this.reportedUserNumberOfReports = report.getReportedUser().getNumberOfReports();
        this.description = report.getDescription();
        this.sentAt = report.getSentAt();
    }

    public UserReportDTO( Long reportingUserId, Long reportedUserId, Integer reportedUserNumberOfReports, String description, LocalDateTime sentAt) {

        this.reportingUserId = reportingUserId;
        this.reportedUserId = reportedUserId;
        this.reportedUserNumberOfReports = reportedUserNumberOfReports;
        this.description = description;
        this.sentAt = sentAt;
    }

    public UserReportDTO() {
    }

}
