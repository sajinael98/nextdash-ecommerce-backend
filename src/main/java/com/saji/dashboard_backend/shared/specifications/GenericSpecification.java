package com.saji.dashboard_backend.shared.specifications;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GenericSpecification<T> implements Specification<T> {

    private final List<SearchCriteria> criteriaList;

    public GenericSpecification(List<SearchCriteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate predicate = builder.conjunction(); // AND

        for (SearchCriteria criteria : criteriaList) {

            Class<?> fieldType = root.get(criteria.getField()).getJavaType();

            Object value = parseValue(criteria.getValue(), fieldType);
            System.out.println(value);
            switch (criteria.getOperation()) {
                case "eq":
                    predicate = builder.and(predicate,
                            builder.equal(root.get(criteria.getField()), value));
                    break;
                default:
                    throw new RuntimeException("operator: " + criteria.getOperation() + " not supported");
            }
        }

        return predicate;
    }

    private Object parseValue(String value, Class<?> type) {
        if (type == String.class)
            return value;
        if (type == Integer.class || type == int.class)
            return Integer.parseInt(value);
        if (type == Long.class || type == long.class)
            return Long.parseLong(value);
        if (type == Double.class || type == double.class)
            return Double.parseDouble(value);
        if (type == Boolean.class || type == boolean.class)
            return Boolean.parseBoolean(value);
        if (type == LocalDate.class)
            return LocalDate.parse(value); // يمكن استخدام DateTimeFormatter مخصص
        if (type == LocalDateTime.class)
            return LocalDateTime.parse(value);

        // ✅ دعم enum
        if (type.isEnum()) {
            return Enum.valueOf((Class<Enum>) type, value.toUpperCase());
        }

        throw new IllegalArgumentException("Unsupported field type: " + type.getName());
    }
}
