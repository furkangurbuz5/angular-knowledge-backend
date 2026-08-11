package nl.furka.angularknowledge.util;

import nl.furka.angularknowledge.model.filter.Filter;
import nl.furka.angularknowledge.model.filter.FilterField;

import java.util.List;
import java.util.Map;

import static nl.furka.angularknowledge.model.filter.FilterFieldBuilder.buildFilterFieldsFrom;

public class SqlUtil {
    public static String buildWhereClause(Filter filter, Map<String, Object> params) {
        StringBuilder whereClause = new StringBuilder();

        List<FilterField> filterableFields = buildFilterFieldsFrom(filter);

        filterableFields.forEach(filterField -> {
            Object value = getFieldValue(filter, filterField.field());
            if (value != null && !value.toString().isBlank()) {
                String paramName = filterField.field();
                String dbColumn = filterField.column();
                String sqlOperator = filterField.sqlOperator();

                if (sqlOperator.equals("LIKE")) {
                    whereClause.append(String.format(
                            " AND LOWER(%s) LIKE LOWER(:%s)", dbColumn, paramName
                    ));
                    params.put(paramName, "%" + value + "%");
                } else {
                    whereClause.append(String.format(
                            " AND %s %s :%s", dbColumn, sqlOperator, paramName
                    ));
                    params.put(paramName, value);
                }
            }
        });
        return whereClause.toString();
    }

    private static Object getFieldValue(Filter filter, String fieldName) {
        try {
            return filter.getClass().getMethod(fieldName).invoke(filter);
        } catch (Exception e) {
            return null;
        }
    }
}
