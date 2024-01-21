package com.bookingapp;

import com.bookingapp.dtos.JwtAuthenticationRequest;
import com.bookingapp.dtos.ReservationRequestDTO;
import com.bookingapp.enums.RequestStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.*;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import java.time.LocalDate;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationRequestApprovalTests extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private static String guestToken;
	private static Long guestId;
	private static String ownerToken;

	@BeforeAll
	public void login() {
		ResponseEntity<String> responseEntityGuest = restTemplate.postForEntity("/api/login",
				new JwtAuthenticationRequest("guest1@guest.com", "12345678"), String.class);
		guestToken = responseEntityGuest.getBody();
		guestId = 4L;

		ResponseEntity<String> responseEntityOwner = restTemplate.postForEntity("/api/login",
				new JwtAuthenticationRequest("owner1@owner.com", "12345678"), String.class);
		ownerToken = responseEntityOwner.getBody();
	}

	@Test
	@DisplayName("Should approve the reservation request (manually) (no overlap)")
	public void test_manual_approveReservationRequest_success_no_overlap() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + ownerToken);
		HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<ReservationRequestDTO> responseEntity = restTemplate.exchange("/api/requests/approve/4",
				HttpMethod.PUT, httpEntity, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), 4L);
		Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getRequestStatus(), RequestStatus.ACCEPTED);
	}

	@Test
	@DisplayName("Should approve the reservation request (automatically) (no overlap)")
	public void test_automatic_approveReservationRequest_success_no_overlap() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + guestToken);
		HttpEntity<Object> httpEntityGet = new HttpEntity<>(headers);

		Long accommodationId = 2L;
		LocalDate startDate = LocalDate.of(2024, 2, 1);
		LocalDate endDate = LocalDate.of(2024, 2, 2);
		double totalPrice = 160;
		int numberOfGuests = 2;
		ReservationRequestDTO requestDTO = new ReservationRequestDTO(guestId, accommodationId, totalPrice,
				startDate, endDate, numberOfGuests, "owner1@owner.com");

		HttpEntity<Object> httpEntityPost = new HttpEntity<>(requestDTO, headers);

		ResponseEntity<Long> responseEntity = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		Long requestId = Objects.requireNonNull(responseEntity.getBody());
		ResponseEntity<ReservationRequestDTO> responseEntityRequest = restTemplate.exchange("/api/requests/" + requestId,
				HttpMethod.GET, httpEntityGet, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntityRequest.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getId(), requestId);
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getRequestStatus(), RequestStatus.ACCEPTED);
	}

	@Test
	@DisplayName("Shouldn't approve the reservation request (accommodation manual)")
	public void test_createReservationRequest_accommodation_manual_success() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + guestToken);
		HttpEntity<Object> httpEntityGet = new HttpEntity<>(headers);

		Long accommodationId = 1L;
		LocalDate startDate = LocalDate.of(2024, 2, 4);
		LocalDate endDate = LocalDate.of(2024, 2, 5);
		double totalPrice = 200;
		int numberOfGuests = 2;
		ReservationRequestDTO requestDTO = new ReservationRequestDTO(guestId, accommodationId, totalPrice,
				startDate, endDate, numberOfGuests, "owner1@owner.com");

		HttpEntity<Object> httpEntityPost = new HttpEntity<>(requestDTO, headers);

		ResponseEntity<Long> responseEntity = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		Long requestId = Objects.requireNonNull(responseEntity.getBody());
		ResponseEntity<ReservationRequestDTO> responseEntityRequest = restTemplate.exchange("/api/requests/" + requestId,
				HttpMethod.GET, httpEntityGet, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntityRequest.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getId(), requestId);
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getRequestStatus(), RequestStatus.SENT);
	}

	@Test
	@DependsOn("test_automatic_approveReservationRequest_success_no_overlap")
	@DisplayName("Shouldn't create the reservation request")
	public void test_createReservationRequest_overlap_fail() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + guestToken);
		HttpEntity<Object> httpEntityGet = new HttpEntity<>(headers);

		Long accommodationId = 2L;
		LocalDate startDate = LocalDate.of(2024, 2, 1);
		LocalDate endDate = LocalDate.of(2024, 2, 2);
		double totalPrice = 160;
		int numberOfGuests = 2;
		ReservationRequestDTO requestDTO = new ReservationRequestDTO(guestId, accommodationId, totalPrice,
				startDate, endDate, numberOfGuests, "owner1@owner.com");

		HttpEntity<Object> httpEntityPost = new HttpEntity<>(requestDTO, headers);

		ResponseEntity<Long> response = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Should approve the reservation request (manually) (with overlap)")
	public void test_manual_approveReservationRequest_success_with_overlap() {
		HttpHeaders headersOwner = new HttpHeaders();
		headersOwner.add("Authorization", "Bearer " + ownerToken);
		HttpEntity<Object> httpEntityOwner = new HttpEntity<>(headersOwner);
		HttpHeaders headersGuest = new HttpHeaders();
		headersGuest.add("Authorization", "Bearer " + guestToken);
		HttpEntity<Object> httpEntityGuest = new HttpEntity<>(headersGuest);

		// creating requests

		Long accommodationId = 1L;
		LocalDate startDate = LocalDate.of(2024, 2, 10);
		LocalDate endDate = LocalDate.of(2024, 2, 13);
		double totalPrice = 200;
		int numberOfGuests = 2;
		ReservationRequestDTO requestDTO = new ReservationRequestDTO(guestId, accommodationId, totalPrice,
				startDate, endDate, numberOfGuests, "owner1@owner.com");

		HttpEntity<Object> httpEntityPost = new HttpEntity<>(requestDTO, headersGuest);
		ResponseEntity<Long> responseEntity = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Long requestToApproveId = Objects.requireNonNull(responseEntity.getBody());

	 	startDate = LocalDate.of(2024, 2, 6);
		endDate = LocalDate.of(2024, 2, 7);
		requestDTO.setStartDate(startDate);
		requestDTO.setEndDate(endDate);

		httpEntityPost = new HttpEntity<>(requestDTO, headersGuest);
		responseEntity = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Long requestAfterApproveStillSentId1 = Objects.requireNonNull(responseEntity.getBody());

		startDate = LocalDate.of(2024, 2, 8);
		endDate = LocalDate.of(2024, 2, 10);
		requestDTO.setStartDate(startDate);
		requestDTO.setEndDate(endDate);

		httpEntityPost = new HttpEntity<>(requestDTO, headersGuest);
		responseEntity = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Long requestAfterApproveDeclined1 = Objects.requireNonNull(responseEntity.getBody());

		startDate = LocalDate.of(2024, 2, 10);
		endDate = LocalDate.of(2024, 2, 11);
		requestDTO.setStartDate(startDate);
		requestDTO.setEndDate(endDate);

		httpEntityPost = new HttpEntity<>(requestDTO, headersGuest);
		responseEntity = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Long requestAfterApproveDeclined2 = Objects.requireNonNull(responseEntity.getBody());

		startDate = LocalDate.of(2024, 2, 11);
		endDate = LocalDate.of(2024, 2, 12);
		requestDTO.setStartDate(startDate);
		requestDTO.setEndDate(endDate);

		httpEntityPost = new HttpEntity<>(requestDTO, headersGuest);
		responseEntity = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Long requestAfterApproveDeclined3 = Objects.requireNonNull(responseEntity.getBody());

		startDate = LocalDate.of(2024, 2, 12);
		endDate = LocalDate.of(2024, 2, 13);
		requestDTO.setStartDate(startDate);
		requestDTO.setEndDate(endDate);

		httpEntityPost = new HttpEntity<>(requestDTO, headersGuest);
		responseEntity = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Long requestAfterApproveDeclined4 = Objects.requireNonNull(responseEntity.getBody());

		startDate = LocalDate.of(2024, 2, 13);
		endDate = LocalDate.of(2024, 2, 15);
		requestDTO.setStartDate(startDate);
		requestDTO.setEndDate(endDate);

		httpEntityPost = new HttpEntity<>(requestDTO, headersGuest);
		responseEntity = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Long requestAfterApproveDeclined5 = Objects.requireNonNull(responseEntity.getBody());

		startDate = LocalDate.of(2024, 2, 14);
		endDate = LocalDate.of(2024, 2, 15);
		requestDTO.setStartDate(startDate);
		requestDTO.setEndDate(endDate);

		httpEntityPost = new HttpEntity<>(requestDTO, headersGuest);
		responseEntity = restTemplate.exchange("/api/requests",
				HttpMethod.POST, httpEntityPost, Long.class);

		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Long requestAfterApproveStillSentId2 = Objects.requireNonNull(responseEntity.getBody());

		// checking if approved request is ACCEPTED and others which are overlapping are DECLINED and others are still SENT

		ResponseEntity<ReservationRequestDTO> responseEntityApprove = restTemplate.exchange("/api/requests/approve/" + requestToApproveId,
		HttpMethod.PUT, httpEntityOwner, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntityApprove.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntityApprove.getBody()).getId(), requestToApproveId);
		Assertions.assertEquals(Objects.requireNonNull(responseEntityApprove.getBody()).getRequestStatus(), RequestStatus.ACCEPTED);

		ResponseEntity<ReservationRequestDTO> responseEntityRequest = restTemplate.exchange("/api/requests/" + requestAfterApproveStillSentId1,
			HttpMethod.GET, httpEntityGuest, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntityRequest.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getId(), requestAfterApproveStillSentId1);
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getRequestStatus(), RequestStatus.SENT);

		responseEntityRequest = restTemplate.exchange("/api/requests/" + requestAfterApproveStillSentId2,
			HttpMethod.GET, httpEntityGuest, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntityRequest.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getId(), requestAfterApproveStillSentId2);
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getRequestStatus(), RequestStatus.SENT);

		responseEntityRequest = restTemplate.exchange("/api/requests/" + requestAfterApproveDeclined1,
			HttpMethod.GET, httpEntityGuest, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntityRequest.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getId(), requestAfterApproveDeclined1);
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getRequestStatus(), RequestStatus.DECLINED);

		responseEntityRequest = restTemplate.exchange("/api/requests/" + requestAfterApproveDeclined2,
			HttpMethod.GET, httpEntityGuest, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntityRequest.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getId(), requestAfterApproveDeclined2);
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getRequestStatus(), RequestStatus.DECLINED);

		responseEntityRequest = restTemplate.exchange("/api/requests/" + requestAfterApproveDeclined3,
			HttpMethod.GET, httpEntityGuest, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntityRequest.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getId(), requestAfterApproveDeclined3);
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getRequestStatus(), RequestStatus.DECLINED);

		responseEntityRequest = restTemplate.exchange("/api/requests/" + requestAfterApproveDeclined4,
			HttpMethod.GET, httpEntityGuest, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntityRequest.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getId(), requestAfterApproveDeclined4);
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getRequestStatus(), RequestStatus.DECLINED);

		responseEntityRequest = restTemplate.exchange("/api/requests/" + requestAfterApproveDeclined5,
			HttpMethod.GET, httpEntityGuest, ReservationRequestDTO.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntityRequest.getStatusCode());
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getId(), requestAfterApproveDeclined5);
		Assertions.assertEquals(Objects.requireNonNull(responseEntityRequest.getBody()).getRequestStatus(), RequestStatus.DECLINED);
	}

}
