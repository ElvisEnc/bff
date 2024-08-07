package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.util.List;
import java.util.function.Function;

public class RangeFilter<T, U extends Comparable<U>> implements IFilter<T> {
    private final U minValue;
    private final U maxValue;
    private final Function<T, U> valueExtractor;

    public RangeFilter(U minValue, U maxValue, Function<T, U> valueExtractor) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.valueExtractor = valueExtractor;
    }

    @Override
    public List<T> apply(List<T> list) {
        return list.stream()
                .filter(item -> {
                    U value = valueExtractor.apply(item);
                    boolean isMinValid = minValue == null || value.compareTo(minValue) >= 0;
                    boolean isMaxValid = maxValue == null || value.compareTo(maxValue) <= 0;
                    return isMinValid && isMaxValid;
                })
                .toList();
    }
}
