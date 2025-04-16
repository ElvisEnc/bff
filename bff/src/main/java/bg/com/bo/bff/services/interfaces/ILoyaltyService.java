package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.*;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ILoyaltyService {

    LoyaltySystemCodeResponse getSystemCode(String personId) throws IOException;

    LoyaltyPointResponse getSumPoint(String personId, String codeSystem) throws IOException;

    GenericResponse registerSubscription(String personId, String accountId, RegisterSubscriptionRequest request) throws IOException;

    LoyaltyRedeemVoucherResponse registerRedeemVoucher(String personId, String codeSystem, RegisterRedeemVoucherRequest request) throws IOException;

    LoyaltyLevelResponse getLevel(String personId) throws IOException;

    LoyaltyPointResponse getPointsPeriod(String personId, String accountId) throws IOException;

    LoyaltyInitialPointsResponse getInitialPointsVAMOS(String personId) throws IOException;

    GenericResponse verifySubscription(String personId) throws IOException;

    List<LoyaltyStatementResponse> statementPoints(String personId, String codeSystem, LoyaltyStatementRequest request) throws IOException;

    LoyaltyGeneralInfoResponse getGeneralInformation(String personId) throws IOException;

    LoyaltyImageResponse getImageInformation(String imageId) throws IOException;

    List<LoyaltyImageResponse> getImagesInformation(LoyaltyImageRequest request) throws IOException;


}
