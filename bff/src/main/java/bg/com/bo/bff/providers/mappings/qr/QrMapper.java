package bg.com.bo.bff.providers.mappings.qr;

import bg.com.bo.bff.application.dtos.request.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.QRCodeRegenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrDecryptRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrDecryptResponse;
import bg.com.bo.bff.application.dtos.request.qr.QRPaymentRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.request.QRCodeRegenerateMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.response.qr.QrGeneratedPaidMW;
import bg.com.bo.bff.providers.dtos.requests.qr.CreditorAccount;
import bg.com.bo.bff.providers.dtos.requests.qr.DebtorAccount;
import bg.com.bo.bff.providers.dtos.requests.qr.InstructedAmount;
import bg.com.bo.bff.providers.dtos.requests.qr.OwnerAccount;
import bg.com.bo.bff.providers.dtos.requests.qr.QRPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.requests.qr.RiskMW;
import bg.com.bo.bff.providers.dtos.requests.qr.SupplementaryMWData;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class QrMapper implements IQrMapper {
    private static final String SCHEME_NAME = "PersonId";
    private static final String SCHEME_NAME_ACCOUNT = "AccountId";
    private static final String SCHEME_NAME_ACH_ACCOUNT_NUMBER = "AchAccountNumber";

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
        requestMW.setOwnerAccount(SCHEME_NAME, request.getPersonId());
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

    @Override
    public QRCodeRegenerateMWRequest convertDecrypt(QrDecryptRequest request) {
        return QRCodeRegenerateMWRequest.builder()
                .fields(request.getData())
                .build();
    }

    @Override
    public QrDecryptResponse convertDecryptResponse(QRCodeGenerateResponse response) {
        String data = response.getData().getResponse();
        String[] partsResponse = data.split("\\|");

        return QrDecryptResponse.builder()
                .qrId(partsResponse[0])
                .companyName(partsResponse[1])
                .identificationNumber(partsResponse[2])
                .eif(partsResponse[3])
                .accountNumber(partsResponse[4])
                .currency(partsResponse[5])
                .amount(Util.convertToDecimal(partsResponse[6], 2))
                .reference(partsResponse[7])
                .expirationDate(partsResponse[8])
                .singleUse(partsResponse[9])
                .serviceCode(Integer.parseInt(partsResponse[10]))
                .freeField(partsResponse[11])
                .serialNumber(partsResponse[12])
                .bank(partsResponse[13])
                .bankType(Util.getBankType(partsResponse[3], partsResponse[4]))
                .build();
    }

    @Override
    public QRPaymentMWRequest convert(QRPaymentRequest request, String personId, String accountId) {
        return QRPaymentMWRequest.
                builder()
                .ownerAccount(new OwnerAccount(SCHEME_NAME, personId))
                .instructedAmount(new InstructedAmount(request.getAmount().getCurrency(), request.getAmount().getAmount()))
                .debtorAccount(new DebtorAccount(SCHEME_NAME_ACCOUNT, accountId))
                .creditorAccount(new CreditorAccount(SCHEME_NAME_ACH_ACCOUNT_NUMBER,
                        request.getTargetAccount().getId(),
                        request.getTargetAccount().getSecondaryIdentification(),
                        request.getTargetAccount().getName()))
                .supplementaryData(SupplementaryMWData.builder()
                        .idQr(request.getSupplementaryData().getIdQr())
                        .nroDni(request.getSupplementaryData().getNroDni())
                        .description(request.getSupplementaryData().getDescription())
                        .dueDate(request.getSupplementaryData().getDueDate())
                        .typeUse(request.getSupplementaryData().getTypeUse())
                        .serviceCode(request.getSupplementaryData().getServiceCode())
                        .fields(request.getSupplementaryData().getFields())
                        .serialNumber(request.getSupplementaryData().getSerialNumber())
                        .build())
                .risk(new RiskMW(request.getRisk().getPaymentContextCode()))
                .build();
    }
}
