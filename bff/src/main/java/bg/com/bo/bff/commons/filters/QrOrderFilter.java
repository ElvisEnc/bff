package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.enums.qr.OrderFieldFilter;

import java.util.Comparator;
import java.util.List;

public class QrOrderFilter {
    private final String field;
    private final boolean desc;

    public QrOrderFilter(OrderRequest request) {
        this.field = request != null ? request.getField() : OrderFieldFilter.REGISTRATION_DATE.getCode();
        this.desc = request == null || request.getDesc();
    }

    public List<QrGeneratedPaid> apply(List<QrGeneratedPaid> list) {
        OrderFieldFilter orderField = OrderFieldFilter.findByDescription(field);
        if (orderField == null)
            throw new GenericException("Invalid field: " + field, AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());

        Comparator<QrGeneratedPaid> comparator = getComparatorForField(orderField);
        list.sort(desc ? comparator.reversed() : comparator);
        return list;
    }

    private Comparator<QrGeneratedPaid> getComparatorForField(OrderFieldFilter field) {
        return switch (field) {
            case REGISTRATION_DATE ->
                    Comparator.comparing(QrGeneratedPaid::getRegistrationDate, Comparator.nullsLast(Comparator.naturalOrder()));
            case EXPIRATION_DATE ->
                    Comparator.comparing(QrGeneratedPaid::getExpiryDate, Comparator.nullsLast(Comparator.naturalOrder()));
            case AMOUNT ->
                    Comparator.comparing(QrGeneratedPaid::getAmount, Comparator.nullsLast(Comparator.naturalOrder()));
        };
    }
}
