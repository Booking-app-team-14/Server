package com.bookingapp.repositories;

import com.bookingapp.entities.UserAccount;
import com.bookingapp.entities.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long> {


    boolean existsByReportingUserAndReportedUser(UserAccount reportingUser, UserAccount reportedUser);
}
