package com.saji.dashboard_backend.shared.mappers;

import org.springframework.beans.BeanUtils;

import com.saji.dashboard_backend.shared.dtos.BaseRequest;
import com.saji.dashboard_backend.shared.dtos.BaseResponse;
import com.saji.dashboard_backend.shared.entites.BaseEntity;

public interface BaseMapper<T extends BaseEntity> {
    default T convertRequestToEntity(T entity, BaseRequest req) {
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    default BaseResponse convertEntityToResponse(T entity) {
        return null;
    }
}
