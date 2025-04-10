package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevel;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ILoyaltyService {

    LoyaltySystemCodeResponse getSystemCode(String personId) throws IOException;

    LoyaltySumPointResponse getSumPoint(String personId, String codeSystem) throws IOException;

    GenericResponse registerSubscription(String personId, String accountId, RegisterSubscriptionRequest request) throws IOException;

    LoyaltyRedeemVoucherResponse registerRedeemVoucher(String personId, String codeSystem, RegisterRedeemVoucherRequest request) throws IOException;

    LoyaltyLevel getLevel(String personId) throws IOException;


}
