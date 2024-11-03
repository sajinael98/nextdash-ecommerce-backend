package com.saji.dashboard_backend.shared.mappers;

import org.springframework.beans.BeanUtils;

import com.saji.dashboard_backend.shared.dtos.BaseDto;
import com.saji.dashboard_backend.shared.entites.BaseEntity;

public interface BaseMapper<Entity extends BaseEntity, EntityDto extends BaseDto> {
    default Entity convertRequestToEntity(EntityDto dto) {
        Entity entity = createEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    default EntityDto convertEntityToResponse(Entity entity) {
        EntityDto dto = createEntityDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    Entity createEntity();

    EntityDto createEntityDto();
}
