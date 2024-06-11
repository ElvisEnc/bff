package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.application.dtos.response.user.UpdatePersonalDetail;

import java.util.List;

public class UpdateDataUserRequestFixture {
    public static UpdateDataUserRequest withDefault(){

        UpdatePersonalDetail.EconomicalActivity economicalActivity = UpdatePersonalDetail.EconomicalActivity.builder()
                .economicActivityCategory("65191")
                .company("eMPRESA DE PAPE")
                .position("GERENTE")
                .incomeLevel(" 0 1000")
                .category("INDEPENDITEE")
                .build();

        UpdatePersonalDetail.MaritalStatus maritalStatus=  UpdatePersonalDetail.MaritalStatus.builder()
                .status("S")
                .spouseLastName("LOPEZ")
                .spouseName("JUAN")
                .usesSpouseLastName("S")
                .build();

        UpdatePersonalDetail.PersonalData personalData = UpdatePersonalDetail.PersonalData.builder()
                .telephoneNumber("121212")
                .email("reynaldo@gmail.com")
                .section("3")
                .zone("Las Lomas")
                .dictrict("Distrito Central")
                .address("Calle moxos")
                .department("La Paz")
                .departmentCode("1")
                .city("La PAz")
                .cityCode("1")
                .floor("1")
                .GPS("-17.743265190031195 ; -63.19027550518513")
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






