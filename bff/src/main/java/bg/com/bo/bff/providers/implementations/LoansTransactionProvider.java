package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.loans.mw.LoanPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;
import bg.com.bo.bff.providers.interfaces.ILoansTransactionProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.loans.LoansTransactionMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.loans.LoansTransactionMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoansTransactionProvider extends MiddlewareProvider<LoansTransactionMiddlewareError> implements ILoansTransactionProvider {
    private final String baseUrl;
    private final HttpServletRequest httpServletRequest;

    public LoansTransactionProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest) {
        super(ProjectNameMW.LOANS_TRANSACTION, LoansTransactionMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientLoansTransactionManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.LOANS_TRANSACTION.getName();
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public LoanPaymentMWResponse payLoanInstallment(LoanPaymentMWRequest request) throws IOException {
        String url = baseUrl + LoansTransactionMiddlewareServices.PAY_LOAN_INSTALLMENT.getServiceURL();
        ApiDataResponse<LoanPaymentMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), LoanPaymentMWResponse.class);
    }
}
