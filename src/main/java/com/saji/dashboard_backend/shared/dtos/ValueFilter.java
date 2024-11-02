package com.saji.dashboard_backend.shared.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValueFilter {
    private String field;
    private String operator;
    private Object value;
}