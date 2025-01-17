package bg.com.bo.bff.providers.dtos.response.remittance.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListGeneralParametersMWResponse {

    private List<GPCurrenciesMW> currencies;
    private List<GPExtensionsMW> extensions;
    private List<GPTransactionReasonsMW> transactionReasons;
    private List<GPRelationshipsMW> relationships;
    private List<GPIncomeSourcesMW> incomeSources;
    private List<GPJobLevelsMW> jobLevels;
    private List<GPEconomicActivitiesMW> economicActivities;
}
