package com.cognizant.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.insurance.entity.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {

}
