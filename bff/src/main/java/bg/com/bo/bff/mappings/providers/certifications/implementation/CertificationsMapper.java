package bg.com.bo.bff.mappings.providers.certifications.implementation;

import bg.com.bo.bff.application.dtos.response.certifications.*;
import bg.com.bo.bff.mappings.providers.certifications.interfaces.ICertificationsMapper;
import bg.com.bo.bff.providers.dtos.response.certifications.*;
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

    @Override
    public CertificationConfigResponse convertCertificationConfig(CertificationConfigMWResponse mdwResponse) {
        return CertificationConfigResponse.builder()
                .certPrice(mdwResponse.getData().getCertPrice())
                .message(mdwResponse.getData().getMessage())
                .build();
    }

    @Override
    public CertificationPriceResponse convertCertificationPrice(CertificationPriceMWResponse mdwResponse) {
        return CertificationPriceResponse.builder()
                .roleId(mdwResponse.getRoleId())
                .eventId(mdwResponse.getEventId())
                .amount(mdwResponse.getAmount())
                .currencyDes(mdwResponse.getCurrencyDes())
                .currencyCode(mdwResponse.getCurrencyCode())
                .rangeFrom(mdwResponse.getRangeFrom())
                .rangeTo(mdwResponse.getRangeTo())
                .rangeType(mdwResponse.getRangeType())
                .build();
    }

    @Override
    public SaveCertificationResponse convertSaveCertification(CertificationSaveRequestMWResponse mdwResponse) {
        if (!mdwResponse.getResponseCode().equals("COD000"))
            return SaveCertificationResponse.builder()
                    .message("Hubo un error al realizar la solicitud del certificado.")
                    .build();
        return SaveCertificationResponse.builder()
                .message("La solicitud fue registrada correctamente.")
                .build();
    }

}
