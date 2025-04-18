package com.saji.dashboard_backend.shared.repositories;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.shared.entites.Setting;
import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepository;

@Repository
public interface SettingRepo extends GenericJpaRepository<Setting, Long> {
}
