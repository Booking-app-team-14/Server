package com.bookingapp.repositories;

import com.bookingapp.entities.Amenity;
import com.bookingapp.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
