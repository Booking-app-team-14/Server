package com.bookingapp.reservations.repositories;

import com.bookingapp.reservations.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestIRepository extends JpaRepository<Request, Long> {

}
