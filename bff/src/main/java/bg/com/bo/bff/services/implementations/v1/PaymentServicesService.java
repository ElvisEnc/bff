package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.SubcategoriesResponse;
import bg.com.bo.bff.providers.dtos.response.payment.services.SubcategoriesMWResponse;
import bg.com.bo.bff.providers.interfaces.IPaymentServicesProvider;
import bg.com.bo.bff.providers.mappings.payment.services.IPaymentServicesMapper;
import bg.com.bo.bff.services.interfaces.IPaymentServicesService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class PaymentServicesService implements IPaymentServicesService {

    private final IPaymentServicesProvider provider;
    private final IPaymentServicesMapper mapper;

    public PaymentServicesService(IPaymentServicesProvider provider, IPaymentServicesMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public SubcategoriesResponse getSubcategories(Integer categoryId, Map<String, String> parameter) throws IOException {
        final SubcategoriesMWResponse result = provider.getSubcategories(categoryId, parameter);
        return mapper.convertResponse(result);
    }
}
