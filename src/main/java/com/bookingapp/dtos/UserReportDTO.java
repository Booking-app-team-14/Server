package com.bookingapp.dtos;

import com.bookingapp.entities.UserReport;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserReportDTO {

    private Long id;

    private Long reportingUserId;

    private Long reportedUserId;

    private String reportingUserUsername;

    private String reportedUserUsername;

    private Integer reportedUserNumberOfReports;

    private String description;

    private LocalDateTime sentAt;

    public UserReportDTO(UserReport report) {
        this.id = report.getId();
        this.reportingUserId = report.getReportingUser().getId();
        this.reportedUserId = report.getReportedUser().getId();
        this.reportingUserUsername = report.getReportingUser().getUsername();
        this.reportedUserUsername = report.getReportedUser().getUsername();
        this.reportedUserNumberOfReports = report.getReportedUser().getNumberOfReports();
        this.description = report.getDescription();
        this.sentAt = report.getSentAt();
    }

    public UserReportDTO( Long reportingUserId, Long reportedUserId, String reportingUserUsername, String reportedUserUsername, Integer reportedUserNumberOfReports, String description, LocalDateTime sentAt) {

        this.reportingUserId = reportingUserId;
        this.reportedUserId = reportedUserId;
        this.reportingUserUsername = reportingUserUsername;
        this.reportedUserUsername = reportedUserUsername;
        this.reportedUserNumberOfReports = reportedUserNumberOfReports;
        this.description = description;
        this.sentAt = sentAt;
    }

    public UserReportDTO() {
    }

}
