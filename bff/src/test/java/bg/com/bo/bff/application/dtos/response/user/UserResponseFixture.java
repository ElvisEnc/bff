package bg.com.bo.bff.application.dtos.response.user;

import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentDetail;
import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DistrictDetail;
import bg.com.bo.bff.application.dtos.response.user.apiface.DistrictsResponse;

import java.util.Arrays;
import java.util.Collections;

public class UserResponseFixture {
    public static UpdateBiometricsResponse withDefaultUpdateBiometricsResponse() {
        return UpdateBiometricsResponse.builder()
                .personId("123")
                .build();
    }

    public static BiometricsResponse withDefaultBiometricsResponse() {
        return BiometricsResponse.builder()
                .status(true)
                .authenticationType("5")
                .build();
    }

    public static DepartmentDetail withDefaultDepartmentDetail() {
        return DepartmentDetail.builder()
                .id(123)
                .description("Description")
                .build();
    }

    public static DepartmentsResponse withDefaultDepartmentsResponse() {
        return DepartmentsResponse.builder()
                .data(Collections.singletonList(withDefaultDepartmentDetail()))
                .build();
    }

    public static EconomicActivityResponse withDefaultEconomicActivityResponse() {
        return EconomicActivityResponse.builder()
                .economicActivity(Collections.singletonList(withDefaultEconomicalActivity()))
                .incomeLevel(Collections.singletonList(withDefaultEconomicalActivity()))
                .incomeSource(Arrays.asList(anotherIncomeSourceEconomicalActivity(), incomeSourceEconomicalActivity())) // Set two objects here
                .jobTitle(Collections.singletonList(withDefaultEconomicalActivity()))
                .build();
    }

    public static EconomicActivityResponse withDefaultEconomicActivity() {
        return EconomicActivityResponse.builder()
                .economicActivity(Collections.singletonList(economicActivityEconomicalActivity()))
                .incomeLevel(Collections.singletonList(incomeLevelEconomicalActivity()))
                .incomeSource(Collections.singletonList(incomeSourceEconomicalActivity()))
                .jobTitle(Collections.singletonList(jobTitleEconomicalActivity()))
                .build();
    }

    public static EconomicalActivity withDefaultEconomicalActivity() {
        return EconomicalActivity.builder()
                .id("123")
                .description("Description")
                .build();
    }

    public static EconomicalActivity economicActivityEconomicalActivity() {
        return EconomicalActivity.builder()
                .id("1401")
                .description("Agricultura/Ganaderia")
                .build();
    }

    public static EconomicalActivity incomeLevelEconomicalActivity() {
        return EconomicalActivity.builder()
                .id("1")
                .description("MENOS DE $600")
                .build();
    }

    public static EconomicalActivity incomeSourceEconomicalActivity() {
        return EconomicalActivity.builder()
                .id("D")
                .description("Dependiente")
                .build();
    }

    public static EconomicalActivity anotherIncomeSourceEconomicalActivity() {
        return EconomicalActivity.builder()
                .id("123")
                .description("Dependiente")
                .build();
    }

    public static EconomicalActivity jobTitleEconomicalActivity() {
        return EconomicalActivity.builder()
                .id("AR1")
                .description("GERENTE")
                .build();
    }

    public static ContactResponse withDefaultContactResponse() {
        return new ContactResponse(
                ContactDetail.SocialNetworks.builder()
                        .facebook("facebook")
                        .linkedin("linkedin")
                        .youtube("youtube")
                        .build(),
                ContactDetail.AttentionLines.builder()
                        .helpNumber("helpNumber")
                        .creditNumber("creditNumber")
                        .build(),
                ContactDetail.Contact.builder()
                        .whatsapp("whatsapp")
                        .countryNumberCode("countryNumberCode")
                        .build()
        );
    }

    public static PersonalResponse withDefaultPersonalResponse() {
        return PersonalResponse.builder()
                .maritalStatus(PersonalDetail.MaritalStatus.builder()
                        .status("status")
                        .spouseName("spouseName")
                        .husbandLastName("spouseLastName")
                        .hasHusbandLastName("N")
                        .build())
                .economicalActivity(PersonalDetail.EconomicalActivity.builder()
                        .type("type")
                        .company("company")
                        .position("position")
                        .incomeLevel("incomeLevel")
                        .economicActivity(1)
                        .build())
                .personalData(PersonalDetail.PersonalData.builder()
                        .completeName("completeName")
                        .cellPhoneNumber("telephoneNumber")
                        .lastUpdate("lastUpdate")
                        .gender("F")
                        .street("street")
                        .doorNumber("doorNumber")
                        .departmentCode(1)
                        .neighborhoodCode(2)
                        .streetCode(3)
                        .cityCode(4)
                        .homeReference("homeReference")
                        .apartmentDescription("apartmentDescription")
                        .phones("phones")
                        .bankEmployee("bankEmployee")
                        .email("email")
                        .section(1)
                        .zone(2)
                        .address("address")
                        .dictrict("dictrict")
                        .department("department")
                        .floor(2)
                        .gps("GPS")
                        .build())
                .references(Collections.singletonList(PersonalDetail.Reference.builder()
                        .name("name")
                        .telephone("telephone")
                        .personType("personType")
                        .referenceType("referenceType")
                        .relationship(3)
                        .ordinal(1)
                        .build()))
                .build();
    }

    public static MaritalStatus withDefaultMaritalStatus() {
        return MaritalStatus.builder()
                .id("1")
                .key("C")
                .description("casado")
                .build();
    }

    public static MaritalStatusResponse withDefaultMaritalStatusResponse() {
        return MaritalStatusResponse.builder()
                .data(Arrays.asList(withDefaultMaritalStatus(), withDefaultMaritalStatus()))
                .build();
    }

    public static DistrictsResponse withDefaultDistrictsResponse() {
        return DistrictsResponse.builder()
                .data(Collections.singletonList(withDefaultDistrictDetail()))
                .build();
    }

    public static DistrictDetail withDefaultDistrictDetail() {
        return DistrictDetail.builder()
                .id(123)
                .description("Description")
                .build();
    }
}