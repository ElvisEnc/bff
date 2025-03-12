package bg.com.bo.bff.mappings.providers.certifications.implementation;

import bg.com.bo.bff.application.dtos.response.certifications.CertificationAccountsResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationHistoryResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationPrefExchRateResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;
import bg.com.bo.bff.mappings.providers.certifications.interfaces.ICertificationsMapper;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesAccountsListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsHistoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsPreferredExchMWResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class CertificationsMapper implements ICertificationsMapper {

    @Override
    public List<CertificationTypesResponse> convertCertsTypesResponse(CertificatesTypeListMWResponse mdwResponse) {
        if (mdwResponse == null || mdwResponse.getData() == null)
            return Collections.emptyList();

        return mdwResponse.getData().stream()
                .map(
                        mdw -> CertificationTypesResponse.builder()
                                .requestCode(mdw.getRequestCode())
                                .typeCode(mdw.getTypeCode())
                                .description(mdw.getDescription())
                                .build()
                ).toList();
    }

    @Override
    public List<CertificationAccountsResponse> convertCertsAccountsResponse(CertificatesAccountsListMWResponse mdwResponse) {
        if (mdwResponse == null || mdwResponse.getData() == null)
            return Collections.emptyList();

        return mdwResponse.getData().stream()
                .map(
                        mdw -> CertificationAccountsResponse.builder()
                                .accountId(mdw.getAccountId())
                                .accountNumber(mdw.getAccountNumber())
                                .clientName(mdw.getClientName())
                                .product(mdw.getProduct())
                                .descState(mdw.getDescState())
                                .descHandle(mdw.getDescHandle())
                                .descCurrency(mdw.getDescCurrency())
                                .currentBalance(mdw.getCurrentBalance())
                                .availableBalance(mdw.getAvailableBalance())
                                .pledgedDFunds(mdw.getPledgedDFunds())
                                .depnoconf(mdw.getDepnoconf())
                                .currencyCode(mdw.getCurrencyCode())
                                .currency(mdw.getCurrency())
                                .descAccountType(mdw.getDescAccountType())
                                .accountTypeCode(mdw.getAccountTypeCode())
                                .build()
                ).toList();
    }

    @Override
    public List<CertificationPrefExchRateResponse> convertCertsPreferredExChaRate(CertificationsPreferredExchMWResponse mdwResponse) {
        if (mdwResponse == null || mdwResponse.getData() == null)
            return Collections.emptyList();

        return mdwResponse.getData().stream()
                .map(mdw -> CertificationPrefExchRateResponse.builder()
                        .buyRateUFV(mdw.getBuyRateUFV())
                        .buyRateEUR(mdw.getBuyRateEUR())
                        .sellRateEur(mdw.getSellRateEur())
                        .buyRate(mdw.getBuyRate())
                        .sellRate(mdw.getSellRate())
                        .client(mdw.getClient())
                        .build()
                ).toList();
    }

    @Override
    public List<CertificationHistoryResponse> convertCertificationHistory(CertificationsHistoryMWResponse mdwResponse) {
        if (mdwResponse == null || mdwResponse.getData() == null)
            return Collections.emptyList();

        return mdwResponse.getData().stream()
                .map(mdw -> CertificationHistoryResponse.builder()
                        .requestNumber(mdw.getRequestNumber())
                        .day(mdw.getDay())
                        .month(mdw.getMonth())
                        .year(mdw.getYear())
                        .title(mdw.getTitle())
                        .docNumber(mdw.getDocNumber())
                        .state(mdw.getState())
                        .mail(mdw.getMail())
                        .build()
                ).toList();
    }

}
