package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.application.dtos.response.user.UpdatePersonalDetail;

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
                .cellPhoneNumber("79409415")
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
                .gps("-17.743265190031195 ; -63.19027550518513")
                .phones("79404040")
                .homeReference("Frente al hospital")
                .doorNumber("1")
                .bankEmployee("1")
                .neighborhoodCode(0)
                .build();

       UpdatePersonalDetail.Reference reference =
                UpdatePersonalDetail.Reference.builder()
                        .name("María")
                        .telephone("987654321")
                        .ordinal(1)
                        .relationship(2)
                        .build();

        return UpdateDataUserRequest.builder()
                .maritalStatus(maritalStatus)
                .economicalActivity(economicalActivity)
                .personalData(personalData)
                .reference(reference)
                .build();
    }

    public static UpdateDataUserRequest validateMarriedPersonWhenSpouseNameIsBlank(){

        UpdatePersonalDetail.EconomicalActivity economicalActivity = UpdatePersonalDetail.EconomicalActivity.builder()
                .economicActivity(65191)
                .company("eMPRESA DE PAPE")
                .position("GERENTE")
                .incomeLevel(1)
                .type("D")
                .build();

        UpdatePersonalDetail.MaritalStatus maritalStatus=  UpdatePersonalDetail.MaritalStatus.builder()
                .status("C")
                .husbandLastName("LOPEZ")
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
                .gps("-17.743265190031195 ; -63.19027550518513")
                .phones("79404040")
                .homeReference("Frente al hospital")
                .doorNumber("1")
                .bankEmployee("1")
                .neighborhoodCode(0)
                .build();

        UpdatePersonalDetail.Reference reference =
                UpdatePersonalDetail.Reference.builder()
                        .name("María")
                        .telephone("987654321")
                        .ordinal(1)
                        .relationship(2)
                        .build();

        return UpdateDataUserRequest.builder()
                .maritalStatus(maritalStatus)
                .economicalActivity(economicalActivity)
                .personalData(personalData)
                .reference(reference)
                .build();
    }

    public static UpdateDataUserRequest validateMarriedPersonWhenSpouseNameIsNull(){

        UpdatePersonalDetail.EconomicalActivity economicalActivity = UpdatePersonalDetail.EconomicalActivity.builder()
                .economicActivity(65191)
                .company("eMPRESA DE PAPE")
                .position("GERENTE")
                .incomeLevel(1)
                .type("D")
                .build();

        UpdatePersonalDetail.MaritalStatus maritalStatus=  UpdatePersonalDetail.MaritalStatus.builder()
                .status("C")
                .husbandLastName("LOPEZ")
                .spouseName(null)
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
                .gps("-17.743265190031195 ; -63.19027550518513")
                .phones("79404040")
                .homeReference("Frente al hospital")
                .doorNumber("1")
                .bankEmployee("1")
                .neighborhoodCode(0)
                .build();

        UpdatePersonalDetail.Reference reference =
                UpdatePersonalDetail.Reference.builder()
                        .name("María")
                        .telephone("987654321")
                        .ordinal(1)
                        .relationship(2)
                        .build();

        return UpdateDataUserRequest.builder()
                .maritalStatus(maritalStatus)
                .economicalActivity(economicalActivity)
                .personalData(personalData)
                .reference(reference)
                .build();
    }

    public static UpdateDataUserRequest validateMarriedPersonWhenAsHusbandLastNameIsS(){

        UpdatePersonalDetail.EconomicalActivity economicalActivity = UpdatePersonalDetail.EconomicalActivity.builder()
                .economicActivity(65191)
                .company("eMPRESA DE PAPE")
                .position("GERENTE")
                .incomeLevel(1)
                .type("D")
                .build();

        UpdatePersonalDetail.MaritalStatus maritalStatus=  UpdatePersonalDetail.MaritalStatus.builder()
                .status("C")

                .spouseName("JUAN MORALES")
                .hasHusbandLastName("S")
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
                .gps("-17.743265190031195 ; -63.19027550518513")
                .phones("79404040")
                .homeReference("Frente al hospital")
                .doorNumber("1")
                .bankEmployee("1")
                .neighborhoodCode(0)
                .build();

        UpdatePersonalDetail.Reference reference =
                UpdatePersonalDetail.Reference.builder()
                        .name("María")
                        .telephone("987654321")
                        .ordinal(1)
                        .relationship(2)
                        .build();

        return UpdateDataUserRequest.builder()
                .maritalStatus(maritalStatus)
                .economicalActivity(economicalActivity)
                .personalData(personalData)
                .reference(reference)
                .build();
    }

}






