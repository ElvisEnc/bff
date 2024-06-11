package bg.com.bo.bff.providers.mappings.personal.information;

import bg.com.bo.bff.application.dtos.request.UpdateDataUserRequest;
import bg.com.bo.bff.application.dtos.response.apiface.DistrictDetail;
import bg.com.bo.bff.application.dtos.response.apiface.DistrictsResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicalActivity;
import bg.com.bo.bff.application.dtos.response.user.PersonalDetail;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.DistrictsNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.PersonalReferences;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdateDataPerson;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdatePersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.ProviderNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictNetDetail;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class PersonalInformationMapper implements IPersonalInformationMapper {

    private static String NUMBER_SESSION = "10052024151318af42ae6fe0fd0f72";

    @Override
    public ApiPersonalInformationNetRequest mapperRequest(String personId) {
        return ApiPersonalInformationNetRequest.builder()
                .intNumeroPersona(personId)
                .pStrNroSesion(NUMBER_SESSION)
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

    @Override
    public DistrictsNetRequest mapToDistrictRequest(String departmentId) {
        return DistrictsNetRequest.builder()
                .codDepartamento(departmentId)
                .build();
    }

    @Override
    public DistrictsResponse mapToDistrictsResponse(DistrictsNetResponse netResponse) {
        List<DistrictDetail> dataList = new ArrayList<>();
        if (netResponse != null && netResponse.getResult().getData() != null) {
            for (DistrictNetDetail districtNetData : netResponse.getResult().getData()) {
                DistrictDetail data = DistrictDetail.builder()
                        .id(Integer.valueOf(districtNetData.getCodeDistrict()))
                        .description(districtNetData.getDescription())
                        .build();
                dataList.add(data);
            }
        }
        return DistrictsResponse.builder()
                .data(dataList)
                .build();
    }

    @Override
    public UpdatePersonalInformationNetRequest convertResponse(String personId, UpdateDataUserRequest request, PersonalResponse personalInformation) {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("es", "ES"));

        UpdateDataPerson oldData = UpdateDataPerson.builder()
                .coordinates(personalInformation.getPersonalData().getGPS())
                .zone(personalInformation.getPersonalData().getAddress())
                .department(personalInformation.getPersonalData().getDepartment())
                .email(personalInformation.getPersonalData().getEmail())
                .neighborhood(personalInformation.getPersonalData().getDictrict())
                .city(personalInformation.getPersonalData().getDictrict())
                .spouseName(personalInformation.getMaritalStatus().getSpouseName())
                .husbandLastName(personalInformation.getMaritalStatus().getSpouseLastName())
                .economicActivity(personalInformation.getEconomicalActivity().getType())
                .company(personalInformation.getEconomicalActivity().getCompany())
                .position(personalInformation.getEconomicalActivity().getPosition())
                .build();


        UpdateDataPerson newData = UpdateDataPerson.builder()
                .coordinates(request.getPersonalData().getGPS())
                .zone(request.getPersonalData().getZone())
                .departmentCode(request.getPersonalData().getDepartment())
                .email(request.getPersonalData().getEmail())
                .neighborhood(request.getPersonalData().getZone())
                .updateDate(fechaActual.format(formatter))
                .department(request.getPersonalData().getDepartment())
                .departmentCode(request.getPersonalData().getDepartmentCode())
                .city(request.getPersonalData().getDepartment())
                .cityCode(request.getPersonalData().getCityCode())
                .spouseName(request.getMaritalStatus().getSpouseName())
                .usesSpouseLastName(request.getMaritalStatus().getUsesSpouseLastName())
                .husbandLastName(request.getMaritalStatus().getSpouseLastName())
                .maritalStatus(request.getMaritalStatus().getStatus())
                .economicActivity(request.getEconomicalActivity().getEconomicActivityCategory())
                .company(request.getEconomicalActivity().getCompany())
                .position(request.getEconomicalActivity().getPosition())
                .incomeLevel(request.getEconomicalActivity().getIncomeLevel())
                .build();


        List<PersonalReferences> personalReferences = request.getReferences().stream().map(x ->
                PersonalReferences.builder()
                        .phones(x.getPhones())
                        .referenceType(x.getReferenceType())
                        .ordinal(x.getOrdinal())
                        .persontype(x.getPersonType())
                        .name(x.getName())
                        .relationship(x.getRelationship())
                        .build()

        ).toList();

        return UpdatePersonalInformationNetRequest.builder()
                .sessionNumber(NUMBER_SESSION)
                .channel(CanalMW.GANAMOVIL.getCanal())
                .personId(personId)
                .newData(newData)
                .oldData(List.of(oldData))
                .personalReferences(personalReferences)
                .build();


    }
}
