package bg.com.bo.bff.providers.dtos.request.personal.information.affiliation;

import java.util.List;

public record DataServiceAffiliationMW(
        String identify,
        String nameOwner,
        String code,
        String description,
        String additionalFact,
        List<DataRegisterServiceAffiliationMW> dataRegister
) {
}
