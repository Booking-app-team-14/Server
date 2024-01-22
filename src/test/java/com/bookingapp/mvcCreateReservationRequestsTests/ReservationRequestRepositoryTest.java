package com.bookingapp.mvcCreateReservationRequestsTests;

import com.bookingapp.dtos.ReservationRequestDTO;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.repositories.ReservationRequestIRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ReservationRequestRepositoryTest {

    @Autowired
    private ReservationRequestIRepository reservationRequestRepository;

    @Test
    void saveReservationRequest() {

        ReservationRequest reservationRequest = createMockReservationRequest();


        ReservationRequest savedRequest = reservationRequestRepository.save(reservationRequest);


        assertNotNull(savedRequest.getId());

    }

    private ReservationRequest createMockReservationRequest() {
        ReservationRequestDTO rdto = new ReservationRequestDTO();
        rdto.setGuestId(1L);
        rdto.setAccommodationId(100L);
        rdto.setRequestStatus(RequestStatus.SENT);
        rdto.setStartDate(LocalDate.now().plusDays(1));
        rdto.setEndDate(LocalDate.now().plusDays(3));
        rdto.setNumberOfGuests(2);
        rdto.setTotalPrice(200.0);
        rdto.setDateRequested("1234567890");
        rdto.setImageType("jpg");
        rdto.setMainPictureBytes("rtjuuky");
        rdto.setStars(4);
        rdto.setName("Sample Accommodation");
        rdto.setType("Apartment");
        rdto.setUserUsername("john_doe");
        rdto.setUserImageType("jpg");
        rdto.setUserProfilePictureBytes("rtetdgfh");

        return new ReservationRequest(rdto);
    }

    @Test
    void testFindAllByAccommodationAndEndDateBetween() {

        ReservationRequest reservationRequest1 = createMockReservationRequest(1L, LocalDate.now().plusDays(5), LocalDate.now().plusDays(10));
        ReservationRequest reservationRequest2 = createMockReservationRequest(1L, LocalDate.now().plusDays(15), LocalDate.now().plusDays(20));
        ReservationRequest reservationRequest3 = createMockReservationRequest(2L, LocalDate.now().plusDays(25), LocalDate.now().plusDays(30));

        reservationRequestRepository.saveAll(List.of(reservationRequest1, reservationRequest2, reservationRequest3));


        List<ReservationRequest> result = reservationRequestRepository.findAllByAccommodationAndEndDateBetween(1L, LocalDate.now().plusDays(8), LocalDate.now().plusDays(22));


        assertEquals(2, result.size());

    }

    @Test
    void testFindAllByAccommodationAndEndDateBetween_NoResults() {

        ReservationRequest reservationRequest1 = createMockReservationRequest(1L, LocalDate.now().plusDays(5), LocalDate.now().plusDays(10));
        ReservationRequest reservationRequest2 = createMockReservationRequest(1L, LocalDate.now().plusDays(15), LocalDate.now().plusDays(20));
        ReservationRequest reservationRequest3 = createMockReservationRequest(2L, LocalDate.now().plusDays(25), LocalDate.now().plusDays(30));

        reservationRequestRepository.saveAll(List.of(reservationRequest1, reservationRequest2, reservationRequest3));


        List<ReservationRequest> result = reservationRequestRepository.findAllByAccommodationAndEndDateBetween(1L, LocalDate.now().plusDays(25), LocalDate.now().plusDays(30));


        assertEquals(0, result.size());

    }

    private ReservationRequest createMockReservationRequest(Long accommodationId, LocalDate startDate, LocalDate endDate) {
        ReservationRequestDTO rdto = new ReservationRequestDTO();
        rdto.setGuestId(1L);
        rdto.setAccommodationId(accommodationId);
        rdto.setRequestStatus(RequestStatus.SENT);
        rdto.setStartDate(startDate);
        rdto.setEndDate(endDate);
        rdto.setNumberOfGuests(2);
        rdto.setTotalPrice(200.0);
        rdto.setDateRequested("1234567890");
        rdto.setImageType("jpg");
        rdto.setMainPictureBytes("rtjuuky");
        rdto.setStars(4);
        rdto.setName("Sample Accommodation");
        rdto.setType("Apartment");
        rdto.setUserUsername("john_doe");
        rdto.setUserImageType("jpg");
        rdto.setUserProfilePictureBytes("rtetdgfh");


        return new ReservationRequest(rdto);
    }
}
