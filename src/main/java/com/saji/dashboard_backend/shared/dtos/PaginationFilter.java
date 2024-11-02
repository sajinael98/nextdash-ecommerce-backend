package com.saji.dashboard_backend.shared.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaginationFilter implements Filter {
    private final Integer page;
    private final Integer size;
}