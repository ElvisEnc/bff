package bg.com.bo.bff.providers.mappings.qr;

import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.responses.qr.QrGeneratedPaidMW;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class QrMapper implements IQrMapper{
    @Override
    public QrGeneratedPaid convert(QrGeneratedPaidMW mw) {
        return QrGeneratedPaid.builder()
                .customField(mw.getCustomField())
                .qrId(mw.getQrId())
                .masterQrId(mw.getMasterQrId())
                .identificationNumber(mw.getIdentificationNumber())
                .businessName(mw.getBussinesName())
                .bankCode(mw.getBankCode())
                .bank(mw.getDescription())
                .currencyCode(mw.getCurrency())
                .currencyDescription(mw.getCurrencyDescription())
                .expiryDate(Util.formatDate(mw.getExpiryDate()))
                .amount(mw.getMonto())
                .description(mw.getReference())
                .singleUse(Objects.equals(mw.getSingleUse(), "1"))
                .serviceCode(mw.getServiceCode())
                .serialNumber(mw.getSerialNumber())
                .operationType(mw.getOperationTypeId())
                .destinationAccountNumber(Long.valueOf(mw.getAccountNumber()))
                .status(mw.getStatus())
                .registrationDate(Util.formatDate(mw.getRegistrationDate()))
                .build();
    }
}
