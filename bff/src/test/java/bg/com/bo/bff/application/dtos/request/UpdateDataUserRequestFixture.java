package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.application.dtos.response.user.UpdatePersonalDetail;

import java.util.List;

public class UpdateDataUserRequestFixture {
    public static UpdateDataUserRequest withDefault(){

        UpdatePersonalDetail.EconomicalActivity economicalActivity = UpdatePersonalDetail.EconomicalActivity.builder()
                .economicActivity("65191")
                .company("eMPRESA DE PAPE")
                .position("GERENTE")
                .incomeLevel("1000")
                .category("D")
                .build();

        UpdatePersonalDetail.MaritalStatus maritalStatus=  UpdatePersonalDetail.MaritalStatus.builder()
                .status("S")
                .spouseLastName("LOPEZ")
                .spouseName("JUAN")
                .usesSpouseLastName("N")
                .build();

        UpdatePersonalDetail.PersonalData personalData = UpdatePersonalDetail.PersonalData.builder()
                .telephoneNumber("121212")
                .email("reynaldo@gmail.com")
                .section("3")
                .zone("7")
                .dictrict("La PAz")
                .address("Calle moxos")
                .department("La Paz")
                .departmentCode("1")
                .floor("1")
                .gps("-17.743265190031195 ; -63.19027550518513")
                .cellphone("79404040")
                .homeReference("Frente al hospital")
                .doorNumber("1")
                .bankEmployee("1")
                .neighborhoodCode("0")
                .build();

        List<UpdatePersonalDetail.Reference> references = List.of(
                UpdatePersonalDetail.Reference.builder()
                        .name("María")
                        .phones("987654321")
                        .referenceType("P")
                        .ordinal("1")
                        .personType("M")
                        .relationship("2")
                        .build()
        );

        return UpdateDataUserRequest.builder()
                .maritalStatus(maritalStatus)
                .economicalActivity(economicalActivity)
                .personalData(personalData)
                .references(references)
                .build();
    }
}






