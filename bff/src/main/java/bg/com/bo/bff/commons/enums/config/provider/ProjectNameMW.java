package bg.com.bo.bff.commons.enums.config.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectNameMW {
    ACCOUNT_STATEMENT_MANAGER("/account-statement-manager", "ACCOUNT STATEMENT MANAGER", "Secret"),
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
    LOANS_TRANSACTION("/loans-transaction-manager", "LOANS TRANSACTION MANAGER", "Secret"),
    CREDIT_CARD_MANAGER("/creditcard-manager", "CREDIT CARD MANAGER", "Secret"),
    CREDIT_CARD_TRANSACTION_MANAGER("/creditcard-transaction-manager", "CREDIT CARD TRANSACTION MANAGER", "Secret"),
    POINT_ATTENTION_MANAGER("/point-attention-manager", "POINT ATTENTION MANAGER", "Secret"),
    REMITTANCE_MANAGER("/remittance-manager", "REMITTANCE MANAGER", "Secret"),
    SOFT_TOKEN_MANAGER("/softtoken-manager", "SOFT TOKEN MANAGER", "Secret"),
    CERTIFICATIONS_MANAGER("/certifications-manager", "CERTIFICATIONS MANAGER", "Secret")

    ;

    private final String name;
    private final String description;
    private final String headerKey;
}
