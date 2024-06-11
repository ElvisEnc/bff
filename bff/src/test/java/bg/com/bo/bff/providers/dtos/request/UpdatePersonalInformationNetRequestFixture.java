package bg.com.bo.bff.providers.dtos.request;

import bg.com.bo.bff.providers.dtos.request.personal.information.UpdateDataPerson;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdatePersonalInformationNetRequest;

import java.util.List;

public class UpdatePersonalInformationNetRequestFixture {
    public static UpdatePersonalInformationNetRequest withDefault(){
        UpdateDataPerson data = UpdateDataPerson.builder()
                .coordinates("")
                .zone("5")
                .fullName("PERSONA NATURAL")
                .office("1.0")
                .departmentCode("1234567")
                .email("7")
                .husbandLastName("bett0@bett0.org")
                .neighborhood("CAÑADA TRES LAGUNAS AV TRILLO 5TO ANILLO")
                .updateDate(" ")
                .department("SANTA CRUZ")
                .city("SANTA CRUZ")
                .cityCode("NO DECLARA")
                .spouseName("1")
                .bankEmployee(" 0")
                .neighborhoodCode("321")
                .apartment("Donde la virgencita")
                .homeReference("71023338")
                .phones("77889944")
                .lastUpdateDate("N")
                .cellphone("PRUEBA PRUEBA PRUEBA PRUEBA")
                .usesSpouseLastName("SN")
                .street("65191")
                .doorNumber("S")
                .economicActivity("123")
                .gender("0")
                .maritalStatus("1")
                .floor("D")
                .streetCode("BGA")
                .incomeLevel("AR4")
                .incomeSource("1")
                .company("Juan Perez")
                .position("1234567")
                .relationship("13202")
                .referenceName("")
                .referencePhone("")
                .ordinal("")

                .build();
        return UpdatePersonalInformationNetRequest.builder()
                .personId("12234")
                .sessionNumber("10052024151318af42ae6fe0fd0f72")
                .channel("2")
                .newData(data)
                .oldData(List.of(data))
                .build();
    }
}

