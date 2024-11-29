package com.saji.dashboard_backend.shared.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortFilterValidator {
    private final static String SORTER_PATTERN = "sorters\\[(\\d+)\\]\\[(field|order)\\]";
    private final static String SORTER_FIELD_PATTERN = "sorters\\[(\\d+)\\]\\[field\\]";
    private final static String SORTER_ORDER_PATTERN = "sorters\\[(\\d+)\\]\\[order\\]";

    public static boolean isValidSorterPattern(String key) {
        return isValid(SORTER_PATTERN, key);
    }

    public static String extractKey(String key) {
        if (isValidSorterPattern(key)) {
            Pattern pattern = Pattern.compile(SORTER_PATTERN);
            Matcher matcher = pattern.matcher(key);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }
        throw new IllegalArgumentException(key + " is invalid!");
    }

    public static boolean isValidSorterFieldPattern(String key) {
        return isValid(SORTER_FIELD_PATTERN, key);
    }

    public static boolean isValidSorterOrderPattern(String key) {
        return isValid(SORTER_ORDER_PATTERN, key);
    }

    private static boolean isValid(String patternStr, String value) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}