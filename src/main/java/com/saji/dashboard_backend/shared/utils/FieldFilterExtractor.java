package com.saji.dashboard_backend.shared.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.saji.dashboard_backend.shared.dtos.ValueFilter;
import com.saji.dashboard_backend.shared.utils.parser.ParserContext;

public class FieldFilterExtractor implements FilterExtractor<Collection<ValueFilter>> {

    @Override
    public Collection<ValueFilter> getFilters(Map<String, Object> filters) {
        Map<String, ValueFilter> temp = new HashMap<>();
        ParserContext parser = new ParserContext();

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            if (ValueFilterValidator.isValueFilter(key)) {
                String keyNumber = ValueFilterValidator.extractKey(key);
                if (!temp.containsKey(keyNumber)) {
                    temp.put(keyNumber, new ValueFilter());
                }
                if (ValueFilterValidator.isValueFilterField(key)) {
                    temp.get(keyNumber).setField(value.toString());
                } else if (ValueFilterValidator.isValueFilterOperator(key)) {
                    temp.get(keyNumber).setOperator(value.toString());
                } else if (ValueFilterValidator.isValueFilterValue(key)) {
                    ValueFilter valueFilter = temp.get(keyNumber);
                    if(valueFilter.getOperator().equals("in")){
                        if(valueFilter.getValue() == null){
                            valueFilter.setValue(new ArrayList<>());
                        }
                        ((ArrayList)valueFilter.getValue()).add(parser.parseValue(value));
                    }else{
                        valueFilter.setValue(parser.parseValue(value));
                    }
                }
            }
        }

        return temp.values();
    }
}