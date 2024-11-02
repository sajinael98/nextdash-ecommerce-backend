package com.saji.dashboard_backend.shared.utils.parser;

public class DoubleParser implements ParsingStrategy {
    @Override
    public Object parse(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}