package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectNameMW {
    LOGIN_MANAGER("/login-manager", "LOGIN MANAGER", "Secret"),
    OWN_ACCOUNT_MANAGER("/own-accounts-manager", "OWN ACCOUNT MANAGER", "Secret"),
    TRANSFER_MANAGER("/transaction-manager", "TRANSACTION MANAGER", "Secret"),
    ACH_TRANSFER_MANAGER("/ach-transaction-manager", "ACH TRANSACTION MANAGER", "Secret"),
    THIRD_ACCOUNTS("/third-accounts-manager", "THIRD ACCOUNTS MANAGER", "Secret"),
    GENERATE_QR_MANAGER("/generate-qr-manager", "GENERATE QR MANAGER", "Secret"),
    ACH_ACCOUNTS("/ach-account-manager", "ACH ACCOUNTS MANAGER", "Secret"),
    QR_TRANSACTION_MANAGER("/qr-transaction-manager", "QR TRANSACTION MANAGER", "Secret"),
    DPF_MANAGER("/dpf-manager", "DPF MANAGER", "Secret"),
    DEBIT_CARD_MANAGER("/debitcard-manager-db", "DEBIT CARD MANAGER", "Secret"),
    PAYMENT_SERVICES("/payment-services-manager", "PAYMENT SERVICES MANAGER", "Secret"),
    LOANS_SERVICES("/loans-manager", "LOANS MANAGER", "Secret"),
    LOANS_TRANSACTION("/loans-transaction-manager", "LOANS TRANSACTION MANAGER", "Secret");

    private final String name;
    private final String description;
    private final String headerKey;
}
