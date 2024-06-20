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
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdateDataPerson;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdatePersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.ProviderNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictNetDetail;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.ClientData;
import bg.com.bo.bff.providers.dtos.response.personal.information.EconomyActivity;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    public PersonalResponse convertRequest(PersonalInformationNetResponse response) {
        PersonalResponse.PersonalResponseBuilder builder = PersonalResponse.builder();
        ClientData clientData = null;
        if (!response.getDataContent().getClientDataList().isEmpty()) {
            clientData = response.getDataContent().getClientDataList().get(0);
            builder.maritalStatus(PersonalDetail.MaritalStatus.builder()
                            .status(clientData.getMaritalStatus())
                            .husbandLastName(clientData.getHusbandLastName())
                            .spouseName(clientData.getSpouseName())
                            .hasHusbandLastName(clientData.getUsesSpouseLastName())
                            .build())
                    .personalData(PersonalDetail.PersonalData.builder()
                            .completeName(clientData.getFullName())
                            .cellPhoneNumber(String.valueOf(clientData.getPersonNumber()))
                            .lastUpdate(clientData.getLastUpdateDate())
                            .gender(clientData.getGender())
                            .street(clientData.getStreet())
                            .doorNumber(clientData.getDoorNumber())
                            .departmentCode(clientData.getDepartmentCode())
                            .neighborhoodCode(clientData.getNeighborhoodCode())
                            .streetCode(clientData.getStreetCode())
                            .cityCode(clientData.getCityCode())
                            .homeReference(clientData.getHomeReference())
                            .apartmentDescription(clientData.getApartment())
                            .phones(clientData.getPhones())
                            .bankEmployee(clientData.getBankEmployee())
                            .email(clientData.getEmail())
                            .section(clientData.getCityCode())
                            .dictrict(clientData.getCity())
                            .zone(clientData.getZone())
                            .address(clientData.getNeighborhood())
                            .department(clientData.getDepartment())
                            .floor(clientData.getFloor())
                            .gps(clientData.getCoordinates())
                            .neighborhood(clientData.getNeighborhood())
                            .build());
        }
        if (!response.getDataContent().getEconomicActivities().isEmpty()) {
            EconomyActivity economicActivity = response.getDataContent().getEconomicActivities().get(0);
            builder.economicalActivity(PersonalDetail.EconomicalActivity.builder()
                    .type(economicActivity.getIncomeSource())
                    .company(economicActivity.getCompany())
                    .position(economicActivity.getPosition())
                    .incomeLevel(clientData != null ? clientData.getIncomeLevel() : "")
                    .economicActivity(clientData != null ? clientData.getEconomicActivity() : 0)
                    .build());
        }

        if (!response.getDataContent().getReferences().isEmpty()) {
            List<PersonalDetail.Reference> references = response.getDataContent().getReferences().stream()
                    .map(reference -> PersonalDetail.Reference.builder()
                            .name(reference.getName())
                            .telephone(reference.getPhone())
                            .relationship(reference.getRelation())
                            .referenceType(reference.getReferenceType())
                            .personType(reference.getPersonType())
                            .ordinal(reference.getOrdinal())
                            .build())
                    .toList();
            builder.references(references);
        }
        return builder.build();
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
    public UpdatePersonalInformationNetRequest convertRequest(String personId, UpdateDataUserRequest request, PersonalInformationNetResponse personalInformation) {


        List<ClientData> oldData = personalInformation.getDataContent().getClientDataList();

        UpdateDataPerson newData = UpdateDataPerson.builder()
                .coordinates(request.getPersonalData().getGps())
                .zone(request.getPersonalData().getZone())
                .cityCode(request.getPersonalData().getCityCode())
                .departmentCode(request.getPersonalData().getDepartmentCode())
                .email(request.getPersonalData().getEmail())
                .usesSpouseLastName(request.getMaritalStatus().getHasHusbandLastName())
                .husbandLastName(request.getMaritalStatus().getHusbandLastName())
                .neighborhood(request.getPersonalData().getNeighborhood())
                .city(request.getPersonalData().getDictrict())
                .spouseName(request.getMaritalStatus().getSpouseName() != null ? request.getMaritalStatus().getSpouseName() : "") //TODO por confirmar con Kevin
                .bankEmployee(request.getPersonalData().getBankEmployee())
                .neighborhoodCode(request.getPersonalData().getNeighborhoodCode())
                .apartment(request.getPersonalData().getApartmentDescription())
                .homeReference(request.getPersonalData().getHomeReference())
                .phones(request.getPersonalData().getPhones())
                .mobile(request.getPersonalData().getCellPhoneNumber())
                .street(request.getPersonalData().getAddress())
                .doorNumber(request.getPersonalData().getDoorNumber())
                .economicActivity(request.getEconomicalActivity().getEconomicActivity())
                .maritalStatus(request.getMaritalStatus().getStatus())
                .floor(request.getPersonalData().getFloor())
                .company(request.getEconomicalActivity().getCompany())
                .position(request.getEconomicalActivity().getPosition())
                .incomeLevel(request.getEconomicalActivity().getIncomeLevel())
                .incomeSource(request.getEconomicalActivity().getType())
                .build();

        if (request.getReference() == null) {
            newData.setReferenceName(personalInformation.getDataContent().getReferences().get(0).getName());
            newData.setReferencePhone(personalInformation.getDataContent().getReferences().get(0).getPhone());
            newData.setOrdinal(personalInformation.getDataContent().getReferences().get(0).getOrdinal());
            newData.setRelationship(personalInformation.getDataContent().getReferences().get(0).getRelation());
        }else {
            newData.setReferenceName(request.getReference().getName());
            newData.setReferencePhone(request.getReference().getTelephone());
            newData.setOrdinal(request.getReference().getOrdinal());
            newData.setRelationship(request.getReference().getRelationship());
        }

        return UpdatePersonalInformationNetRequest.builder()
                .sessionNumber(NUMBER_SESSION)
                .channel(CanalMW.GANAMOVIL.getCanal())
                .personId(personId)
                .newData(newData)
                .oldData(oldData)
                .build();
    }
}
