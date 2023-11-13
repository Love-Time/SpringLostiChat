package com.example.demo.repository;

import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.dto.dialog.DialogView;
import com.example.demo.entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.AbstractJpaQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;


@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {

    //    @Query(value = "SELECT * from dialog "
//            + "WHERE sender_id = :userId or recipient_id = :userId "
//            + "GROUP BY id, sender_id, recipient_id "
//            + "ORDER BY date_time DESC", nativeQuery = true)
//u1.id, u1.name, u2.id, u2.name
    @Query(value = "SELECT dialog.id, message, is_read as isRead, date_time as dateTime, u1.id as user1Id, " +
            "u1.name as user1Name, u2.id as user2Id, u2.name as user2Name " +
            "FROM dialog INNER JOIN\n" +
            "(SELECT id, first_name || ' ' || last_name as name from users) as u1 on sender_id=u1.id  INNER JOIN\n" +
            "(select id, first_name || ' ' || last_name as name from users) as u2 on recipient_id=u2.id\n" +
            "\n" +
            "WHERE dialog.id IN \n" +
            "(SELECT max(id) as id FROM dialog \n" +
            "WHERE sender_id = 1 OR recipient_id = 1\n" +
            "GROUP BY sender_id + recipient_id)"
            , nativeQuery = true)
    List<DialogView> findDialogsByUserId(@Param("userId") Long userId);

    List<Dialog> findDialogsBySenderIdOrRecipientId(Long sender_id, Long recipient_id);

}
