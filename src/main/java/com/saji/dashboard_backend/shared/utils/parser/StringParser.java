package com.saji.dashboard_backend.shared.utils.parser;

public class StringParser implements ParsingStrategy {
    @Override
    public Object parse(String value) {
        return value; // Everything is a String if it reaches here
    }
}