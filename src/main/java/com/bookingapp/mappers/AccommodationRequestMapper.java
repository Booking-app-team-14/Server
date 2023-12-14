package com.bookingapp.mappers;

import com.bookingapp.dtos.AccommodationRequestDTO;
import com.bookingapp.entities.AccommodationRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccommodationRequestMapper {

    private final ModelMapper modelMapper;

    public AccommodationRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // TODO: add mapping logic

    public AccommodationRequestDTO toDTO(AccommodationRequest accommodationRequest) {
        AccommodationRequestDTO dto = modelMapper.map(accommodationRequest, AccommodationRequestDTO.class);
//        Accommodation accommodation = accommodationRequest.getAccommodation();
//        dto.setName(accommodation.getName());
//        dto.setType(accommodation.getType());
//        dto.setPostedAgo((int) ChronoUnit.MINUTES.between(accommodationRequest.getDateRequested(), LocalDateTime.now()));
//        Owner owner = accommodationRequest.getOwner();
//        dto.setOwnerImageType(owner.getProfilePictureType());
//        dto.setOwnerProfilePictureBytes(owner.getProfilePictureBytes());
//        dto.setOwnerUsername(owner.getUsername());
//        dto.setDateRequested(accommodationRequest.getDateRequested().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        dto.setStars(accommodation.getStars());
//        dto.setImageType(accommodation.getMainPictureType());
//        dto.setMainPictureBytes(accommodation.getMainPictureBytes());
        return dto;
    }

    public AccommodationRequest toEntity(AccommodationRequestDTO dto) {
        AccommodationRequest entity = modelMapper.map(dto, AccommodationRequest.class);
//        entity.setAccommodation(new Accommodation(dto.getName(), dto.getType(), dto.getStars(), dto.getImageType(), dto.getMainPictureBytes()));
//        entity.setOwner(new Owner(dto.getOwnerUsername(), dto.getOwnerImageType(), dto.getOwnerProfilePictureBytes()));
//        entity.setDateRequested(LocalDateTime.parse(dto.getDateRequested(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return entity;
    }
}
