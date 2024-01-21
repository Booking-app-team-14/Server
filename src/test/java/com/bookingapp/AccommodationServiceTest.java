package com.bookingapp;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Availability;
import com.bookingapp.entities.Reservation;
import com.bookingapp.repositories.AccommodationRepository;
import com.bookingapp.repositories.AvailabilityRepository;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.AvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccommodationServiceTest {

    @InjectMocks
    private AccommodationService accommodationService;

    @Mock
    private AccommodationRepository accommodationRepository;

    @Spy
    @InjectMocks
    private AvailabilityService availabilityService;

    @Mock
    private AvailabilityRepository availabilityRepository;

    private static Long accommodationId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Arrange
        accommodationId = 10L;
        Accommodation accommodation = new Accommodation();
        accommodation.setId(accommodationId);

        LocalDate startDate1 = LocalDate.of(2024, 2, 1);
        LocalDate endDate1 = LocalDate.of(2024, 2, 6);
        LocalDate startDate2 = LocalDate.of(2024, 2, 7);
        LocalDate endDate2 = LocalDate.of(2024, 2, 10);
        LocalDate startDate3 = LocalDate.of(2024, 2, 11);
        LocalDate endDate3 = LocalDate.of(2024, 2, 14);

        Availability availability1 = new Availability();
        availability1.setStartDate(startDate1);
        availability1.setEndDate(endDate1);
        availability1.setSpecialPrice(100D);
        availability1.setAccommodation(accommodation);

        Availability availability2 = new Availability();
        availability2.setStartDate(startDate2);
        availability2.setEndDate(endDate2);
        availability2.setSpecialPrice(110D);
        availability2.setAccommodation(accommodation);

        Availability availability3 = new Availability();
        availability3.setStartDate(startDate3);
        availability3.setEndDate(endDate3);
        availability3.setSpecialPrice(120D);
        availability3.setAccommodation(accommodation);

        Set<Availability> availabilities = new HashSet<>();
        availabilities.add(availability1);
        availabilities.add(availability2);
        availabilities.add(availability3);
        accommodation.setAvailability(availabilities);

        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));
        when(availabilityRepository.save(any(Availability.class))).thenReturn(null);
    }

    @Test
    public void test_ReserveAvailability_find_fail() {
        // Arrange
        Long id = 100L;
        LocalDate startDate = LocalDate.of(2024, 2, 5);
        LocalDate endDate = LocalDate.of(2024, 2, 12);

        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.empty());

        // Act
        Reservation reservation = accommodationService.reserveAvailability(id, startDate, endDate);

        // Assert
        assertNull(reservation);
    }

    @Test
    public void test_1_ReserveAvailability() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 2, 5);
        LocalDate endDate = LocalDate.of(2024, 2, 12);

        // Act
        Reservation reservation = accommodationService.reserveAvailability(accommodationId, startDate, endDate);

        // Assert
        assertEquals(2, reservation.getAccommodation().getAvailability().size());

        // checking if accommodation has these availabilities exactly: 1.2.2024 - 4.2.2024, 13.2.2024 - 14.2.2024
        reservation.getAccommodation().getAvailability().forEach(availability -> {
            if (availability.getStartDate().equals(LocalDate.of(2024, 2, 1))) {
                assertEquals(LocalDate.of(2024, 2, 4), availability.getEndDate());
            } else if (availability.getStartDate().equals(LocalDate.of(2024, 2, 13))) {
                assertEquals(LocalDate.of(2024, 2, 14), availability.getEndDate());
            } else {
                fail();
            }
        });
    }

    @Test
    public void test_2_ReserveAvailability() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 2, 7);
        LocalDate endDate = LocalDate.of(2024, 2, 10);

        // Act
        Reservation reservation = accommodationService.reserveAvailability(accommodationId, startDate, endDate);

        // Assert
        assertEquals(2, reservation.getAccommodation().getAvailability().size());

        // checking if accommodation has these availabilities exactly: 1.2.2024 - 6.2.2024, 11.2.2024 - 14.2.2024
        reservation.getAccommodation().getAvailability().forEach(availability -> {
            if (availability.getStartDate().equals(LocalDate.of(2024, 2, 1))) {
                assertEquals(LocalDate.of(2024, 2, 6), availability.getEndDate());
            } else if (availability.getStartDate().equals(LocalDate.of(2024, 2, 11))) {
                assertEquals(LocalDate.of(2024, 2, 14), availability.getEndDate());
            } else {
                fail();
            }
        });
    }

    @Test
    public void test_3_ReserveAvailability() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 2, 7);
        LocalDate endDate = LocalDate.of(2024, 2, 8);

        // Act
        Reservation reservation = accommodationService.reserveAvailability(accommodationId, startDate, endDate);

        // Assert
        assertEquals(3, reservation.getAccommodation().getAvailability().size());

        // checking if accommodation has these availabilities exactly: 1.2.2024 - 6.2.2024, 9.2.2024 - 10.2.2024, 11.2.2024 - 14.2.2024
        reservation.getAccommodation().getAvailability().forEach(availability -> {
            if (availability.getStartDate().equals(LocalDate.of(2024, 2, 1))) {
                assertEquals(LocalDate.of(2024, 2, 6), availability.getEndDate());
            } else if (availability.getStartDate().equals(LocalDate.of(2024, 2, 9))) {
                assertEquals(LocalDate.of(2024, 2, 10), availability.getEndDate());
            } else if (availability.getStartDate().equals(LocalDate.of(2024, 2, 11))) {
                assertEquals(LocalDate.of(2024, 2, 14), availability.getEndDate());
            } else {
                fail();
            }
        });
    }

    @Test
    public void test_4_ReserveAvailability() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 2, 6);
        LocalDate endDate = LocalDate.of(2024, 2, 11);

        // Act
        Reservation reservation = accommodationService.reserveAvailability(accommodationId, startDate, endDate);

        // Assert
        assertEquals(2, reservation.getAccommodation().getAvailability().size());

        // checking if accommodation has these availabilities exactly: 1.2.2024 - 5.2.2024, 12.2.2024 - 14.2.2024
        reservation.getAccommodation().getAvailability().forEach(availability -> {
            if (availability.getStartDate().equals(LocalDate.of(2024, 2, 1))) {
                assertEquals(LocalDate.of(2024, 2, 5), availability.getEndDate());
            } else if (availability.getStartDate().equals(LocalDate.of(2024, 2, 12))) {
                assertEquals(LocalDate.of(2024, 2, 14), availability.getEndDate());
            } else {
                fail();
            }
        });
    }

    @Test
    public void test_5_ReserveAvailability() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 2, 7);
        LocalDate endDate = LocalDate.of(2024, 2, 7);

        // Act
        Reservation reservation = accommodationService.reserveAvailability(accommodationId, startDate, endDate);

        // Assert
        assertEquals(3, reservation.getAccommodation().getAvailability().size());

        // checking if accommodation has these availabilities exactly: 1.2.2024 - 6.2.2024, 8.2.2024 - 10.2.2024, 11.2.2024 - 14.2.2024
        reservation.getAccommodation().getAvailability().forEach(availability -> {
            if (availability.getStartDate().equals(LocalDate.of(2024, 2, 1))) {
                assertEquals(LocalDate.of(2024, 2, 6), availability.getEndDate());
            } else if (availability.getStartDate().equals(LocalDate.of(2024, 2, 8))) {
                assertEquals(LocalDate.of(2024, 2, 10), availability.getEndDate());
            } else if (availability.getStartDate().equals(LocalDate.of(2024, 2, 11))) {
                assertEquals(LocalDate.of(2024, 2, 14), availability.getEndDate());
            } else {
                fail();
            }
        });
    }

    @Test
    public void test_6_ReserveAvailability() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 2, 4);
        LocalDate endDate = LocalDate.of(2024, 2, 14);

        // Act
        Reservation reservation = accommodationService.reserveAvailability(accommodationId, startDate, endDate);

        // Assert
        assertEquals(1, reservation.getAccommodation().getAvailability().size());

        // checking if accommodation has these availabilities exactly: 1.2.2024 - 3.2.2024
        reservation.getAccommodation().getAvailability().forEach(availability -> {
            if (availability.getStartDate().equals(LocalDate.of(2024, 2, 1))) {
                assertEquals(LocalDate.of(2024, 2, 3), availability.getEndDate());
            } else {
                fail();
            }
        });
    }

}