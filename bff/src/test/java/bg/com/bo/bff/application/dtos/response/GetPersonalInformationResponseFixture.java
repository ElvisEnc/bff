package bg.com.bo.bff.application.dtos.response;

import bg.com.bo.bff.application.dtos.response.user.PersonalDetail;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;

import java.util.Collections;

public class GetPersonalInformationResponseFixture {
    public static PersonalResponse withDefault(){
        return PersonalResponse.builder()
                .maritalStatus(PersonalDetail.MaritalStatus.builder()
                        .status("status")
                        .spouseName("spouseName")
                        .spouseLastName("spouseLastName")
                        .build())
                .economicalActivity(PersonalDetail.EconomicalActivity.builder()
                        .type("type")
                        .company("company")
                        .position("position")
                        .build())
                .personalData(PersonalDetail.PersonalData.builder()
                        .completeName("completeName")
                        .telephoneNumber("telephoneNumber")
                        .email("email")
                        .section(1)
                        .zone(2)
                        .address("address")
                        .dictrict("dictrict")
                        .department("department")
                        .floor(2)
                        .GPS("GPS")
                        .build())
                .references(Collections.singletonList(PersonalDetail.Reference.builder()
                        .name("name")
                        .telephone("telephone")
                        .build()))
                .build();
    }
}
