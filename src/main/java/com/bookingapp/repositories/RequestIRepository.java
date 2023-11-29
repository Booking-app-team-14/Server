package com.bookingapp.repositories;

import com.bookingapp.dtos.RequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RequestIRepository extends JpaRepository<RequestDTO, Long> {

    Set<RequestDTO> findAllById(Long Id);
}
