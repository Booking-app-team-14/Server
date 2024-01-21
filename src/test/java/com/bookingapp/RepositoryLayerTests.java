package com.bookingapp;

import com.bookingapp.entities.*;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.enums.Role;
import com.bookingapp.repositories.*;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.ReservationService;
import com.bookingapp.services.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryLayerTests{

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRequestIRepository reservationRequestIRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void when_ReservationRequestIRepository_findById_then_return_reservationRequest() {
        // given
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setAccommodationId(1L);
        reservationRequest.setStartDate(LocalDate.now().plusDays(4));
        reservationRequest.setEndDate(LocalDate.now().plusDays(8));
        reservationRequest.setRequestStatus(RequestStatus.ACCEPTED);
        reservationRequest.setUserId(1L);
        reservationRequest.setNumberOfGuests(2);
        reservationRequest.setTotalPrice(100);
        reservationRequest.setName("Test Name");
        reservationRequest.setDateRequested(LocalDate.now().toString());
        reservationRequest.setUserUsername("Test Username");
        reservationRequest.setType("Test Type");
        reservationRequest.setImageType("Test Image Type");
        reservationRequest.setMainPictureBytes("Test Main Picture Bytes");
        reservationRequest.setUserImageType("Test User Image Type");
        reservationRequest.setUserProfilePictureBytes("Test User Profile Picture Bytes");

        entityManager.persist(reservationRequest);
        entityManager.flush();

        // when
        Optional<ReservationRequest> found = reservationRequestIRepository.findById(reservationRequest.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals(found.get().getAccommodationId(), reservationRequest.getAccommodationId());
    }

    @Test
    public void when_ReservationRequestIRepository_findById_then_return_empty() {
        // given
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setAccommodationId(1L);
        reservationRequest.setStartDate(LocalDate.now().plusDays(4));
        reservationRequest.setEndDate(LocalDate.now().plusDays(8));
        reservationRequest.setRequestStatus(RequestStatus.ACCEPTED);
        reservationRequest.setUserId(1L);
        reservationRequest.setNumberOfGuests(2);
        reservationRequest.setTotalPrice(100);
        reservationRequest.setName("Test Name");
        reservationRequest.setDateRequested(LocalDate.now().toString());
        reservationRequest.setUserUsername("Test Username");
        reservationRequest.setType("Test Type");
        reservationRequest.setImageType("Test Image Type");
        reservationRequest.setMainPictureBytes("Test Main Picture Bytes");
        reservationRequest.setUserImageType("Test User Image Type");
        reservationRequest.setUserProfilePictureBytes("Test User Profile Picture Bytes");

        entityManager.persist(reservationRequest);
        entityManager.flush();

        // when
        Optional<ReservationRequest> found = reservationRequestIRepository.findById(2L);

        // then
        assertFalse(found.isPresent());
    }

    @Test
    public void when_ReservationRequestIRepository_save_new_then_return_reservationRequest() {
        // given
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setAccommodationId(1L);
        reservationRequest.setStartDate(LocalDate.now().plusDays(4));
        reservationRequest.setEndDate(LocalDate.now().plusDays(8));
        reservationRequest.setRequestStatus(RequestStatus.ACCEPTED);
        reservationRequest.setUserId(1L);
        reservationRequest.setNumberOfGuests(2);
        reservationRequest.setTotalPrice(100);
        reservationRequest.setName("Test Name");
        reservationRequest.setDateRequested(LocalDate.now().toString());
        reservationRequest.setUserUsername("Test Username");
        reservationRequest.setType("Test Type");
        reservationRequest.setImageType("Test Image Type");
        reservationRequest.setMainPictureBytes("Test Main Picture Bytes");
        reservationRequest.setUserImageType("Test User Image Type");
        reservationRequest.setUserProfilePictureBytes("Test User Profile Picture Bytes");

        // when
        ReservationRequest saved = reservationRequestIRepository.save(reservationRequest);

        // then
        assertNotNull(saved);
        assertEquals(saved.getAccommodationId(), reservationRequest.getAccommodationId());
    }

    @Test
    public void when_ReservationRequestIRepository_save_update_then_return_reservationRequest() {
        // given
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setAccommodationId(1L);
        reservationRequest.setStartDate(LocalDate.now().plusDays(4));
        reservationRequest.setEndDate(LocalDate.now().plusDays(8));
        reservationRequest.setRequestStatus(RequestStatus.ACCEPTED);
        reservationRequest.setUserId(1L);
        reservationRequest.setNumberOfGuests(2);
        reservationRequest.setTotalPrice(100);
        reservationRequest.setName("Test Name");
        reservationRequest.setDateRequested(LocalDate.now().toString());
        reservationRequest.setUserUsername("Test Username");
        reservationRequest.setType("Test Type");
        reservationRequest.setImageType("Test Image Type");
        reservationRequest.setMainPictureBytes("Test Main Picture Bytes");
        reservationRequest.setUserImageType("Test User Image Type");
        reservationRequest.setUserProfilePictureBytes("Test User Profile Picture Bytes");

        entityManager.persist(reservationRequest);
        entityManager.flush();

        // when
        reservationRequest.setName("Updated Name");
        ReservationRequest updated = reservationRequestIRepository.save(reservationRequest);

        // then
        assertNotNull(updated);
        assertEquals(updated.getName(), reservationRequest.getName());
    }

    @Test
    public void when_UserAccountRepository_findByUsername_then_return_userAccount() {
        // given
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("test@example.com");
        userAccount.setPassword("12345678");
        userAccount.setRole(Role.GUEST);
        userAccount.setFirstName("Test First Name");
        userAccount.setLastName("Test Last Name");
        userAccount.setAddress("Test Address");
        userAccount.setPhoneNumber("+381000000000");
        userAccount.setProfilePicturePath("Test Profile Picture Path");
        userAccount.setLastPasswordResetDate(null);
        userAccount.setBlocked(false);
        userAccount.setVerified(false);
        userAccount.setNumberOfReports(0);
        userAccount.setNotWantedNotificationTypes(new HashSet<>());

        entityManager.persist(userAccount);
        entityManager.flush();

        // when
        UserAccount found = userAccountRepository.findByUsername(userAccount.getUsername());

        // then
        assertNotNull(found);
        assertEquals(found.getUsername(), userAccount.getUsername());
    }

    @Test
    public void when_UserAccountRepository_findByUsername_then_return_null() {
        // given
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("test@example.com");
        userAccount.setPassword("12345678");
        userAccount.setRole(Role.GUEST);
        userAccount.setFirstName("Test First Name");
        userAccount.setLastName("Test Last Name");
        userAccount.setAddress("Test Address");
        userAccount.setPhoneNumber("+381000000000");
        userAccount.setProfilePicturePath("Test Profile Picture Path");
        userAccount.setLastPasswordResetDate(null);
        userAccount.setBlocked(false);
        userAccount.setVerified(false);
        userAccount.setNumberOfReports(0);
        userAccount.setNotWantedNotificationTypes(new HashSet<>());

        entityManager.persist(userAccount);
        entityManager.flush();

        // when
        UserAccount found = userAccountRepository.findByUsername("other@example.com");

        // then
        assertNull(found);
    }

}