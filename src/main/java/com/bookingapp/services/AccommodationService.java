package com.bookingapp.services;

import com.bookingapp.dtos.*;
import com.bookingapp.entities.*;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.AccommodationRepository;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.repositories.LocationRepository;
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

        fillAvailabilityForCurrentYear(accommodation, accommodationDTO.getAvailability());

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

    public boolean uploadAccommodationImage(Long id, String imageBytes) {
        String imageType = null;
        try {
            imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException e) {
            return false;
        }

        deleteAccommodationImage(id);

        String relativePath = String.format("accommodations\\accommodation-%d", id);
        String directoryPath = String.format("src/main/resources/images/accommodations/accommodation-%d", id);
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        /*relativePath += File.separator;
        relativePath += "." + imageType;*/
        String filename = UUID.randomUUID().toString();
        String imagePath = String.format("%s%s%s.%s", relativePath, File.separator, filename, imageType);
        try {
            imagesRepository.addImage(imageBytes, imageType, imagePath);
        } catch (Exception e) {
            return false;
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

    public Optional<Accommodation> findById(Long id) {
        return accommodationRepository.findById(id);
    }
}

