package com.bookingapp.services;

import com.bookingapp.dtos.AmenityDTO;
import com.bookingapp.entities.Amenity;
import com.bookingapp.repositories.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Set;

@Service
public class AmenityService {

    @Autowired
    private AmenityRepository amenityRepository;

    public void save(Amenity amenity) {
        amenityRepository.save(amenity);
    }

    public List<Amenity> findAllById(Set<Long> amenityIds) {
        return amenityRepository.findAllById(amenityIds);
    }

    public Amenity findById(Long id) {
        if (this.amenityRepository == null) {
            System.out.println("AmenityRepository is null???????????????");
        }

        return this.amenityRepository.findById(id).get();
    }

    public String findAmenityImageName(Long id) {
        File mainDirectory = new File("src\\main\\resources\\images\\amenities");

        File[] files = mainDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                if (filename.startsWith("icon" + id)) {
                    return "amenities\\" + file.getName();
                }
            }
        }
        return null;
    }

    public List<AmenityDTO> findAll() {
        List<Amenity> amenities = amenityRepository.findAll();
        return AmenityDTO.transformToDTO(amenities);
    }
}
