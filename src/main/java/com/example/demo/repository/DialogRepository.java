package com.example.demo.repository;

import com.example.demo.entity.Dialog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {

    @Query(value = "SELECT * from dialog "
            + "WHERE sender_id = :userId or recipient_id = :userId "
            + "GROUP BY id, sender_id, recipient_id "
            + "ORDER BY date_time DESC", nativeQuery = true)
    List<Dialog> findDialogsByUserId(@Param("userId") Long userId);

}
