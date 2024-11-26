package bg.com.bo.bff.application.dtos.response.user;


import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UpdateDataUserResponse implements IGenericControllerResponse {
      SUCCESS("SUCCESS","Actualización correcta.");
      private final String code;
      private final String message;
}
