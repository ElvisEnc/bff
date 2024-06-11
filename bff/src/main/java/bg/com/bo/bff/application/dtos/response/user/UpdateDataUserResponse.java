package bg.com.bo.bff.application.dtos.response.user;


import bg.com.bo.bff.commons.enums.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UpdateDataUserResponse implements IGenericControllerResponse {
      SUCCESS("SUCCESS","Actulización correcta.");

      private final String code;
      private final String message;
}
