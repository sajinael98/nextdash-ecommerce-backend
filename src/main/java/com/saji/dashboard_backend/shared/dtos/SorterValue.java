package com.saji.dashboard_backend.shared.dtos;

import com.saji.dashboard_backend.shared.enums.Sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SorterValue {
    private String field;
    private Sort order;
}