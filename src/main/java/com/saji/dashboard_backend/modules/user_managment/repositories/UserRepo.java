package com.saji.dashboard_backend.modules.user_managment.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.shared.repositories.BaseRepository;

@Repository
public interface UserRepo extends BaseRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.accountInformation.email = :email or u.accountInformation.username = :username")
    Optional<User> findByEmailOrUsername(String email, String username);

    @Query("SELECT true FROM User u WHERE u.accountInformation.email = :email")
    boolean existsByEmail(String email);

    @Query("SELECT u.id FROM User u WHERE u.accountInformation.email = :email")
    Long findIdByEmail(@Param("email") String email);

    @Modifying
    @Query("update User u set u.accountInformation.password = :password where u.id = :id")
    void changePassword(@Param("id") Long id, String password);

    @Modifying
    @Query("update User u set u.accountInformation.email = :email, u.personalInformation.firstName = :firstName, u.personalInformation.lastName = :lastName where u.id = :id")
    void updateUserProfile(String email, String firstName, String lastName, Long id);
}
