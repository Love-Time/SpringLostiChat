package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "first_user", referencedColumnName = "id")
    private User firstUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "second_user", referencedColumnName = "id")
    private User secondUser;

    @Enumerated(EnumType.STRING)
    private FriendAccept status;
}
