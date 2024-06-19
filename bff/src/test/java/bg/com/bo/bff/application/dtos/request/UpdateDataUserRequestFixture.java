package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.application.dtos.response.user.UpdatePersonalDetail;

import java.util.List;

public class UpdateDataUserRequestFixture {
    public static UpdateDataUserRequest withDefault(){

        UpdatePersonalDetail.EconomicalActivity economicalActivity = UpdatePersonalDetail.EconomicalActivity.builder()
                .economicActivity(65191)
                .company("eMPRESA DE PAPE")
                .position("GERENTE")
                .incomeLevel(1)
                .type("D")
                .build();

        UpdatePersonalDetail.MaritalStatus maritalStatus=  UpdatePersonalDetail.MaritalStatus.builder()
                .status("S")
                .husbandLastName("LOPEZ")
                .spouseName("JUAN")
                .hasHusbandLastName("N")
                .build();

        UpdatePersonalDetail.PersonalData personalData = UpdatePersonalDetail.PersonalData.builder()
                .cellPhoneNumber("121212")
                .email("reynaldo@gmail.com")
                .zone(7)
                .dictrict("La PAz")
                .address("Calle moxos")
                .department("La Paz")
                .departmentCode(1)
                .cityCode(1)
                .streetCode(1)
                .neighborhood("calle moxos")
                .floor("1")
                .GPS("-17.743265190031195 ; -63.19027550518513")
                .phones("79404040")
                .homeReference("Frente al hospital")
                .doorNumber("1")
                .bankEmployee("1")
                .neighborhoodCode(0)
                .build();

        List<UpdatePersonalDetail.Reference> references = List.of(
                UpdatePersonalDetail.Reference.builder()
                        .name("María")
                        .telephone("987654321")
                        .referenceType("P")
                        .ordinal(1)
                        .personType("M")
                        .relationship(2)
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






