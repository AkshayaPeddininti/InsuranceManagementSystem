package com.cognizant.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.insurance.entity.Notifications;
@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {

}
