package com.bookingapp.repositories;

import com.bookingapp.entities.Location;
import com.bookingapp.entities.MonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
