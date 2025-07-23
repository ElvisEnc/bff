package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;
import bg.com.bo.bff.commons.utils.UtilDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class DateFilter<T> implements IFilter<T> {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Function<T, String> dateExtractor;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DateFilter(String startDate, String endDate, Function<T, String> dateExtractor) {
        this.startDate = LocalDate.parse(startDate, DATE_FORMATTER);
        this.endDate = LocalDate.parse(endDate, DATE_FORMATTER);
        if (this.startDate.isAfter(this.endDate)) {
            throw new GenericException("La fecha de inicio no puede ser después de la fecha de fin", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
        }
        this.dateExtractor = dateExtractor;
    }

    @Override
    public List<T> apply(List<T> list) {
        return list.stream()
                .filter(item -> {
                    String dateStr = UtilDate.getDateGenericFormat(dateExtractor.apply(item));
                    if (dateStr == null) {
                        return false;
                    }
                    LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
                    return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate));
                })
                .toList();
    }
}
