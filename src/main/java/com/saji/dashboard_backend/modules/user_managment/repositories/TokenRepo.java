package com.saji.dashboard_backend.modules.user_managment.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.user_managment.entities.Token;
import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepository;

@Repository
public interface TokenRepo extends GenericJpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokensByUser(Long userId);
}
