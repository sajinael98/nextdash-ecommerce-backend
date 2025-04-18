package com.saji.dashboard_backend.shared.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterRequest {
    private String field;
    private String operator;
    private String value;
}
