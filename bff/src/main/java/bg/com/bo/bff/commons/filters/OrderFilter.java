package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class OrderFilter<T> implements IFilter<T> {
    private final String field;
    private final boolean desc;
    private final Map<String, Function<T, ? extends Comparable<?>>> comparatorOptions;

    public OrderFilter(String field, boolean desc, Map<String, Function<T, ? extends Comparable<?>>> comparatorOptions) {
        this.field = field;
        this.desc = desc;
        this.comparatorOptions = comparatorOptions;
    }

    @Override
    public List<T> apply(List<T> list) {
        List<T> modifiableList = new ArrayList<>(list);
        Comparator<T> comparator = getComparatorForField(field);
        modifiableList.sort(desc ? comparator.reversed() : comparator);
        return modifiableList;
    }

    private Comparator<T> getComparatorForField(String field) {
        Function<T, ? extends Comparable<?>> keyExtractor = comparatorOptions.get(field.toUpperCase());
        if (keyExtractor == null) {
            throw new GenericException(field + " - El campo esperado no es correcto.", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode(), "Datos inválidos");
        }
        return Comparator.comparing(
                keyExtractor,
                Comparator.nullsLast((Comparator) Comparator.naturalOrder())
        );
    }
}
