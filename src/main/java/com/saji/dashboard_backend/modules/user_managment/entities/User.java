package com.saji.dashboard_backend.modules.user_managment.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saji.dashboard_backend.shared.entites.BaseEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sysusers")
@JsonIgnoreProperties({ "password" })
@Data
public class User extends BaseEntity implements UserDetails {
    @Embedded
    private PersonalInformation personalInformation = new PersonalInformation();

    @Embedded
    private AccountInformation accountInformation = new AccountInformation();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId", nullable = false), uniqueConstraints = @UniqueConstraint(columnNames = {
            "userId" }))
    private Set<UserRole> roles = new HashSet<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.accountInformation.getPassword();
    }
    
    @Override
    public String getUsername() {
        return this.accountInformation.getUsername();
    }
}
