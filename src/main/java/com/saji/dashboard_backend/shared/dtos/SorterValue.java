package com.saji.dashboard_backend.shared.dtos;

import com.saji.dashboard_backend.shared.enums.Sort;

import lombok.Data;

@Data
public class SorterValue {
    private String field;
    private Sort order;
}