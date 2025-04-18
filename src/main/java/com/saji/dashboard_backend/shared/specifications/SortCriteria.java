package com.saji.dashboard_backend.shared.specifications;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortCriteria {
    private String field;
    private Sort.Direction direction;
}
