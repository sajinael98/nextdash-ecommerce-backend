package com.saji.dashboard_backend.shared.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueFilterValidator {
    private final static String VALUE_FILTER_PATTERN = "filters\\[(\\d+)\\]\\[(field|operator|value)\\]";
    private final static String VALUE_FILTER_FIELD_PATTERN = "filters\\[(\\d+)\\]\\[field\\]";
    private final static String VALUE_FILTER_OPERATOR_PATTERN = "filters\\[(\\d+)\\]\\[operator\\]";
    private final static String VALUE_FILTER_VALUE_PATTERN = "filters\\[(\\d+)\\]\\[value\\]";
    public static boolean isValueFilter(String key) {
        return isValid(VALUE_FILTER_PATTERN, key);
    }

    public static String extractKey(String key) {
        if (isValueFilter(key)) {
            Pattern pattern = Pattern.compile(VALUE_FILTER_PATTERN);
            Matcher matcher = pattern.matcher(key);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }
        throw new IllegalArgumentException(key + " is invalid!");
    }

    public static boolean isValueFilterField(String key) {
        return isValid(VALUE_FILTER_FIELD_PATTERN, key);
    }

    public static boolean isValueFilterOperator(String key) {
        return isValid(VALUE_FILTER_OPERATOR_PATTERN, key);
    }

    public static boolean isValueFilterValue(String key) {
        return isValid(VALUE_FILTER_VALUE_PATTERN, key);
    }

    private static boolean isValid(String patternStr, String value) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}