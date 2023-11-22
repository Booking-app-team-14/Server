package com.bookingapp.services;

import com.bookingapp.entities.UserReport;
import com.bookingapp.repositories.UserReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReportService {

    @Autowired
    private UserReportRepository userReportRepository;

    public List<UserReport> findAll() {
        return userReportRepository.findAll();
    }

    public UserReport findOne(Long id) {
        return userReportRepository.findById(id).orElse(null);
    }

    public void remove(Long id) {
        userReportRepository.deleteById(id);
    }

    public UserReport save(UserReport userReport) {
        return userReportRepository.save(userReport);
    }

}
