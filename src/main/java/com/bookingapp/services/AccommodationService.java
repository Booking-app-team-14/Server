package com.bookingapp.services;

import com.bookingapp.dtos.*;
import com.bookingapp.entities.*;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.AccommodationRepository;
import com.bookingapp.repositories.GuestRepository;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.repositories.LocationRepository;
import lombok.Getter;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AmenityService amenityService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private AvailabilityService availabilityService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private LocationService locationService;

    private ImagesRepository imagesRepository = new ImagesRepository();

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository) {
       this.accommodationRepository = accommodationRepository;
    }

    public List<Accommodation> findAll() {
        return null;
//        return accommodationRepository.findAll();
    }

    // CREATE
    public Accommodation createAccommodation(Accommodation accommodation) {
        return null;
//        return accommodationRepository.save(accommodation);
    }

    // READ
    public List<Accommodation> getAllAccommodations() {
        return null;
//        return accommodationRepository.findAll();
    }

    public Optional<Accommodation> getAccommodationById(Long id) {
     return accommodationRepository.findById(id);
    }

    // UPDATE
    public Optional<Accommodation> updateAccommodation(Long id, Accommodation updatedAccommodation) {
//        Optional<Accommodation> existingAccommodation = accommodationRepository.findById(id);
//
//        if (existingAccommodation.isPresent()) {
//            updatedAccommodation.setId(id);
//            return Optional.of(accommodationRepository.save(updatedAccommodation));
//        } else {
//            return Optional.empty();
//        }
        return Optional.empty();
    }

    // DELETE
    public boolean deleteAccommodation(Long id) {
        if (accommodationRepository.existsById(id)) {
//            userAccountService.deleteAllGuestFavoriteAccommodation(id);
            accommodationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    public List<OwnersAccommodationDTO> getAllOwnersAccommodations(Long ownerId) {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        List<OwnersAccommodationDTO> ownersAccommodationDTOS = new ArrayList<>();
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getOwner().getId().equals(ownerId) && accommodation.isApproved()) {
                OwnersAccommodationDTO ownersAccommodationDTO = new OwnersAccommodationDTO();
                ownersAccommodationDTO.setId(accommodation.getId());
                ownersAccommodationDTO.setName(accommodation.getName());
                ownersAccommodationDTO.setType(accommodation.getType().toString());
                ownersAccommodationDTO.setStars(accommodation.getRating().intValue());
                ownersAccommodationDTO.setMaxGuests(accommodation.getMaxNumberOfGuests());
                ownersAccommodationDTO.setAddress(accommodation.getLocation().getAddress());
                ownersAccommodationDTO.setPrice(accommodation.getPricePerNight());
                ImagesRepository imagesRepository = new ImagesRepository();
                String accommodationImagePath = findAccommodationImageName(accommodation.getId());
                try {
                    String imageBytes = imagesRepository.getImageBytes(accommodationImagePath);
                    ownersAccommodationDTO.setMainPictureBytes(imageBytes);
                    ownersAccommodationDTO.setImageType(imagesRepository.getImageType(imageBytes));
                } catch (IOException ignored) { }
                ownersAccommodationDTOS.add(ownersAccommodationDTO);
            }
        }
        return ownersAccommodationDTOS;
    }

    public List<AccommodationMobileDTO> getAllMobileAccommodations() {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        List<AccommodationMobileDTO> ownersAccommodationDTOS = new ArrayList<>();
        for (Accommodation accommodation : accommodations) {
            if  (accommodation.isApproved()) {
                AccommodationMobileDTO ownersAccommodationDTO = new AccommodationMobileDTO();
                ownersAccommodationDTO.setId(accommodation.getId());
                ownersAccommodationDTO.setName(accommodation.getName());
                ownersAccommodationDTO.setType(accommodation.getType().toString());
                ownersAccommodationDTO.setStars(accommodation.getRating().intValue());
                ownersAccommodationDTO.setMaxGuests(accommodation.getMaxNumberOfGuests());
                ownersAccommodationDTO.setAddress(accommodation.getLocation().getAddress());
                ownersAccommodationDTO.setPrice(accommodation.getPricePerNight());
                ImagesRepository imagesRepository = new ImagesRepository();
                String accommodationImagePath = findAccommodationImageName(accommodation.getId());
                try {
                    String imageBytes = imagesRepository.getImageBytes(accommodationImagePath);
                    ownersAccommodationDTO.setMainPictureBytes(imageBytes);
                    ownersAccommodationDTO.setImageType(imagesRepository.getImageType(imageBytes));
                } catch (IOException ignored) { }
                ownersAccommodationDTOS.add(ownersAccommodationDTO);
            }
        }
        return ownersAccommodationDTOS;
    }

    public Set<BestOffersDTO> getBestOffers() {
        return null;
//        return accommodationRepository.getBestOffers();
    }

    public Accommodation save(AccommodationDTO accommodationDTO) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();


        Accommodation accommodation = new Accommodation();
        accommodation.setName(accommodationDTO.getName());
        accommodation.setDescription(accommodationDTO.getDescription());

        /*Location location = new Location(accommodationDTO.getLocation());
        locationRepository.save(location);
        accommodation.setLocation(location)*/;
        Location existingLocation = locationRepository.findByCountryAndCityAndAddress(
                accommodationDTO.getLocation().getCountry(),
                accommodationDTO.getLocation().getCity(),
                accommodationDTO.getLocation().getAddress()
        );

        if (existingLocation != null) {
            accommodation.setLocation(existingLocation);
        } else {
            Location newLocation = new Location(accommodationDTO.getLocation());
            locationRepository.save(newLocation);
            accommodation.setLocation(newLocation);
        }

        accommodation.setType(accommodationDTO.getType());

        Set<Long> amenityIds = accommodationDTO.getAmenities().stream()
                .map(AmenityDTO::getId)
                .collect(Collectors.toSet());

        accommodation.setAmenities(new HashSet<>(amenityService.findAllById(amenityIds)));

        Set<Availability> availabilities  = new HashSet<>();
        for(AvailabilityDTO availabilityDTO : accommodationDTO.getAvailability()){
            Availability availability = new Availability(availabilityDTO);
            availability.setAccommodation(accommodation);
            if (availability.getSpecialPrice() == null) {
                availability.setSpecialPrice(accommodationDTO.getPricePerNight());
            }
            availabilities.add(availability);
        }


        accommodation.setAvailability(availabilities);

//        fillAvailabilityForCurrentYear(accommodation, accommodationDTO.getAvailability());

        accommodation.setImages(accommodationDTO.getImages());
        accommodation.setRating(accommodationDTO.getRating());
        accommodation.setMinNumberOfGuests(accommodationDTO.getMinNumberOfGuests());
        accommodation.setMaxNumberOfGuests(accommodationDTO.getMaxNumberOfGuests());
        accommodation.setPricePerGuest(accommodationDTO.isPricePerGuest());
        accommodation.setPricePerNight(accommodationDTO.getPricePerNight());
        accommodation.setCancellationDeadline(accommodationDTO.getCancellationDeadline());
        accommodation.setApproved(false);
        accommodation.setOwner(user);
        accommodationRepository.save(accommodation);
        return accommodation;
    }

    //posmatrati sve dostupnosti u toku tekuce godine
    public void fillAvailabilityForCurrentYear(Accommodation accommodation, Set<AvailabilityDTO> availabilitiesDTO) {
        int currentYear = Year.now().getValue();

        // sortirati postojece dostupnosti po datumu
        List<AvailabilityDTO> sortedAvailabilities = availabilitiesDTO.stream()
                .sorted(Comparator.comparing(AvailabilityDTO::getStartDate))
                .collect(Collectors.toList());

        // dodaj availabilityije za datume izmedju postojecih definisanih dostupnosti
        for (int i = 0; i < sortedAvailabilities.size() - 1; i++) {
            LocalDate endOfCurrent = sortedAvailabilities.get(i).getEndDate() ;
            LocalDate startOfNext = sortedAvailabilities.get(i + 1).getStartDate() ;

            // proveri razliku izmedju dva uzastopna datuma
            if (!endOfCurrent.plusDays(1).equals(startOfNext)) {

                Availability defaultAvailability = new Availability();
                defaultAvailability.setStartDate(LocalDate.from(endOfCurrent.plusDays(1).atStartOfDay()));
                defaultAvailability.setEndDate(LocalDate.from(startOfNext.minusDays(1).atStartOfDay()));
                defaultAvailability.setAccommodation(accommodation);
                // postaviti da bude null cena za nedostupne datume
                defaultAvailability.setSpecialPrice(null);

                accommodation.getAvailability().add(defaultAvailability);
            }
        }

        // dodaj availability za datume nakon poslednje definisane dostupnosti do kraja godine
        LocalDate lastEndDate = sortedAvailabilities.get(sortedAvailabilities.size() - 1).getEndDate() ;
        if (!lastEndDate.isEqual(LocalDate.of(currentYear, 12, 31))) {
            Availability defaultAvailability = new Availability();
            defaultAvailability.setStartDate(LocalDate.from(lastEndDate.plusDays(1).atStartOfDay()));
            defaultAvailability.setEndDate(LocalDate.from(LocalDate.of(currentYear, 12, 31).atTime(LocalTime.MAX)));
            defaultAvailability.setAccommodation(accommodation);
            // isto staviti da je null
            defaultAvailability.setSpecialPrice(null);

            accommodation.getAvailability().add(defaultAvailability);
        }
    }


    public void save(Accommodation accommodation) {
        accommodationRepository.save(accommodation);
    }

    public List<Accommodation> filterAccommodations(Double minPrice, Double maxPrice, Double minRating,
                                                    Integer minGuests, Integer maxGuests,
                                                    Set<Long> amenityIds,
                                                    AccommodationType accommodationType,
                                                    LocalDate startDate, LocalDate endDate) {


        return accommodationRepository.filterAccommodations(minPrice, maxPrice, minRating, minGuests, maxGuests,
                amenityIds, accommodationType, startDate, endDate);

    }


    public List<Accommodation> findAllByPricePerNightAsc () {
        return accommodationRepository.findAllByPricePerNightAsc();
    }

    public List<Accommodation> findAllByPricePerNightDesc () {
        return accommodationRepository.findAllByPricePerNightDesc();
    }

    public List<Accommodation> findAllByRatingDesc () {
        return accommodationRepository.findAllByRatingDesc();
    }

    public List<Accommodation> findAllByRatingAsc () {
        return accommodationRepository.findAllByRatingAsc();
    }


    public List<Accommodation> searchAccommodations (String searchTerm){
        return accommodationRepository.findAccommodationsByNameOrLocation(searchTerm);
    }

    public boolean uploadAccommodationImage(Long accommodationId, String imageBytes) {
        String imageType;
        try {
            imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException e) {
            return false;
        }

        String relativePath = String.format("accommodations/accommodation-%d", accommodationId);
        String directoryPath = String.format("src/main/resources/images/accommodations/accommodation-%d", accommodationId);
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        int imageCount = new File(directoryPath).listFiles().length + 1; // Count existing images
        String filename = String.format("accommodation-%d-%d.%s", accommodationId, imageCount, imageType);
        String imagePath = String.format("%s/%s", relativePath, filename);

        try {
            imagesRepository.addImage(imageBytes, imageType, imagePath);

            // Update the Accommodation entity's images field
            Accommodation accommodation = accommodationRepository.findById(accommodationId).orElse(null);
            if (accommodation != null) {
                accommodation.getImages().add(imagePath);
                accommodationRepository.save(accommodation);
            } else {
                // Handle if the accommodation with the given ID is not found
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }



     public boolean uploadAccommodationImages(Long accommodationId, List<String> imageBytesList) {
        String imageType;
        try {
             imageType = imagesRepository.getImageType(imageBytesList.get(0));
        } catch (IOException e) {
            return false;
        }

        String relativePath = String.format("accommodations/accommodation-%d", accommodationId);
        String directoryPath = String.format("src/main/resources/images/accommodations/accommodation-%d", accommodationId);
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for (int i = 0; i < imageBytesList.size(); i++) {
            int imageCount = new File(directoryPath).listFiles().length + i + 1;
            String filename = String.format("accommodation-%d-%d.%s", accommodationId, imageCount, imageType);
            String imagePath = String.format("%s/%s", relativePath, filename);

            try {
                imagesRepository.addImage(imageBytesList.get(i), imageType, imagePath);

                Accommodation accommodation = accommodationRepository.findById(accommodationId).orElse(null);
                if (accommodation != null) {
                    accommodation.getImages().add(imagePath);
                    accommodationRepository.save(accommodation);
                } else {

                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }


    public boolean deleteAccommodationImage(Long id) {
        String relativePath = findAccommodationImageName(id);
        if (relativePath == null) {
            return false;
        }
        return imagesRepository.deleteImage(relativePath);
    }

    public String getAccommodationImage(Long id) {
        String relativePath = findAccommodationImageName(id);
        if (relativePath == null) {
            return null;
        }
        try {
            return imagesRepository.getImageBytes(relativePath);
        } catch (IOException e) {
            return null;
        }
    }

    public String findAccommodationImageName(Long id) {
        File mainDirectory = new File("src\\main\\resources\\images\\accommodations");

        File[] accommodationDirectories = mainDirectory.listFiles();
        if (accommodationDirectories != null) {
            for (File accommodationDirectory : accommodationDirectories) {
                if (accommodationDirectory.isDirectory()) {
                    File[] filesInAccommodation = accommodationDirectory.listFiles();
                    if (filesInAccommodation != null) {
                        for (File file : filesInAccommodation) {
                            String filename = file.getName();
                            if (filename.startsWith("accommodation-" + id)) {
                                return "accommodations\\" + accommodationDirectory.getName() + "\\" + file.getName();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public  List<String> findAllAccommodationImageNames(Long id) {
        File mainDirectory = new File("src\\main\\resources\\images\\accommodations");

        List<String> names = new ArrayList<>();

        File[] accommodationDirectories = mainDirectory.listFiles();
        if (accommodationDirectories != null) {
            for (File accommodationDirectory : accommodationDirectories) {
                if (accommodationDirectory.isDirectory() && accommodationDirectory.getName().startsWith("accommodation-" + id)) {
                    File[] filesInAccommodation = accommodationDirectory.listFiles();
                    if (filesInAccommodation != null) {
                        for (File file : filesInAccommodation) {
                            String filename = file.getName();
                            names.add("accommodations\\" + accommodationDirectory.getName() + "\\" + file.getName());
                            }
                        }
                    }
                }
            }
        return names;
    }

    public Optional<Accommodation> findById(Long id) {
        return accommodationRepository.findById(id);
    }

    public void deleteAllImages(Long id) {
        String relativePath = String.format("accommodations\\accommodation-%d", id);
        imagesRepository.deleteAllImages(relativePath);
    }

    private int getImagesCount(Long id) {
        String relativePath = String.format("accommodations\\accommodation-%d", id);
        File mainDirectory = new File("src\\main\\resources\\images\\" + relativePath);
        File[] files = mainDirectory.listFiles();
        if (files != null) {
            return files.length;
        }
        return 0;
    }

    public String addImage(Long id, Image image) {
        String relativePath = "accommodations/accommodation-" + id + "/" + "accommodation-" + id + "-" + (getImagesCount(id) + 1) + "." + image.getImageType();;
        try {
            imagesRepository.addImage(image.getImageBytes(), image.getImageType(), relativePath);
            return relativePath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<String> getAllAccommodationImages(Long id) {
        List<String> relativePaths = findAllAccommodationImageNames(id);
        List<String> imageBytes = new ArrayList<>();
        if (relativePaths.isEmpty()) {
            return null;
        }
           for (String relativePath:relativePaths) {
            try {
                imageBytes.add(imagesRepository.getImageBytes(relativePath));
            } catch (IOException e) {
                return null;
            }
        }
        return imageBytes;
    }

    public Reservation reserveAvailability(Long accommodationId, LocalDate startDate, LocalDate endDate) {
        Optional<Accommodation> accommodationOptional = accommodationRepository.findById(accommodationId);
        Reservation reservation = new Reservation();
        if (accommodationOptional.isPresent()) {
            Accommodation accommodation = accommodationOptional.get();
            reservation.setAccommodation(accommodation);
            Set<Availability> accommodationAvailabilities = new HashSet<>(accommodation.getAvailability());
            for (Availability availability : accommodationAvailabilities) {
                if (availability.getStartDate().isEqual(startDate) && availability.getEndDate().isEqual(endDate)) {
                    accommodation.getAvailability().remove(availability);
                    reservation.getAvailability().add(availability);
                }
                else if(availability.getStartDate().isBefore(startDate) && (availability.getEndDate().isAfter(startDate) && availability.getEndDate().isBefore(endDate))){
                    Availability savedAvailability = new Availability(startDate, availability.getEndDate(), availability.getSpecialPrice(), accommodation);
                    availabilityService.save(savedAvailability);
                    reservation.getAvailability().add(savedAvailability);
                    availability.setEndDate(startDate.minusDays(1));
                }
                else if(availability.getStartDate().isBefore(startDate) && availability.getEndDate().isAfter(endDate)){
                    Availability savedAvailability = new Availability(startDate, endDate, availability.getSpecialPrice(), accommodation);
                    availabilityService.save(savedAvailability);
                    reservation.getAvailability().add(savedAvailability);
                    Availability newAvailability = new Availability();
                    newAvailability.setStartDate(endDate.plusDays(1));
                    newAvailability.setEndDate(availability.getEndDate());
                    newAvailability.setAccommodation(accommodation);
                    newAvailability.setSpecialPrice(availability.getSpecialPrice());
                    accommodation.getAvailability().add(newAvailability);
                    availability.setEndDate(startDate.minusDays(1));
                }
                else if(availability.getStartDate().isAfter(startDate) && availability.getEndDate().isBefore(endDate)){
                    accommodation.getAvailability().remove(availability);
                    reservation.getAvailability().add(availability);
                }
                else if(availability.getStartDate().isAfter(startDate) && (availability.getEndDate().isAfter(endDate) && availability.getStartDate().isBefore(endDate))){
                    Availability savedAvailability = new Availability(availability.getStartDate(), endDate, availability.getSpecialPrice(), accommodation);
                    availabilityService.save(savedAvailability);
                    reservation.getAvailability().add(savedAvailability);
                    availability.setStartDate(endDate.plusDays(1));
                }
                else if(availability.getStartDate().isEqual(startDate) && availability.getEndDate().isBefore(endDate)){
                    accommodation.getAvailability().remove(availability);
                    reservation.getAvailability().add(availability);
                }
                else if(availability.getStartDate().isEqual(startDate) && availability.getEndDate().isAfter(endDate)){
                    Availability savedAvailability = new Availability(startDate, endDate, availability.getSpecialPrice(), accommodation);
                    availabilityService.save(savedAvailability);
                    reservation.getAvailability().add(savedAvailability);
                    availability.setStartDate(endDate.plusDays(1));
                }
                else if(availability.getStartDate().isBefore(startDate) && availability.getEndDate().isEqual(endDate)){
                    Availability savedAvailability = new Availability(startDate, endDate, availability.getSpecialPrice(), accommodation);
                    availabilityService.save(savedAvailability);
                    reservation.getAvailability().add(savedAvailability);
                    availability.setEndDate(startDate.minusDays(1));
                }
                else if(availability.getStartDate().isAfter(startDate) && availability.getEndDate().isEqual(endDate)){
                    accommodation.getAvailability().remove(availability);
                    reservation.getAvailability().add(availability);
                }
                else if(availability.getStartDate().isEqual(endDate)){
                    Availability savedAvailability = new Availability(endDate, endDate, availability.getSpecialPrice(), accommodation);
                    availabilityService.save(savedAvailability);
                    reservation.getAvailability().add(savedAvailability);
                    if(availability.getStartDate().isEqual(availability.getEndDate())) {
                        accommodation.getAvailability().remove(availability);
                    }
                    else {
                        availability.setStartDate(availability.getStartDate().plusDays(1));
                    }
                }
                else if(availability.getEndDate().isEqual(startDate)){
                    Availability savedAvailability = new Availability(endDate, endDate, availability.getSpecialPrice(), accommodation);
                    availabilityService.save(savedAvailability);
                    reservation.getAvailability().add(savedAvailability);
                    if(availability.getStartDate().isEqual(availability.getEndDate())) {
                        accommodation.getAvailability().remove(availability);
                    }
                    else {
                        availability.setEndDate(availability.getEndDate().minusDays(1));
                    }
                }
            }
            accommodationRepository.save(accommodation);
        }
        return reservation;
    }

    public void cancelReservation(ReservationRequest reservationRequest) {
        Optional<Accommodation> accommodationOptional = accommodationRepository.findById(reservationRequest.getAccommodationId());
        if (accommodationOptional.isPresent()) {
            Accommodation accommodation = accommodationOptional.get();
            Reservation reservation = reservationService.findById(reservationRequest.getReservationId());
            for (Availability availability : reservation.getAvailability()) {
                accommodation.getAvailability().add(availability);
            }
            accommodationRepository.save(accommodation);
        }
    }

    public Accommodation update(Accommodation accommodation, AccommodationUpdateDTO accommodationUpdateDTO) {
        accommodation.setName(accommodationUpdateDTO.getName());
        accommodation.setDescription(accommodationUpdateDTO.getDescription());
        accommodation.setType(AccommodationType.valueOf(accommodationUpdateDTO.getType()));
        accommodation.setMinNumberOfGuests(accommodationUpdateDTO.getMinNumberOfGuests());
        accommodation.setMaxNumberOfGuests(accommodationUpdateDTO.getMaxNumberOfGuests());
        accommodation.setPricePerNight(accommodationUpdateDTO.getDefaultPrice());
        accommodation.setPricePerGuest(accommodationUpdateDTO.getPricePerGuest());
        accommodation.setCancellationDeadline(accommodationUpdateDTO.getCancellationDeadline());
        accommodation.setAutomatic(accommodationUpdateDTO.isAutomaticHandling());

        this.deleteAllImages(accommodation.getId());
        Set<String> images = new HashSet<>();
        for (Image image : accommodationUpdateDTO.getImages()) {
            String path = this.addImage(accommodation.getId(), image);
            images.add(path);
        }
        accommodation.setImages(images);

        Location existingLocation = locationRepository.findByCountryAndCityAndAddress(
                accommodationUpdateDTO.getLocation().getCountry(),
                accommodationUpdateDTO.getLocation().getCity(),
                accommodationUpdateDTO.getLocation().getAddress()
        );
        if (existingLocation != null) {
            accommodation.setLocation(existingLocation);
        } else {
            Location location = new Location(accommodationUpdateDTO.getLocation());
            locationService.save(location);
            accommodation.setLocation(location);
        }

        accommodation.setAmenities(accommodationUpdateDTO.getAmenities().stream()
                .map(amenityId -> amenityService.findById(amenityId))
                .collect(Collectors.toSet()));

        accommodation.setAvailability(accommodationUpdateDTO.getAvailability().stream()
                .map(availabilityDTO -> {
                    Availability availability = new Availability(availabilityDTO);
                    availability.setAccommodation(accommodation);
                    availabilityService.save(availability);
                    return availability;
                })
                .collect(Collectors.toSet()));

        accommodation.setApproved(true);
        this.save(accommodation);
        return accommodation;
    }

    public AccommodationUpdateDTO fillAccommodationUpdateDTO(Accommodation accommodation, AccommodationUpdateDTO accommodationUpdateDTO) {
        accommodationUpdateDTO.setId(accommodation.getId());
        accommodationUpdateDTO.setName(accommodation.getName());
        accommodationUpdateDTO.setDescription(accommodation.getDescription());
        accommodationUpdateDTO.setType(accommodation.getType().toString());
        accommodationUpdateDTO.setMinNumberOfGuests(accommodation.getMinNumberOfGuests());
        accommodationUpdateDTO.setMaxNumberOfGuests(accommodation.getMaxNumberOfGuests());
        accommodationUpdateDTO.setDefaultPrice(accommodation.getPricePerNight());
        accommodationUpdateDTO.setPricePerGuest(accommodation.getPricePerGuest());
        accommodationUpdateDTO.setCancellationDeadline(accommodation.getCancellationDeadline());
        accommodationUpdateDTO.setAutomaticHandling(accommodation.isAutomatic());

        Set<Image> images = new HashSet<>();
        for (String imagePath : accommodation.getImages()) {
            ImagesRepository imagesRepository = new ImagesRepository();
            String bytes = null;
            String type = null;
            try {
                bytes = imagesRepository.getImageBytes(imagePath);
                type = imagesRepository.getImageType(bytes);
            } catch (Exception ignored) { }
            Image image = new Image(bytes, type);
            images.add(image);
        }
        accommodationUpdateDTO.setImages(images);

        LocationDTO locationDTO = new LocationDTO(accommodation.getLocation());
        accommodationUpdateDTO.setLocation(locationDTO);

        Set<Long> amenities = accommodation.getAmenities().stream()
                .map(Amenity::getId)
                .collect(Collectors.toSet());
        accommodationUpdateDTO.setAmenities(amenities);

        Set<UpdateAvailabilityDTO> availabilities = accommodation.getAvailability().stream()
                .map(availability -> {
                    UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
                    updateAvailabilityDTO.setStartDate(availability.getStartDate());
                    updateAvailabilityDTO.setEndDate(availability.getEndDate());
                    updateAvailabilityDTO.setSpecialPrice(availability.getSpecialPrice());
                    return updateAvailabilityDTO;
                })
                .collect(Collectors.toSet());
        accommodationUpdateDTO.setAvailability(availabilities);
        return accommodationUpdateDTO;
    }

    public void removePastAvailabilities(Accommodation accommodation) {
        Set<Availability> availabilitiesToRemove = new HashSet<>();
        Set<Availability> availabilitiesToChange = new HashSet<>();
        for (Availability availability : accommodation.getAvailability()) {
            if (availability.getEndDate().isBefore(LocalDate.now())) {
                availabilitiesToRemove.add(availability);
            }
            else if (availability.getStartDate().isBefore(LocalDate.now()) && availability.getEndDate().isAfter(LocalDate.now())) {
                availabilitiesToChange.add(availability);
            }
        }
        for (Availability availability : availabilitiesToRemove) {
            accommodation.getAvailability().remove(availability);
        }
        for (Availability availability : availabilitiesToChange) {
            availability.setStartDate(LocalDate.now());
            availabilityService.save(availability);
        }
        this.save(accommodation);
    }

    public void setApprovedToFalseForAllOwnersApartments(Long ownerId) {
        List<Accommodation> accommodations = accommodationRepository.findAllByOwnerId(ownerId);
        for (Accommodation accommodation : accommodations) {
            accommodation.setApproved(false);
            accommodationRepository.save(accommodation);

            List<Guest> guests = userAccountService.findAllGuests();
            for (Guest guest : guests) {
                guest.getFavouriteAccommodations().remove(accommodation);
                userAccountService.save(guest);
            }

        }
    }

}

