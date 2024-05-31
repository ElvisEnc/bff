package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.response.BiometricsResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;

import java.io.IOException;
import java.util.Map;

public interface IUserService {
    GenericResponse changePassword(String personId, String personRoleId, ChangePasswordRequest changePasswordRequest, Map<String, String> parameters) throws IOException;

    BiometricsResponse getBiometrics(Integer personId, Map<String, String> parameter) throws IOException;

    UpdateBiometricsResponse updateBiometrics(Integer personId, UpdateBiometricsRequest request, Map<String, String> parameter) throws IOException;

    ContactResponse getContactInfo();

    PersonalResponse getPersonalInformation(String personId, Map<String, String> parameter) throws IOException;

    EconomicActivityResponse getEconomicActivity(Integer personId, Map<String, String> parameter);
}
