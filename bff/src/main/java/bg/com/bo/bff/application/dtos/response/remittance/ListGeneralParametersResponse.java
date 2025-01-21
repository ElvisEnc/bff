package bg.com.bo.bff.application.dtos.response.remittance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListGeneralParametersResponse {

    private List<GPCurrencies> currencies;
    private List<GPExtensions> extensions;
    private List<GPTransactionReasons> transactionReasons;
    private List<GPRelationships> relationships;
    private List<GPIncomeSources> incomeSources;
    private List<GPJobLevels> jobLevels;
    private List<GPEconomicActivities> economicActivities;
}
