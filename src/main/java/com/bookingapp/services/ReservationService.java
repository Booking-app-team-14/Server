package com.bookingapp.services;

import com.bookingapp.entities.Reservation;
import com.bookingapp.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void delete(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Reservation findById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }
}
