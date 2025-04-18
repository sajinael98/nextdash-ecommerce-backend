package com.saji.dashboard_backend.shared.specifications;

import org.springframework.data.domain.Sort;

import com.saji.dashboard_backend.shared.dtos.FilterRequest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryCriteriaBuilder {

    private final List<SearchCriteria> filters = new ArrayList<>();
    private final List<SortCriteria> sorts = new ArrayList<>();

    private QueryCriteriaBuilder() {
    }

    public static QueryCriteriaBuilder builder() {
        return new QueryCriteriaBuilder();
    }

    public QueryCriteriaBuilder add(String field, String operator, String value) {
        filters.add(new SearchCriteria(field, operator, value));
        return this;
    }

    public QueryCriteriaBuilder sortBy(String field, Sort.Direction direction) {
        sorts.add(new SortCriteria(field, direction));
        return this;
    }

    public List<SearchCriteria> buildFilters() {
        return filters;
    }

    public Sort buildSort() {
        if (sorts.isEmpty()) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (SortCriteria sc : sorts) {
            orders.add(new Sort.Order(sc.getDirection(), sc.getField()));
        }

        return Sort.by(orders);
    }

    public static List<SearchCriteria> parseFiltersFromParams(Map<String, String> params) {
        Pattern pattern = Pattern.compile("filters\\[(\\d+)]\\[(\\w+)]");
        Map<Integer, FilterRequest> filterMap = new HashMap<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            Matcher matcher = pattern.matcher(entry.getKey());
            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group(1));
                String property = matcher.group(2);
                String value = entry.getValue();

                filterMap.putIfAbsent(index, new FilterRequest());
                FilterRequest filter = filterMap.get(index);

                switch (property) {
                    case "field":
                        filter.setField(value);
                        break;
                    case "operator":
                        filter.setOperator(value);
                        break;
                    case "value":
                        filter.setValue(value);
                        break;
                }
            }
        }

        List<SearchCriteria> criteriaList = new ArrayList<>();
        for (FilterRequest filter : filterMap.values()) {
            criteriaList.add(new SearchCriteria(filter.getField(), filter.getOperator(), filter.getValue()));
        }

        return criteriaList;
    }

    public static List<SortCriteria> parseSortsFromParams(Map<String, String> params) {
        Pattern pattern = Pattern.compile("sorters\\[(\\d+)]\\[(\\w+)]");
        Map<Integer, SortCriteria> sortMap = new HashMap<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            Matcher matcher = pattern.matcher(entry.getKey());

            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group(1));
                String property = matcher.group(2);
                String value = entry.getValue();

                sortMap.putIfAbsent(index, new SortCriteria(null, Sort.Direction.ASC));
                SortCriteria current = sortMap.get(index);

                switch (property) {
                    case "field":
                        current = new SortCriteria(value, current.getDirection());
                        break;
                    case "order":
                        current = new SortCriteria(current.getField(), Sort.Direction.fromString(value));
                        break;
                }

                sortMap.put(index, current);
            }
        }

        return new ArrayList<>(sortMap.values());
    }

    public static Sort buildSort(List<SortCriteria> sortCriteriaList) {
        if (sortCriteriaList == null || sortCriteriaList.isEmpty()) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (SortCriteria sc : sortCriteriaList) {
            orders.add(new Sort.Order(sc.getDirection(), sc.getField()));
        }

        return Sort.by(orders);
    }
}
