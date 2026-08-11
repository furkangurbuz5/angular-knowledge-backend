package nl.furka.angularknowledge.model.filter;

import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.List;

public final class FilterFieldBuilder {
    public static List<FilterField> buildFilterFieldsFrom(final Filter filter) {
        if (filter == null) return List.of();

        Class<?> clazz = filter.getClass();
        if (!clazz.isRecord()) {
            throw new IllegalArgumentException("Filter must be a record. Got: " + clazz.getName());
        }

        List<FilterField> result = new ArrayList<>();

        for (RecordComponent component : clazz.getRecordComponents()) {
            Object value;
            try {
                value = component.getAccessor().invoke(filter);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to read filter component: " + component.getName(), e);
            }

            if (value == null) continue;

            String field = component.getName();
            String column = fieldToColumn(field);
            String sqlOperator = operatorForValue(value, component.getType());

            result.add(new FilterField(field, column, sqlOperator));
        }

        return result;
    }

    private static String fieldToColumn(String field) {
        return field.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    private static String operatorForValue(Object value, Class<?> type) {
        if (type == Integer.class || type == int.class) return "=";
        if (type == Long.class || type == long.class) return "=";

        if (value instanceof String) return "LIKE"; // use "LIKE" if not on Postgres
        return "=";
    }
}

