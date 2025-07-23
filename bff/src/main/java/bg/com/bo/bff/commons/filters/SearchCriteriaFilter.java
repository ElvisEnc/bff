package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class SearchCriteriaFilter<T> implements IFilter<T> {
    private final List<String> parameters;
    private final String value;
    private Set<String> relevantKeys;

    public SearchCriteriaFilter(List<String> parameters, String value) {
        this.parameters = parameters.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        this.value = value.toLowerCase().trim();
    }

    @Override
    public List<T> apply(List<T> listArrayItems) {
        if (listArrayItems.isEmpty()) {
            return Collections.emptyList();
        }

        extractRelevantKeysFromFirstItem(listArrayItems.get(0));

        return listArrayItems.stream()
                .filter(this::matchesRelevantKeys)
                .collect(Collectors.toList());
    }

    private void extractRelevantKeysFromFirstItem(T firstItem) {
        relevantKeys = getObjectFields(firstItem).entrySet().stream()
                .map(Map.Entry::getKey)
                .filter(key -> parameters.contains(key.toUpperCase()))
                .collect(Collectors.toSet());
    }

    private boolean matchesRelevantKeys(T item) {
        Map<String, Object> itemFields = getObjectFields(item);

        for (String key : relevantKeys) {
            Object fieldValue = itemFields.get(key);
            if (fieldValue != null && fieldValue.toString().toLowerCase().contains(value)) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Object> getObjectFields(T item) {
        Map<String, Object> fields = new HashMap<>();
        try {
            for (Field field : item.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                fields.put(field.getName(), field.get(item));
            }
        } catch (IllegalAccessException e) {
            throw new GenericException("El campo esperado no es correcto", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
        }
        return fields;
    }
}
