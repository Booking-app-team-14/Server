package com.bookingapp.repositories;

import com.bookingapp.dtos.NotificationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationIRepository extends JpaRepository<NotificationDTO, Long> {


}
