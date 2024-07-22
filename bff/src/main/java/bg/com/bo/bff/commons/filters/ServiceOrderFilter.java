package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.dtos.request.payment.service.ListServiceRequest;
import bg.com.bo.bff.application.dtos.response.payment.service.ServiceResponse;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ServiceOrderFilter implements IFilter<ServiceResponse> {
    private static final String SERVICE_NAME = "SERVICE_NAME";
    private final String field;
    private final boolean desc;

    public ServiceOrderFilter(ListServiceRequest.Filter filters) {
        this.field = filters.getOrder().getField();
        this.desc = filters.getOrder().getDesc();
    }

    @Override
    public List<ServiceResponse> apply(List<ServiceResponse> list) {
        Comparator<ServiceResponse> comparator;
        if (Objects.equals(field, SERVICE_NAME)) {
            comparator = Comparator.comparing(ServiceResponse::getServiceName, Comparator.nullsLast(Comparator.naturalOrder()));
        } else {
            return list;
        }

        list.sort(desc ? comparator.reversed() : comparator);
        return list;
    }
}

