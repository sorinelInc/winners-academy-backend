package com.winnersacademy.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winnersacademy.entity.encryption.PasswordEncrypter;
import com.winnersacademy.model.UserType;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @Convert(converter = PasswordEncrypter.class)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType type;
}
