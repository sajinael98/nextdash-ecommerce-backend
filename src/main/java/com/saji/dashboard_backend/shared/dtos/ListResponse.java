package com.saji.dashboard_backend.shared.dtos;

import java.util.List;

import com.saji.dashboard_backend.shared.entites.BaseEntity;

import lombok.Data;

@Data
public class ListResponse<T> {
    private List<T> data;
    private Long total;
}