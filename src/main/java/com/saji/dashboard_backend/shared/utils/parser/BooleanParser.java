package com.saji.dashboard_backend.shared.utils.parser;

public class BooleanParser implements ParsingStrategy {
    @Override
    public Object parse(String value) {
        if ("Yes".equalsIgnoreCase(value) || "No".equalsIgnoreCase(value)) {
            return value.equals("Yes") ;
        }
        return null; // Indicates this parser can't handle the value
    }
}
