package com.enigma.chateringapp.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_user_credential")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
