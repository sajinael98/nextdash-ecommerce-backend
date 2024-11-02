package com.saji.dashboard_backend.shared.utils.parser;

public class IntegerParser implements ParsingStrategy {
    @Override
    public Object parse(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
