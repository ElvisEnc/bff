package bg.com.bo.bff.providers.dtos.request.payment.services.mw;

import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.DataRegisterServiceAffiliationMW;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.DataServiceAffiliationMW;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.DependencyServiceAffiliationMW;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.ServiceAffiliationMWRequest;

import java.math.BigDecimal;
import java.util.List;

public class PaymentServicesMWRequestFixture {
    public static DeleteAffiliateServiceMWRequest withDefaultDeleteAffiliateServiceMWRequest(){
        return DeleteAffiliateServiceMWRequest.builder()
                .personId("123456")
                .affiliationCode("1234")
                .build();
    }

    public static DebtsConsultationMWRequest withDefaultDebtsRequestMW() {
        return new DebtsConsultationMWRequest(123, 123, 2024, 123);
    }

    public static PaymentDebtsMWRequest withDefaultPaymentDebtsMWRequest() {
        return PaymentDebtsMWRequest.builder()
                .ownerAccount(PaymentDebtsMWRequest.PaymentOwnerAccount.builder()
                        .schemeName("personId")
                        .personId("123")
                        .companyId("123")
                        .build())
                .instructedAmount(PaymentDebtsMWRequest.PaymentAmount.builder()
                        .currency("068")
                        .amount(BigDecimal.valueOf(100.00))
                        .build())
                .debtorAccount(PaymentDebtsMWRequest.PaymentDebtor.builder()
                        .schemeName("AccountId")
                        .identification("1254678")
                        .build())
                .supplementaryData(PaymentDebtsMWRequest.PaymentSupplementary.builder()
                        .idGeneratedForDebt("324a029a-553f-4acb-abf4-4dcb25574463")
                        .invoiceNITCI("1254678")
                        .invoiceName("Juan Perez")
                        .company("Test Company")
                        .affiliationCode("123")
                        .serviceCode("77")
                        .description("Pagos")
                        .build())
                .risk(PaymentDebtsMWRequest.PaymentRisk.builder()
                        .paymentContextCode("PaymentService")
                        .build())
                .build();
    }

    public static ServiceAffiliationMWRequest withDefaultServiceAffiliationMWRequest() {
        return ServiceAffiliationMWRequest.builder()
                .serviceCode("42")
                .criteriaSearchId("24")
                .referenceName("referencia")
                .year("2024")
                .personId("123")
                .accountNumber("0")
                .isTemporal("N")
                .searchFields(List.of(withDefaultDependencyServiceAffiliationMW()))
                .dataAffiliation(List.of(withDefaultDataServiceAffiliationMW()))
                .build();
    }

    public static DependencyServiceAffiliationMW withDefaultDependencyServiceAffiliationMW() {
        return new DependencyServiceAffiliationMW(
                "28",
                "73166120"
        );
    }

    public static DataServiceAffiliationMW withDefaultDataServiceAffiliationMW() {
        return new DataServiceAffiliationMW(
                "1",
                "S/N",
                "73166120",
                "desc",
                null,
                List.of(withDefaultDataRegisterServiceAffiliationMW())
        );
    }

    public static DataRegisterServiceAffiliationMW withDefaultDataRegisterServiceAffiliationMW() {
        return new DataRegisterServiceAffiliationMW(
                "cuenta",
                "73166120",
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ValidateAffiliateCriteriaMWRequest withDefaultValidateAffiliateCriteria(){
        return ValidateAffiliateCriteriaMWRequest.builder()
                .serviceCode(85)
                .year(2023)
                .searchCriteriaId("28")
                .searchCriteriaIdAbbreviation("3")
                .personId(123)
                .searchFields(
                        List.of(
                                ValidateAffiliateCriteriaMWRequest.SearchField.builder()
                                        .code("28")
                                        .value("73166120")
                                        .build()
                        )
                )
                .build();
    }
}
