package bg.com.bo.bff.providers.dtos.request;

import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdateDataPerson;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdatePersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;

import java.io.IOException;
import java.util.List;

public class UpdatePersonalInformationNetRequestFixture {
    public static UpdatePersonalInformationNetRequest withDefault() throws IOException {
        String result = "{\"CodigoError\":\"COD000\",\"Datos\":{\"cur_datosClienteGanasueldo\":[{\"NUMEROPERSONAFISICA\":1487723,\"FECHAULTACTUALIZACION\":null,\"NOMBRECOMPLETO\":\"PERSONA NATURAL\",\"ESTADOCIVIL\":\"S\",\"SEXO\":\"M\",\"CALLE\":\"LAS LOMAS\",\"NUMEROPUERTA\":\"SN\",\"PISO\":0,\"CIUDAD\":\"SANTA CRUZ\",\"DEPARTAMENTO\":\"SANTA CRUZ\",\"COD_DEPARTAMENTO\":7,\"BARRIOZONA\":\"LAS LOMAS\",\"EMAIL\":\"rb@bg.com\",\"CELULAR\":\"77653520\",\"COD_BARRIO\":0,\"COD_CALLE\":0,\"COD_CIUDAD\":1,\"APELLIDOESPOSO\":\" \",\"USA_APELLIDOESPOSO\":\"N\",\"REFERENCIADOMICILIO\":\" \",\"OFICINA\":\" \",\"ZONA\":1,\"NOMBRE_CONYUGUE\":\" \",\"APARTAMENTO\":\" \",\"TELEFONOS\":\" \",\"FECHAACTUALIZACION\":\"  \",\"NIVEL_INGRESOS\":null,\"ACTIVIDAD_ECONOMICA\":93099,\"EMPLEADO_BANCO\":\"1\",\"COORDENADAS\":\" \"}],\"cur_referenciasPersonaFisica\":[{\"NOMBRE\":\"INGRID CAROLA SAAVEDRA MEDIN\",\"TELEFONOS\":\"78529352\",\"RELACION\":1,\"TIPOREFERENCIA\":\"P\",\"TIPO_PERSONA\":\"F\",\"ORDINAL\":0}],\"cur_actividadEconomica\":[{\"EMPRESA\":\" \",\"CARGO\":\" \",\"FUENTE_INGRESO\":\"P\"}]},\"Mensaje\":\"Ejecución Correcta\"}";
        PersonalInformationNetResponse oldData = Util.stringToObject(result, PersonalInformationNetResponse.class);
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
                .maritalStatus("1")
                .floor("D")
                .streetCode("BGA")
                .incomeLevel("AR4")
                .incomeSource("1")
                .company("Juan Perez")
                .position("1234567")
                .relationship(13202)
                .referenceName("REynaldo ")
                .referencePhone("121")
                .ordinal(20)

                .build();
        return UpdatePersonalInformationNetRequest.builder()
                .personId("12234")
                .sessionNumber("10052024151318af42ae6fe0fd0f72")
                .channel("2")
                .newData(data)
                .oldData(oldData.getDataContent().getClientDataList())
                .build();
    }
}

