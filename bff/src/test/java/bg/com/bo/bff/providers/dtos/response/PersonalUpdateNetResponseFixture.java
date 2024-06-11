package bg.com.bo.bff.providers.dtos.response;

import bg.com.bo.bff.providers.dtos.response.personal.update.PersonalUpdateNetResponse;

public class PersonalUpdateNetResponseFixture {
    public static PersonalUpdateNetResponse withDefault(){
        return new PersonalUpdateNetResponse("COD000","1", "Ejecucion Correcta");

    }
}
