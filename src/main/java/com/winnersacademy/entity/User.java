package com.winnersacademy.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winnersacademy.entity.encryption.PasswordEncrypter;
import com.winnersacademy.model.UserType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(type.name()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
