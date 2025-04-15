package bg.com.bo.bff.mappings.providers.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGeneralInfoResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyInitialPointsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevelResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStatementResponse;
import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterRedeemVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyStatementPointRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoyaltyMapper implements ILoyaltyMapper{
    String campaign = "1";

    @Override
    public LoyaltySystemCodeResponse convertResponse(LoyaltySystemCodeServerResponse response) {
        return LoyaltySystemCodeResponse.builder()
                .codeSystem(response.getCodeSystem())
                .build();
    }

    @Override
    public LoyaltyPointResponse convertResponse(LoyaltyPointServerResponse response) {
        return LoyaltyPointResponse.builder()
                .points(response.getPoint())
                .build();
    }

    @Override
    public LoyaltyRedeemVoucherResponse convertResponse(LoyaltyRegisterRedeemVoucherResponse response) {
        return LoyaltyRedeemVoucherResponse.builder()
                .identifier(response.getIdentifier())
                .codeVoucher(response.getCodeVoucher())
                .idCampaign(response.getIdCampaign())
                .holderName(response.getHolderName())
                .documentHolder(response.getDocumentHolder())
                .beneficiaryName(response.getBeneficiaryName())
                .beneficiaryDocument(response.getBeneficiaryDocument())
                .idPerson(response.getIdPerson())
                .codePerson(response.getCodePerson())
                .dateCreation(response.getDateCreation())
                .dateVoucher(response.getDateVoucher())
                .valueVoucher(response.getValueVoucher())
                .expirationDate(response.getExpirationDate())
                .idBenefit(response.getIdBenefit())
                .name(response.getName())
                .description(response.getDescription())
                .banner(response.getBanner())
                .note(response.getNote())
                .status(response.getStatus())
                .typeValue(response.getTypeValue())
                .trade(mapTrade(response.getTrade()))
                .redeemVoucher(mapRedeemVoucher(response.getRedeemVoucher()))
                .build();
    }

    @Override
    public LoyaltyLevelResponse convertResponse(LoyaltyGetLevelResponse response) {
        return LoyaltyLevelResponse.builder()
                .identifier(response.getIdentifier())
                .name(response.getName())
                .minimumScore(response.getMinimumScore())
                .maximumScore(response.getMaximumScore())
                .idCampaign(response.getIdCampaign())
                .idLevelNext(response.getIdLevelNext())
                .build();
    }

    @Override
    public LoyaltyInitialPointsResponse convertResponse(LoyaltyGetInitialPointsVamosResponse response) {
        return LoyaltyInitialPointsResponse.builder()
                .points(response.getPointsVamos())
                .datePoints(response.getDatePointsVamos())
                .pointsLoyalty(response.getPointsLoyalty())
                .build();
    }

    @Override
    public LoyaltyStatementResponse convertResponse(List<LoyaltyStatementPointsResponse> response) {
        if (response == null || response.isEmpty()) {
            return LoyaltyStatementResponse.builder()
                    .movements(Collections.emptyList())
                    .build();
        }
        List<LoyaltyStatementResponse.LoyaltyDetailStatementPoints> movements = response.stream()
                .map(item -> LoyaltyStatementResponse.LoyaltyDetailStatementPoints.builder()
                        .action(item.getAction())
                        .comment(item.getComment())
                        .dateCreation(item.getDateCreation())
                        .origin(item.getOrigin())
                        .campaignScore(item.getCampaignScore())
                        .build())
                .toList();
        return LoyaltyStatementResponse.builder()
                .movements(movements)
                .build();
    }

    @Override
    public LoyaltyGeneralInfoResponse convertResponse(LoyaltyGeneralInformationResponse response) {
        return LoyaltyGeneralInfoResponse.builder()
                .codeSystem(response.getCodeSystem())
                .level(convertLevel(response.getLevel()))
                .levels(convertLevels(response.getLevels()))
                .points(response.getPoints())
                .pointsPeriod(response.getPointsPeriod())
                .build();
    }

    private LoyaltyLevelResponse convertLevel(LoyaltyGetLevelResponse level) {
        if (level == null) {
            return null;
        }
        return convertResponse(level);
    }

    private List<LoyaltyLevelResponse> convertLevels(List<LoyaltyGetLevelResponse> levels) {
        return levels.stream()
                .map(this::convertLevel)
                .toList();
    }

    @Override
    public Map<String, String> mapperRequestService(String personId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("sesion", "0704202517010198a33421103b7311");
        headers.put("idpersona", personId);
        return headers;
    }

    @Override
    public LoyaltyRegisterSubscriptionRequest mapperRequest(String personId, String accountId, RegisterSubscriptionRequest request) {
        return LoyaltyRegisterSubscriptionRequest.builder()
                .signatureDigital(true)
                .idPerson(personId)
                .codeCampaign("1")
                .jtsOidAccountNumber(accountId)
                .email(request.getEmail())
                .subscriptionOrigin(CanalMW.GANAMOVIL.getDescription())
                .build();
    }

    @Override
    public LoyaltyRegisterRedeemVoucherRequest mapperRequest(String personId, String codeSystem, RegisterRedeemVoucherRequest request) {
        return LoyaltyRegisterRedeemVoucherRequest.builder()
                .codigoCampana(campaign)
                .idCodigoSistema(codeSystem)
                .idBeneficio(request.getIdBenefit())
                .tipoBeneficio(request.getTypeBenefit())
                .informacion(
                        request.getInformation() != null
                                ? LoyaltyRegisterRedeemVoucherRequest.Information.builder()
                                .nombreBeneficiario(request.getInformation().getBeneficiaryName())
                                .documentoBeneficiario(request.getInformation().getBeneficiaryDocument())
                                .parentesco(request.getInformation().getBeneficiaryRelationship())
                                .build()
                                : null
                )
                .build();
    }

    @Override
    public LoyaltyStatementPointRequest mapperRequest(String personId, String codeSystem, LoyaltyStatementRequest request) {
        return LoyaltyStatementPointRequest.builder()
                .codigoPersona(codeSystem)
                .codigoCampana("1")
                .fechaInicial(request.getStartDate())
                .fechaFinal(request.getEndDate())
                .build();
    }

    private LoyaltyRedeemVoucherResponse.LoyaltyTrade mapTrade(LoyaltyRegisterRedeemVoucherResponse.LoyaltyTrade sourceTrade) {
        return LoyaltyRedeemVoucherResponse.LoyaltyTrade.builder()
                .identifierTrade(sourceTrade.getIdentifierTrade())
                .nameTrade(sourceTrade.getNameTrade())
                .descriptionTrade(sourceTrade.getDescriptionTrade())
                .logoTrade(sourceTrade.getLogoTrade())
                .category(mapCategory(sourceTrade.getCategory()))
                .build();
    }

    private LoyaltyRedeemVoucherResponse.LoyaltyCategory mapCategory(LoyaltyRegisterRedeemVoucherResponse.LoyaltyCategory sourceCategory) {
        return LoyaltyRedeemVoucherResponse.LoyaltyCategory.builder()
                .identifierCategory(sourceCategory.getIdentifierCategory())
                .nameCategory(sourceCategory.getNameCategory())
                .iconCategory(sourceCategory.getIconCategory())
                .build();
    }

    private LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher mapRedeemVoucher(LoyaltyRegisterRedeemVoucherResponse.LoyaltyRedeemVoucher sourceVoucher) {
        return LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher.builder()
                .valueRedeemVoucher(sourceVoucher.getValueRedeemVoucher())
                .typeValueRedeemVoucher(sourceVoucher.getTypeValueRedeemVoucher())
                .build();
    }
}

