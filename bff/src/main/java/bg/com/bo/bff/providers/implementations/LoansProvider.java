package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;
import bg.com.bo.bff.providers.interfaces.ILoansProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.loans.LoansMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.loans.LoansMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class LoansProvider extends MiddlewareProvider<LoansMiddlewareError> implements ILoansProvider {
    @Value("${account.statement.total}")
    private String totalRecords;
    private final String baseUrl;

    public LoansProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.LOANS_SERVICES, LoansMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientLoansManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.LOANS_SERVICES.getName();
    }

    @Override
    public ListLoansMWResponse getListLoansByPerson(String personId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(LoansMiddlewareServices.GET_LIST_LOANS.getServiceURL(), personId);
        return get(url, HeadersMW.getDefaultHeaders(parameters), ListLoansMWResponse.class);
    }

    @Override
    public LoanPaymentsMWResponse getListLoanPayments(String loanId, String loamNumber, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(LoansMiddlewareServices.GET_LIST_LOAN_PAYMENTS.getServiceURL(), loanId, loamNumber);
        ByMwErrorResponseHandler<LoanPaymentsMWResponse> responseHandler = ByMwErrorResponseHandler.instance(LoansMiddlewareError.MDWPRE_001);
        return get(url, HeadersMW.getDefaultHeaders(parameters), LoanPaymentsMWResponse.class, responseHandler);
    }

    @Override
    public LoanInsurancePaymentsMWResponse getListLoanInsurancePayments(String loanId, String loamNumber, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(LoansMiddlewareServices.GET_LIST_LOAN_INSURANCE_PAYMENTS.getServiceURL(), loanId, loamNumber, totalRecords);
        ByMwErrorResponseHandler<LoanInsurancePaymentsMWResponse> responseHandler = ByMwErrorResponseHandler.instance(LoansMiddlewareError.MDWPRE_004);
        return get(url, HeadersMW.getDefaultHeaders(parameters), LoanInsurancePaymentsMWResponse.class, responseHandler);
    }

    @Override
    public LoanPlanMWResponse getLoanPlansPayments(String loanId, String personId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(LoansMiddlewareServices.GET_LIST_LOAN_PLANS.getServiceURL(), loanId, personId);
        return get(url, HeadersMW.getDefaultHeaders(parameters), LoanPlanMWResponse.class);
    }

    @Override
    public LoanDetailPaymentMWResponse getLoanDetailPayment(String loanId, String clientId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(LoansMiddlewareServices.GET_LOAN_DETAIL_PAYMENT.getServiceURL(), loanId, clientId);
        ApiDataResponse<LoanDetailPaymentMWResponse> mwResponse = get(url, HeadersMW.getDefaultHeaders(parameters), ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), LoanDetailPaymentMWResponse.class);
    }
}
