package com.saji.dashboard_backend.shared.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.saji.dashboard_backend.shared.dtos.SorterValue;
import com.saji.dashboard_backend.shared.enums.Sort;

public class FieldSorterExtractor implements FilterExtractor<Collection<SorterValue>> {

    @Override
    public Collection<SorterValue> getFilters(Map<String, Object> sorters) {
        Map<String, SorterValue> temp = new HashMap<>();

        for (Map.Entry<String, Object> entry : sorters.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            if (SortFilterValidator.isValidSorterPattern(key)) {
                String keyNumber = SortFilterValidator.extractKey(key);
                if (!temp.containsKey(keyNumber)) {
                    temp.put(keyNumber, new SorterValue());
                }
                if (SortFilterValidator.isValidSorterFieldPattern(key)) {
                    temp.get(keyNumber).setField(value.toString());
                }
                if (SortFilterValidator.isValidSorterOrderPattern(key)) {
                    if (value.equals("asc")) {
                        temp.get(keyNumber).setOrder(Sort.ASC);
                    } else {
                        temp.get(keyNumber).setOrder(Sort.DESC);
                    }
                }
            }
        }

        return temp.values();
    }

}
