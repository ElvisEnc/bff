package bg.com.bo.bff.commons.enums.config.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectNameMW {
    ACCOUNT_STATEMENT_MANAGER("/account-statement-manager", "ACCOUNT STATEMENT MANAGER", ProjectNameMW.HEADER_KEY),
    LOGIN_MANAGER("/login-manager", "LOGIN MANAGER", ProjectNameMW.HEADER_KEY),
    OWN_ACCOUNT_MANAGER("/own-accounts-manager", "OWN ACCOUNT MANAGER", ProjectNameMW.HEADER_KEY),
    TRANSFER_MANAGER("/transaction-manager", "TRANSACTION MANAGER", ProjectNameMW.HEADER_KEY),
    ACH_TRANSFER_MANAGER("/ach-transaction-manager", "ACH TRANSACTION MANAGER", ProjectNameMW.HEADER_KEY),
    THIRD_ACCOUNTS("/third-accounts-manager", "THIRD ACCOUNTS MANAGER", ProjectNameMW.HEADER_KEY),
    GENERATE_QR_MANAGER("/generate-qr-manager", "GENERATE QR MANAGER", ProjectNameMW.HEADER_KEY),
    ACH_ACCOUNTS("/ach-account-manager", "ACH ACCOUNTS MANAGER", ProjectNameMW.HEADER_KEY),
    QR_TRANSACTION_MANAGER("/qr-transaction-manager", "QR TRANSACTION MANAGER", ProjectNameMW.HEADER_KEY),
    DPF_MANAGER("/dpf-manager", "DPF MANAGER", ProjectNameMW.HEADER_KEY),
    DEBIT_CARD_MANAGER("/debitcard-manager-db", "DEBIT CARD MANAGER", ProjectNameMW.HEADER_KEY),
    PAYMENT_SERVICES("/payment-services-manager", "PAYMENT SERVICES MANAGER", ProjectNameMW.HEADER_KEY),
    LOANS_SERVICES("/loans-manager", "LOANS MANAGER", ProjectNameMW.HEADER_KEY),
    LOANS_TRANSACTION("/loans-transaction-manager", "LOANS TRANSACTION MANAGER", ProjectNameMW.HEADER_KEY),
    CREDIT_CARD_MANAGER("/creditcard-manager", "CREDIT CARD MANAGER", ProjectNameMW.HEADER_KEY),
    CREDIT_CARD_TRANSACTION_MANAGER("/creditcard-transaction-manager", "CREDIT CARD TRANSACTION MANAGER", ProjectNameMW.HEADER_KEY),
    POINT_ATTENTION_MANAGER("/point-attention-manager", "POINT ATTENTION MANAGER", ProjectNameMW.HEADER_KEY),
    REMITTANCE_MANAGER("/remittance-manager", "REMITTANCE MANAGER", ProjectNameMW.HEADER_KEY),
    SOFT_TOKEN_MANAGER("/softtoken-manager", "SOFT TOKEN MANAGER", ProjectNameMW.HEADER_KEY),
    CERTIFICATIONS_MANAGER("/certifications-manager", "CERTIFICATIONS MANAGER", ProjectNameMW.HEADER_KEY)

    ;

    private final String name;
    private final String description;
    private final String headerKey;
    private static final String HEADER_KEY = "Secret";
}
