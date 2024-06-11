package bg.com.bo.bff.providers.dtos.response.personal.update;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonalUpdateNetResponse {
    @JsonProperty("CodigoError")
    private String errorCode;

    @JsonProperty("Datos")
    private DataContent dataContent;

    @JsonProperty("Mensaje")
    private String message;

    public String getUpdateResponse() {
        return dataContent.getUpdateResponse();
    }


    public PersonalUpdateNetResponse(String errorCode, String updateResponse, String message) {
        DataContent dataContent = new DataContent();
        dataContent.setUpdateResponse(updateResponse);
        this.errorCode = errorCode;
        this.dataContent = dataContent;
        this.message = message;
    }

    class DataContent {
        @JsonProperty("p_actualizacionCorrecta")
        private String updateResponse;

        public DataContent() {
        }

        public String getUpdateResponse() {
            return updateResponse;
        }

        public void setUpdateResponse(String updateResponse) {
            this.updateResponse = updateResponse;
        }
    }



}


