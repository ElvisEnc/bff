package bg.com.bo.bff.application.dtos.response;

import bg.com.bo.bff.application.dtos.response.user.PersonalDetail;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;

import java.util.Collections;

public class GetPersonalInformationResponseFixture {
    public static PersonalResponse withDefault() {
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
}
