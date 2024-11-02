package com.saji.dashboard_backend.shared.utils.parser;

public class BooleanParser implements ParsingStrategy {
    @Override
    public Object parse(String value) {
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        }
        return null; // Indicates this parser can't handle the value
    }
}
