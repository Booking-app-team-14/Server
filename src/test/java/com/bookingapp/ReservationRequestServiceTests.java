package com.bookingapp;

import com.bookingapp.entities.*;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.repositories.ReservationRequestIRepository;
import com.bookingapp.services.ReservationRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationRequestServiceTests {

    @InjectMocks
    private ReservationRequestService reservationRequestService;

    @Mock
    private ReservationRequestIRepository requestRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_RejectOverlappingReservationRequests_overlapping_requests() {
        // Arrange
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setAccommodationId(1L);
        reservationRequest.setStartDate(LocalDate.now().plusDays(10));
        reservationRequest.setEndDate(LocalDate.now().plusDays(20));
        reservationRequest.setRequestStatus(RequestStatus.ACCEPTED);

        ReservationRequest overlappingRequest1 = new ReservationRequest();
        overlappingRequest1.setAccommodationId(1L);
        overlappingRequest1.setStartDate(LocalDate.now().plusDays(8));
        overlappingRequest1.setEndDate(LocalDate.now().plusDays(10));
        overlappingRequest1.setRequestStatus(RequestStatus.SENT);

        ReservationRequest overlappingRequest2 = new ReservationRequest();
        overlappingRequest2.setAccommodationId(1L);
        overlappingRequest2.setStartDate(LocalDate.now().plusDays(8));
        overlappingRequest2.setEndDate(LocalDate.now().plusDays(11));
        overlappingRequest2.setRequestStatus(RequestStatus.SENT);

        ReservationRequest overlappingRequest3 = new ReservationRequest();
        overlappingRequest3.setAccommodationId(1L);
        overlappingRequest3.setStartDate(LocalDate.now().plusDays(10));
        overlappingRequest3.setEndDate(LocalDate.now().plusDays(10));
        overlappingRequest3.setRequestStatus(RequestStatus.SENT);

        ReservationRequest overlappingRequest4 = new ReservationRequest();
        overlappingRequest4.setAccommodationId(1L);
        overlappingRequest4.setStartDate(LocalDate.now().plusDays(10));
        overlappingRequest4.setEndDate(LocalDate.now().plusDays(12));
        overlappingRequest4.setRequestStatus(RequestStatus.SENT);

        ReservationRequest overlappingRequest5 = new ReservationRequest();
        overlappingRequest5.setAccommodationId(1L);
        overlappingRequest5.setStartDate(LocalDate.now().plusDays(12));
        overlappingRequest5.setEndDate(LocalDate.now().plusDays(15));
        overlappingRequest5.setRequestStatus(RequestStatus.SENT);

        ReservationRequest overlappingRequest6 = new ReservationRequest();
        overlappingRequest6.setAccommodationId(1L);
        overlappingRequest6.setStartDate(LocalDate.now().plusDays(15));
        overlappingRequest6.setEndDate(LocalDate.now().plusDays(20));
        overlappingRequest6.setRequestStatus(RequestStatus.SENT);

        ReservationRequest overlappingRequest7 = new ReservationRequest();
        overlappingRequest7.setAccommodationId(1L);
        overlappingRequest7.setStartDate(LocalDate.now().plusDays(20));
        overlappingRequest7.setEndDate(LocalDate.now().plusDays(20));
        overlappingRequest7.setRequestStatus(RequestStatus.SENT);

        ReservationRequest overlappingRequest8 = new ReservationRequest();
        overlappingRequest8.setAccommodationId(1L);
        overlappingRequest8.setStartDate(LocalDate.now().plusDays(20));
        overlappingRequest8.setEndDate(LocalDate.now().plusDays(22));
        overlappingRequest8.setRequestStatus(RequestStatus.SENT);

        ReservationRequest overlappingRequest9 = new ReservationRequest();
        overlappingRequest9.setAccommodationId(1L);
        overlappingRequest9.setStartDate(LocalDate.now().plusDays(19));
        overlappingRequest9.setEndDate(LocalDate.now().plusDays(22));
        overlappingRequest9.setRequestStatus(RequestStatus.SENT);

        List<ReservationRequest> requests = Arrays.asList(overlappingRequest1, overlappingRequest2, overlappingRequest3,
                                                        overlappingRequest4, overlappingRequest5, overlappingRequest6,
                                                        overlappingRequest7, overlappingRequest8, overlappingRequest9);

        when(reservationRequestService.findByAccommodationId(anyLong())).thenReturn(requests);
        when(requestRepository.save(any(ReservationRequest.class))).thenReturn(new ReservationRequest());

        // Act
        reservationRequestService.rejectOverlappingReservationRequests(reservationRequest);

        // Assert
        verify(requestRepository, times(1)).save(overlappingRequest1);
        assert (overlappingRequest1.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(1)).save(overlappingRequest2);
        assert (overlappingRequest2.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(1)).save(overlappingRequest3);
        assert (overlappingRequest3.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(1)).save(overlappingRequest4);
        assert (overlappingRequest4.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(1)).save(overlappingRequest5);
        assert (overlappingRequest5.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(1)).save(overlappingRequest6);
        assert (overlappingRequest6.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(1)).save(overlappingRequest7);
        assert (overlappingRequest7.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(1)).save(overlappingRequest8);
        assert (overlappingRequest8.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(1)).save(overlappingRequest9);
        assert (overlappingRequest9.getRequestStatus().equals(RequestStatus.DECLINED));
    }

    @Test
    public void test_RejectOverlappingReservationRequests_no_overlapping_requests() {
        // Arrange
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setAccommodationId(1L);
        reservationRequest.setStartDate(LocalDate.now().plusDays(4));
        reservationRequest.setEndDate(LocalDate.now().plusDays(6));
        reservationRequest.setRequestStatus(RequestStatus.ACCEPTED);

        ReservationRequest notOverlappingRequest1 = new ReservationRequest();
        notOverlappingRequest1.setAccommodationId(1L);
        notOverlappingRequest1.setStartDate(LocalDate.now().plusDays(7));
        notOverlappingRequest1.setEndDate(LocalDate.now().plusDays(10));
        notOverlappingRequest1.setRequestStatus(RequestStatus.SENT);

        ReservationRequest notOverlappingRequest2 = new ReservationRequest();
        notOverlappingRequest2.setAccommodationId(1L);
        notOverlappingRequest2.setStartDate(LocalDate.now().plusDays(1));
        notOverlappingRequest2.setEndDate(LocalDate.now().plusDays(3));
        notOverlappingRequest2.setRequestStatus(RequestStatus.SENT);

        List<ReservationRequest> requests = Arrays.asList(notOverlappingRequest1, notOverlappingRequest2);

        when(reservationRequestService.findByAccommodationId(anyLong())).thenReturn(requests);
        when(requestRepository.save(any(ReservationRequest.class))).thenReturn(new ReservationRequest());

        // Act
        reservationRequestService.rejectOverlappingReservationRequests(reservationRequest);

        // Assert
        verify(requestRepository, times(0)).save(notOverlappingRequest1);
        assert (notOverlappingRequest1.getRequestStatus().equals(RequestStatus.SENT));
        verify(requestRepository, times(0)).save(notOverlappingRequest2);
        assert (notOverlappingRequest2.getRequestStatus().equals(RequestStatus.SENT));
    }

    @Test
    public void test_RejectOverlappingReservationRequests_overlapping_and_no_overlapping_requests() {
        // Arrange
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setAccommodationId(1L);
        reservationRequest.setStartDate(LocalDate.now().plusDays(4));
        reservationRequest.setEndDate(LocalDate.now().plusDays(8));
        reservationRequest.setRequestStatus(RequestStatus.ACCEPTED);

        ReservationRequest overlappingRequest1 = new ReservationRequest();
        overlappingRequest1.setAccommodationId(1L);
        overlappingRequest1.setStartDate(LocalDate.now().plusDays(3));
        overlappingRequest1.setEndDate(LocalDate.now().plusDays(5));
        overlappingRequest1.setRequestStatus(RequestStatus.SENT);

        ReservationRequest overlappingRequest2 = new ReservationRequest();
        overlappingRequest2.setAccommodationId(1L);
        overlappingRequest2.setStartDate(LocalDate.now().plusDays(7));
        overlappingRequest2.setEndDate(LocalDate.now().plusDays(10));
        overlappingRequest2.setRequestStatus(RequestStatus.SENT);

        ReservationRequest overlappingRequest3 = new ReservationRequest();
        overlappingRequest3.setAccommodationId(1L);
        overlappingRequest3.setStartDate(LocalDate.now().plusDays(5));
        overlappingRequest3.setEndDate(LocalDate.now().plusDays(7));
        overlappingRequest3.setRequestStatus(RequestStatus.SENT);

        ReservationRequest notOverlappingRequest1 = new ReservationRequest();
        notOverlappingRequest1.setAccommodationId(1L);
        notOverlappingRequest1.setStartDate(LocalDate.now().plusDays(1));
        notOverlappingRequest1.setEndDate(LocalDate.now().plusDays(3));
        notOverlappingRequest1.setRequestStatus(RequestStatus.SENT);

        ReservationRequest notOverlappingRequest2 = new ReservationRequest();
        notOverlappingRequest2.setAccommodationId(1L);
        notOverlappingRequest2.setStartDate(LocalDate.now().plusDays(10));
        notOverlappingRequest2.setEndDate(LocalDate.now().plusDays(12));
        notOverlappingRequest2.setRequestStatus(RequestStatus.SENT);

        List<ReservationRequest> requests = Arrays.asList(overlappingRequest1, overlappingRequest2,
                                            overlappingRequest3, notOverlappingRequest1, notOverlappingRequest2);

        when(reservationRequestService.findByAccommodationId(anyLong())).thenReturn(requests);
        when(requestRepository.save(any(ReservationRequest.class))).thenReturn(new ReservationRequest());

        // Act
        reservationRequestService.rejectOverlappingReservationRequests(reservationRequest);

        // Assert
        verify(requestRepository, times(1)).save(overlappingRequest1);
        assert (overlappingRequest1.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(1)).save(overlappingRequest2);
        assert (overlappingRequest2.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(1)).save(overlappingRequest3);
        assert (overlappingRequest3.getRequestStatus().equals(RequestStatus.DECLINED));
        verify(requestRepository, times(0)).save(notOverlappingRequest1);
        assert (notOverlappingRequest1.getRequestStatus().equals(RequestStatus.SENT));
        verify(requestRepository, times(0)).save(notOverlappingRequest2);
        assert (notOverlappingRequest2.getRequestStatus().equals(RequestStatus.SENT));
    }


}