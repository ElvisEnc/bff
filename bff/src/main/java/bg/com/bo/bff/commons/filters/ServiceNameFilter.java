package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.dtos.response.payment.service.ServiceResponse;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.util.List;

public class ServiceNameFilter implements IFilter<ServiceResponse> {
    private final String searchText;
    public ServiceNameFilter(String searchText) {
        this.searchText = searchText;
    }
    @Override
    public List<ServiceResponse> apply(List<ServiceResponse> list) {
        if (searchText == null || searchText.isEmpty()) {
            return list;
        }
        return list.stream()
                .filter(service -> service.getServiceName() != null && service.getServiceName().toLowerCase().contains(searchText.toLowerCase()))
                .toList();
    }
}
