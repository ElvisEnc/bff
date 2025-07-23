package bg.com.bo.bff.providers.dtos.request.payment.services.mw;

public record DebtsConsultationMWRequest(
        Integer serviceCode,
        Integer personId,
        Integer year,
        Integer affiliationCode,
        Integer concept
) {
}
