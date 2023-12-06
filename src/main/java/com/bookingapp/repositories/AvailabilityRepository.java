package com.bookingapp.repositories;

import com.bookingapp.entities.Availability;
import com.bookingapp.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

}
