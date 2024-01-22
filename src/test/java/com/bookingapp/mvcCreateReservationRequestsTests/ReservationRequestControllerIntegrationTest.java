package com.bookingapp.mvcCreateReservationRequestsTests;

import com.bookingapp.dtos.OwnerDTO;
import com.bookingapp.dtos.ReservationRequestDTO;
import com.bookingapp.entities.*;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.enums.NotificationType;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.enums.Role;
import com.bookingapp.repositories.AccommodationRepository;
import com.bookingapp.repositories.ReservationRequestIRepository;
import com.bookingapp.repositories.UserAccountRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccommodationRepository accommodationRepository;

    @MockBean
    private UserAccountRepository userAccountRepository;

    @MockBean
    private ReservationRequestIRepository reservationRequestRepository;

    @Test
    public void testCreateReservationRequest() throws Exception {
        // Mock data
        Accommodation accommodation=new Accommodation();
        accommodation.setMinNumberOfGuests(1);
        accommodation.setMaxNumberOfGuests(5);
        Set<Availability> avail= new HashSet<>();
        Availability newAvailability = new Availability(LocalDate.now(),LocalDate.now().plusDays(25),200.0,accommodation);
        avail.add(newAvailability);
        accommodation.setAvailability(avail);
        OwnerDTO owner1= new OwnerDTO();
        owner1.setRole(Role.OWNER);
        Owner owner =  new Owner(owner1);
        owner.setId(10L);
        owner.setUsername("mniko");
        Set<NotificationType> notificationTypes= new HashSet<>();
        notificationTypes.add(NotificationType.RESERVATION_REQUEST_CREATED);
        owner.setNotWantedNotificationTypes(notificationTypes);
        when(userAccountRepository.findByUsername(owner.getUsername())).thenReturn(owner);
        accommodation.setOwner(owner);



        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setGuestId(1L);
        requestDTO.setName("sdgfghzdf");
        requestDTO.setType(AccommodationType.APARTMENT.toString());
        requestDTO.setAccommodationId(2L);
        requestDTO.setStartDate(LocalDate.now().plusDays(10));
        requestDTO.setEndDate(LocalDate.now().plusDays(19));
        requestDTO.setNumberOfGuests(2);
        requestDTO.setTotalPrice(150.0);
        requestDTO.setRequestStatus(RequestStatus.SENT);
        requestDTO.setDateRequested("e3456789");
        requestDTO.setImageType("jpg");
        requestDTO.setMainPictureBytes("tdsgfsh");
        requestDTO.setStars(5);
        requestDTO.setUserImageType("png");
        requestDTO.setUserProfilePictureBytes("dgfhbg");
        requestDTO.setUserUsername("mniko");
        requestDTO.setId(10L);



        when(accommodationRepository.findById(2L)).thenReturn(Optional.of(accommodation));


        ReservationRequest savedRequest = new ReservationRequest(requestDTO);
        savedRequest.setId(1L);
        when(reservationRequestRepository.save(savedRequest)).thenReturn(savedRequest);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(201, statusCode, "Expected HTTP status 201 (Created)");
    }


    @Test
    public void testCreateReservationRequest_AccommodationNotFound() throws Exception {
        // Mock data
        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setGuestId(1L);
        requestDTO.setName("sdgfghzdf");
        requestDTO.setType(AccommodationType.APARTMENT.toString());
        requestDTO.setAccommodationId(2L);
        requestDTO.setStartDate(LocalDate.now().plusDays(10));
        requestDTO.setEndDate(LocalDate.now().plusDays(19));
        requestDTO.setNumberOfGuests(2);
        requestDTO.setTotalPrice(150.0);
        requestDTO.setRequestStatus(RequestStatus.SENT);
        requestDTO.setDateRequested("e3456789");
        requestDTO.setImageType("jpg");
        requestDTO.setMainPictureBytes("tdsgfsh");
        requestDTO.setStars(5);
        requestDTO.setUserImageType("png");
        requestDTO.setUserProfilePictureBytes("dgfhbg");
        requestDTO.setUserUsername("mniko");
        requestDTO.setId(10L);


        when(accommodationRepository.findById(2L)).thenReturn(Optional.empty());


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(400, statusCode, "Expected HTTP status 400 (Bad Request) - Accommodation not found");
    }

}

