package bg.com.bo.bff.application.dtos.request.payment.service;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.DataRegisterServiceAffiliation;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.DataServiceAffiliation;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.DependencyServiceAffiliation;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.ServiceAffiliationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;

import java.math.BigDecimal;
import java.util.List;

public class PaymentServiceRequestFixture {
    public static AffiliationDebtsRequest withDefaultDebtsRequest() {
        return new AffiliationDebtsRequest(123, 2024);
    }

    public static AffiliationDebtsRequest withDefaultDebtsRequestBadYear() {
        return new AffiliationDebtsRequest(123, 99999);
    }

    public static ServiceAffiliationRequest withDefaultServiceAffiliationRequest() {
        return new ServiceAffiliationRequest(
                "42",
                "24",
                "referencia",
                "2024",
                "0",
                false,
                List.of(withDefaultDependencyServiceAffiliation()),
                List.of(withDefaultDataServiceAffiliation())
        );
    }

    public static ServiceAffiliationRequest withDefaultServiceAffiliationRequestIsTemporalTrue() {
        return new ServiceAffiliationRequest(
                "42",
                "24",
                "referencia",
                "2024",
                "0",
                true,
                List.of(withDefaultDependencyServiceAffiliation()),
                List.of(withDefaultDataServiceAffiliation())
        );
    }

    public static DependencyServiceAffiliation withDefaultDependencyServiceAffiliation() {
        return new DependencyServiceAffiliation(
                "28",
                "73166120"
        );
    }

    public static DataServiceAffiliation withDefaultDataServiceAffiliation() {
        return new DataServiceAffiliation(
                "1",
                "S/N",
                "73166120",
                "desc",
                null,
                List.of(withDefaultDataRegisterServiceAffiliation())  // dataRegister
        );
    }

    public static DataRegisterServiceAffiliation withDefaultDataRegisterServiceAffiliation() {
        return new DataRegisterServiceAffiliation(
                "cuenta",
                "73166120",
                null,
                null,
                null,
                null,
                null
        );
    }

    public static PaymentDebtsRequest withDefaultPaymentDebtsRequest() {
        return new PaymentDebtsRequest(
                "068",
                BigDecimal.valueOf(100.00),
                1254678L,
                "324a029a-553f-4acb-abf4-4dcb25574463",
                "1254678",
                "Juan Perez",
                "Test Company",
                "77",
                "Pagos"
        );
    }

    public static ValidateAffiliateCriteriaRequest withDefaultValidateAffiliateCriteria() {
        return ValidateAffiliateCriteriaRequest.builder()
                .year(2023)
                .criteriaId("28")
                .criteriaIdAbbreviation("3")
                .searchFields(List.of(ValidateAffiliateCriteriaRequest.SearchField.builder()
                        .code("28")
                        .value("73166120")
                        .build()))
                .build();
    }

    public static ListServiceRequest withDefaultListServiceRequest() {
        return ListServiceRequest.builder()
                .filters(ListServiceRequest.Filter.builder()
                        .pagination(withDefaultPaginationRequest())
                        .order(withDefaultOrderRequest())
                        .search("entel")
                        .build())
                .build();
    }

    public static PaginationRequest withDefaultPaginationRequest() {
        return PaginationRequest.builder()
                .page(1)
                .pageSize(10)
                .build();
    }

    public static OrderRequest withDefaultOrderRequest() {
        return OrderRequest.builder()
                .field("serviceName")
                .desc(false)
                .build();
    }

    public static ListServiceRequest withDefaultNullListServiceRequest() {
        return ListServiceRequest.builder()
                .filters(ListServiceRequest.Filter.builder()
                        .pagination(null)
                        .order(null)
                        .search(null)
                        .build())
                .build();
    }

    public static ListServiceRequest withDefaultSearchListServiceRequest() {
        return ListServiceRequest.builder()
                .filters(ListServiceRequest.Filter.builder()
                        .pagination(null)
                        .order(withDefaultOrderRequestTrue())
                        .search("servicio")
                        .build())
                .build();
    }

    public static OrderRequest withDefaultOrderRequestTrue() {
        return OrderRequest.builder()
                .field("SERVICE_NAME")
                .desc(true)
                .build();
    }

    public static ListServiceRequest withDefaultOrderFalseListServiceRequest() {
        return ListServiceRequest.builder()
                .filters(ListServiceRequest.Filter.builder()
                        .pagination(null)
                        .order(withDefaultOrderRequestFalse())
                        .search("entel")
                        .build())
                .build();
    }

    public static OrderRequest withDefaultOrderRequestFalse() {
        return OrderRequest.builder()
                .field("SERVICE_NAME")
                .desc(false)
                .build();
    }

    public static ListServiceRequest withDefaultEmptyListServiceRequest() {
        return ListServiceRequest.builder()
                .filters(ListServiceRequest.Filter.builder()
                        .pagination(withDefaultPaginationRequestPage2())
                        .order(null)
                        .search("")
                        .build())
                .build();
    }

    public static PaginationRequest withDefaultPaginationRequestPage2() {
        return PaginationRequest.builder()
                .page(2)
                .pageSize(10)
                .build();
    }
}
