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

    private String reportingUserImagePath;

    private String reportedUserImagePath;

    private Integer reportedUserNumberOfReports;

    private String description;

    private LocalDateTime sentAt;

    public UserReportDTO(UserReport report) {
        this.id = report.getId();
        this.reportingUserId = report.getReportingUser().getUserId();
        this.reportedUserId = report.getReportedUser().getUserId();
        this.reportingUserUsername = report.getReportingUser().getUsername();
        this.reportedUserUsername = report.getReportedUser().getUsername();
        this.reportingUserImagePath = report.getReportingUser().getImagePath();
        this.reportedUserImagePath = report.getReportedUser().getImagePath();
        this.reportedUserNumberOfReports = report.getReportedUser().getNumberOfReports();
        this.description = report.getDescription();
        this.sentAt = report.getSentAt();
    }

    public UserReportDTO(Long id, Long reportingUserId, Long reportedUserId, String reportingUserUsername, String reportedUserUsername, String reportingUserImagePath, String reportedUserImagePath, Integer reportedUserNumberOfReports, String description, LocalDateTime sentAt) {
        this.id = id;
        this.reportingUserId = reportingUserId;
        this.reportedUserId = reportedUserId;
        this.reportingUserUsername = reportingUserUsername;
        this.reportedUserUsername = reportedUserUsername;
        this.reportingUserImagePath = reportingUserImagePath;
        this.reportedUserImagePath = reportedUserImagePath;
        this.reportedUserNumberOfReports = reportedUserNumberOfReports;
        this.description = description;
        this.sentAt = sentAt;
    }

}
