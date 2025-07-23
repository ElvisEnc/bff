package bg.com.bo.bff.providers.dtos.request.personal.information.affiliation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceAffiliationMWRequest {
    private String serviceCode;
    private String criteriaSearchId;
    private String referenceName;
    private String year;
    private String personId;
    private String accountNumber;
    private String isTemporal;
    private List<DependencyServiceAffiliationMW> searchFields;
    private List<DataServiceAffiliationMW> dataAffiliation;
}
