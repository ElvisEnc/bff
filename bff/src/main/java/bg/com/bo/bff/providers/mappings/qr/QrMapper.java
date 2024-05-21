package bg.com.bo.bff.providers.mappings.qr;

import bg.com.bo.bff.application.dtos.request.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.QRCodeRegenerateRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.requests.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.requests.QRCodeRegenerateMWRequest;
import bg.com.bo.bff.providers.dtos.responses.qr.QrGeneratedPaidMW;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class QrMapper implements IQrMapper{
    private static final String SCHEME_NAME =  "PersonId";
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

    @Override
    public QRCodeGenerateMWRequest convert(QRCodeGenerateRequest request) {
        QRCodeGenerateMWRequest requestMW = QRCodeGenerateMWRequest.builder()
                .companyName(request.getCompanyName())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .reference(request.getReference())
                .typeDueDate(request.getTypeDueDate())
                .codService(request.getCodService())
                .singleUse(request.getSingleUse())
                .accountNumber(request.getAccountNumber())
                .field(request.getField())
                .serialNumber(request.getSerialNumber())
                .operationType(request.getOperationType())
                .personId(request.getPersonId())
                .userRegister(request.getUserRegister())
                .typeReturn(request.getTypeReturn())
                .formatImage(request.getFormatImage())
                .build();
        requestMW.setOwnerAccount(SCHEME_NAME,request.getPersonId());
        return requestMW;
    }

    @Override
    public QRCodeRegenerateMWRequest convert(QRCodeRegenerateRequest request) {
      return new QRCodeRegenerateMWRequest(String
              .format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
                      request.getIdQr(),
                      request.getCompanyName(),
                      request.getIdentificationNumber(),
                      request.getEif(),
                      request.getAccountNumber(),
                      request.getAmount(),
                      request.getReference(),
                      request.getExpirationDate(),
                      request.getSingleUse(),
                      request.getCodService(),
                      request.getField())
      );
    }
}
