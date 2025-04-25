package com.saji.dashboard_backend.shared.repositories.base;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.shared.specifications.SearchCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

public class GenericJpaRepositoryCustomImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements GenericJpaRepositoryCustom<T> {

    private final EntityManager em;

    public GenericJpaRepositoryCustomImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.em = em;
    }
    
    @Override
    public List<Map<String, Object>> fetchValues(Class<T> entityClass, List<String> fields,
            List<SearchCriteria> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<T> root = query.from(entityClass);

        List<Selection<?>> selections = fields.stream()
                .map(f -> getPath(root, f).alias(f))
                .collect(Collectors.toList());

        query.multiselect(selections);

        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria sc : filters) {
            Path<Object> path = getPath(root, sc.getField());
            Object value = parseValue(sc.getValue(), path.getJavaType());

            switch (sc.getOperation()) {
                case "=": {
                    predicates.add(cb.equal(path, value));
                    break;
                }
            }
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        List<Tuple> results = em.createQuery(query).getResultList();

        return results.stream()
                .map(t -> {
                    Map<String, Object> row = new HashMap<>();
                    for (String f : fields) {
                        row.put(f, t.get(f)); // alias is same as field name
                    }
                    return row;
                })
                .collect(Collectors.toList());
    }

    private Object parseValue(String value, Class<?> type) {
        if (type == String.class)
            return value;
        if (type == Integer.class || type == int.class)
            return Integer.parseInt(value);
        if (type == Long.class || type == long.class)
            return Long.parseLong(value);
        if (type == Boolean.class || type == boolean.class)
            return Boolean.parseBoolean(value);
        if (type == Double.class || type == double.class)
            return Double.parseDouble(value);
        if (type == LocalDate.class)
            return LocalDate.parse(value);
        if (type == LocalDateTime.class)
            return LocalDateTime.parse(value);
        return value;
    }

    private Path<Object> getPath(Root<T> root, String fieldName) {
        String[] parts = fieldName.split("\\.");
        Path<Object> path = root.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            path = path.get(parts[i]);
        }
        return path;
    }
}
