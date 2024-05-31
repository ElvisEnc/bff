package bg.com.bo.bff.providers.mappings.personal.information;

import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicalActivity;
import bg.com.bo.bff.application.dtos.response.user.PersonalDetail;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.ProviderNetResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class PersonalInformationMapper implements IPersonalInformationMapper {

    @Override
    public ApiPersonalInformationNetRequest mapperRequest(String personId) {
        return ApiPersonalInformationNetRequest.builder()
                .intNumeroPersona(personId)
                .pStrNroSesion("10052024151318af42ae6fe0fd0f72")
                .build();
    }

    @Override
    public PersonalResponse convertResponse(PersonalInformationNetResponse response) {
        return PersonalResponse.builder()
                .maritalStatus(PersonalDetail.MaritalStatus.builder()
                        .status(response.getDataContent().getClientDataList().get(0).getMaritalStatus())
                        .spouseLastName(response.getDataContent().getClientDataList().get(0).getSpouseLastName())
                        .spouseName(response.getDataContent().getClientDataList().get(0).getSpouseName())
                        .build())
                .economicalActivity(PersonalDetail.EconomicalActivity.builder()
                        .type(response.getDataContent().getEconomicActivities().get(0).getIncomeSource())
                        .company(response.getDataContent().getEconomicActivities().get(0).getCompany())
                        .position(response.getDataContent().getEconomicActivities().get(0).getPosition())
                        .build())
                .personalData(PersonalDetail.PersonalData.builder()
                        .completeName(response.getDataContent().getClientDataList().get(0).getFullName())
                        .telephoneNumber(response.getDataContent().getClientDataList().get(0).getPhones())
                        .email(response.getDataContent().getClientDataList().get(0).getEmail())
                        .section(response.getDataContent().getClientDataList().get(0).getCityCode())
                        .dictrict(response.getDataContent().getClientDataList().get(0).getCity())
                        .zone(response.getDataContent().getClientDataList().get(0).getZone())
                        .address(response.getDataContent().getClientDataList().get(0).getNeighborhood())
                        .department(response.getDataContent().getClientDataList().get(0).getDepartment())
                        .floor(response.getDataContent().getClientDataList().get(0).getFloor())
                        .GPS(response.getDataContent().getClientDataList().get(0).getCoordinates())
                        .build())
                .references(response.getDataContent().getReferences().stream()
                        .map(reference -> PersonalDetail.Reference.builder()
                                .name(reference.getName())
                                .telephone(reference.getPhone())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public EconomicActivityResponse convertEconomicActivity(ProviderNetResponse netResponse) {
        List<Object> netData = netResponse.getData();
        if (netData == null || netData.isEmpty()) {
            return EconomicActivityResponse.builder()
                    .economicActivity(Collections.emptyList())
                    .incomeLevel(Collections.emptyList())
                    .incomeSource(Collections.emptyList())
                    .jobTitle(Collections.emptyList())
                    .build();
        }
        Map<String, List<Map<String, String>>> firstMap = (Map<String, List<Map<String, String>>>) netResponse.getData().get(0);
        Map<String, List<Map<String, String>>> secondMap = (Map<String, List<Map<String, String>>>) netResponse.getData().get(1);
        Map<String, List<Map<String, String>>> thirdMap = (Map<String, List<Map<String, String>>>) netResponse.getData().get(2);
        Map<String, List<Map<String, String>>> fourthMap = (Map<String, List<Map<String, String>>>) netResponse.getData().get(3);

        List<EconomicalActivity> economicalActivityList = firstMap.get("actividadEconomica").stream()
                .map(data -> EconomicalActivity.builder()
                        .id(data.get("id"))
                        .description(data.get("name"))
                        .build())
                .toList();
        List<EconomicalActivity> economicLevelList = secondMap.get("nivelIngreso").stream()
                .map(data -> new EconomicalActivity(data.get("OPCIONINTERNA"), data.get("DESCRIPCION")))
                .toList();
        List<EconomicalActivity> economicSourceList = thirdMap.get("fuenteIngreso").stream()
                .map(data -> new EconomicalActivity(data.get("OPCIONINTERNA"), data.get("DESCRIPCION")))
                .toList();
        List<EconomicalActivity> economicJobList = fourthMap.get("cargo").stream()
                .map(data -> new EconomicalActivity(data.get("CODIGO"), data.get("CARGO")))
                .toList();

        return EconomicActivityResponse.builder()
                .economicActivity(economicalActivityList)
                .incomeLevel(economicLevelList)
                .incomeSource(economicSourceList)
                .jobTitle(economicJobList)
                .build();
    }
}
