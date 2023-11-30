package com.bookingapp.repositories;

import com.bookingapp.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RequestIRepository extends JpaRepository<Request, Long> {

//    Set<Request> findAllByRequestId(Long Id);
}
