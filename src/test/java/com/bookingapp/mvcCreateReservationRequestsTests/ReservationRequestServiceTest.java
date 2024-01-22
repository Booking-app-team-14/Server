package com.bookingapp.mvcCreateReservationRequestsTests;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Availability;
import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.repositories.ReservationRequestIRepository;
import com.bookingapp.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.configuration.GlobalConfiguration.validate;

public class ReservationRequestServiceTest {

    @Mock
    private ReservationRequestIRepository requestRepository;

    @Mock
    private AccommodationService accommodationService;

    @InjectMocks
    private ReservationRequestService reservationRequestService;

    public ReservationRequestServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("validRequestArgumentsProvider")
    void createValidRequest(ReservationRequest reservationRequest, Accommodation mockAccommodation) {
        // Arrange
        when(accommodationService.getAccommodationById(any())).thenReturn(Optional.of(mockAccommodation));
        when(requestRepository.save(any())).thenReturn(reservationRequest);

        // Act & Assert
        assertDoesNotThrow(() -> {
            reservationRequestService.createRequest(reservationRequest);

            // Verify that save method was called with the correct reservation request
            verify(requestRepository).save(reservationRequest);
            // Verify that no other interactions occurred with the repository
            verifyNoMoreInteractions(requestRepository);
        });
    }

    private static Stream<Arguments> validRequestArgumentsProvider() {
        return Stream.of(
                Arguments.of(createMockReservationRequest(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)), createMockAccommodation()),
                Arguments.of(createMockReservationRequest(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5)), createMockAccommodation()),
                Arguments.of(createMockReservationRequest(LocalDate.now().plusDays(2), LocalDate.now().plusDays(4)), createMockAccommodation()),
                Arguments.of(createMockReservationRequest(LocalDate.now().plusDays(3), LocalDate.now().plusDays(4)), createMockAccommodation()),
                Arguments.of(createMockReservationRequest(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2)), createMockAccommodation())
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDateArgumentsProvider")
    void createRequestWithInvalidDates(LocalDate startDate, LocalDate endDate, String expectedErrorMessage) {
        // Arrange
        ReservationRequest reservationRequest = createMockReservationRequest(startDate, endDate);
        when(accommodationService.getAccommodationById(any())).thenReturn(Optional.of(createMockAccommodation()));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationRequestService.createRequest(reservationRequest));
        assertEquals(expectedErrorMessage, exception.getMessage());

        // Verify that no interactions occurred with the repository
        verifyNoInteractions(requestRepository);
    }

    private static ReservationRequest createMockReservationRequest(LocalDate startDate, LocalDate endDate) {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setStartDate(startDate);
        reservationRequest.setEndDate(endDate);
        reservationRequest.setNumberOfGuests(2);
        reservationRequest.setAccommodationId(100L);
        return reservationRequest;
    }

    private static Stream<Arguments> invalidDateArgumentsProvider() {
        return Stream.of(
                Arguments.of(LocalDate.now().plusDays(1), LocalDate.now(), "Check-out date should be after the check-in date"),
                Arguments.of(LocalDate.now().plusDays(1), LocalDate.now().minusDays(1), "Check-out date should be after the check-in date")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidGuestRangeArgumentsProvider")
    void createRequestWithInvalidGuestRange(int numberOfGuests, String expectedErrorMessage) {
        // Arrange
        ReservationRequest reservationRequest = createMockReservationRequest();
        reservationRequest.setNumberOfGuests(numberOfGuests);
        when(accommodationService.getAccommodationById(any())).thenReturn(Optional.of(createMockAccommodation()));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationRequestService.createRequest(reservationRequest));
        assertEquals(expectedErrorMessage, exception.getMessage());

        // Verify that no interactions occurred with the repository
        verifyNoInteractions(requestRepository);
    }

    private static Stream<Arguments> invalidGuestRangeArgumentsProvider() {
        return Stream.of(
                Arguments.of(0, "Accommodation guest range is not respected"),
                Arguments.of(5, "Accommodation guest range is not respected"),
                Arguments.of(37, "Accommodation guest range is not respected"),
                Arguments.of(-1, "Accommodation guest range is not respected"),
                Arguments.of(8, "Accommodation guest range is not respected")
        );
    }

    @ParameterizedTest
    @MethodSource("unavailableAccommodationArgumentsProvider")
    void createRequestWithUnavailableAccommodation(LocalDate startDate, LocalDate endDate, String expectedErrorMessage) {
        // Arrange
        ReservationRequest reservationRequest = createMockReservationRequest(startDate, endDate);
        when(accommodationService.getAccommodationById(any()))
                .thenReturn(Optional.of(createMockAccommodationWithUnavailableDates()));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationRequestService.createRequest(reservationRequest));
        assertEquals(expectedErrorMessage, exception.getMessage());

        // Verify that no interactions occurred with the repository
        verifyNoInteractions(requestRepository);
    }

    private static Stream<Arguments> unavailableAccommodationArgumentsProvider() {
        return Stream.of(
                // Case 1: Reservation starts before and ends within accommodation availability
                Arguments.of(LocalDate.now().minusDays(3), LocalDate.now().minusDays(1), "Accommodation is not available for the chosen dates"),
                // Case 2: Reservation starts within and ends within accommodation availability
                Arguments.of(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), "Accommodation is not available for the chosen dates"),
                // Case 3: Reservation starts within and ends after accommodation availability
                Arguments.of(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), "Accommodation is not available for the chosen dates"),
                // Case 4: Reservation starts before and ends after accommodation availability
                Arguments.of(LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), "Accommodation is not available for the chosen dates"),
                // Case 5: Reservation completely within accommodation availability
                Arguments.of(LocalDate.now().plusDays(1), LocalDate.now().plusDays(8), "Accommodation is not available for the chosen dates")
        );
    }

    @ParameterizedTest
    @MethodSource("nonExistentAccommodationArgumentsProvider")
    void createRequestWithNonExistentAccommodation(Optional<Accommodation> accommodation, String expectedErrorMessage) {
        // Arrange
        ReservationRequest reservationRequest = createMockReservationRequest();
        when(accommodationService.getAccommodationById(any())).thenReturn(accommodation);

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> reservationRequestService.createRequest(reservationRequest));
        assertEquals(expectedErrorMessage, exception.getMessage());

        // Verify that no interactions occurred with the repository
        verifyNoInteractions(requestRepository);
    }

    private static Stream<Arguments> nonExistentAccommodationArgumentsProvider() {
        return Stream.of(
                Arguments.of(Optional.empty(), "No value present")
        );
    }

    private ReservationRequest createMockReservationRequest() {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setStartDate(LocalDate.now().plusDays(1));
        reservationRequest.setEndDate(LocalDate.now().plusDays(3));
        reservationRequest.setNumberOfGuests(2);
        reservationRequest.setAccommodationId(100L);
        return reservationRequest;
    }

    private static Accommodation createMockAccommodation() {
        Accommodation accommodation = new Accommodation();
        accommodation.setMinNumberOfGuests(1);
        accommodation.setMaxNumberOfGuests(4);
        accommodation.setAvailability(Collections.singleton(new Availability(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5),200.0, accommodation)));
        return accommodation;
    }

    private Accommodation createMockAccommodationWithUnavailableDates() {
        Accommodation accommodation = new Accommodation();
        accommodation.setMinNumberOfGuests(1);
        accommodation.setMaxNumberOfGuests(4);
        accommodation.setAvailability(Collections.singleton(new Availability(LocalDate.now(), LocalDate.now().plusDays(2),200.0,accommodation)));
        return accommodation;
    }
}
