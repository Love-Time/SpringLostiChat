package com.example.demo.repository;

import com.example.demo.entity.Friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByFirstUserIdOrSecondUserId(Long firstUserId, Long secondUserId);

    @Query(value = "SELECT EXISTS(*) from friend "
            + "WHERE ((first_user = :MyId And second_user = :FriendId)  Or (second_user = :MyId AND first_user = :FriendId)) and accept='ACCEPT'"
            , nativeQuery = true)
    Boolean IsMyFriend(@Param("MyId") Long myId, @Param("FriendId") Long FriendId);

    @Query(value = "SELECT * from friend "
            + "WHERE (first_user = :MyId And second_user = :FriendId)  Or (second_user = :MyId AND first_user = :FriendId)"
            , nativeQuery = true)
    Optional<Friend> findFriend(@Param("MyId") Long myId, @Param("FriendId") Long FriendId);
}
