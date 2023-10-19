package com.example.demo.repository;

import com.example.demo.entity.FriendNotify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendNotifyRepository extends JpaRepository<FriendNotify, Long> {
}
