package com.saji.dashboard_backend.shared.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Sort {
    ASC("asc"),
    DESC("desc");

    @Getter
    private final String order;
}