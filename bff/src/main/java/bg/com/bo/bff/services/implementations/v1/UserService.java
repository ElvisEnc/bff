package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.response.BiometricsResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.apiface.DistrictsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.MaritalStatusResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.validators.generics.*;
import bg.com.bo.bff.providers.dtos.request.personal.information.DistrictsNetRequest;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;
import bg.com.bo.bff.providers.dtos.response.login.BiometricStatusMWResponse;
import bg.com.bo.bff.providers.interfaces.IApiFaceNetProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.IPersonalInformationNetProvider;
import bg.com.bo.bff.providers.mappings.apiface.IApiFaceMapper;
import bg.com.bo.bff.providers.mappings.personal.information.IPersonalInformationMapper;
import bg.com.bo.bff.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService implements IUserService {

    private final ILoginMiddlewareProvider loginMiddlewareProvider;
    private final IPersonalInformationNetProvider personalInformationNetProvider;
    private final IPersonalInformationMapper iPersonalInformationMapper;
    private final IApiFaceNetProvider apiFaceNetProvider;
    private final IApiFaceMapper iApiFaceMapper;

    @Autowired
    public UserService(ILoginMiddlewareProvider provider, IPersonalInformationNetProvider providerPersonal, IPersonalInformationMapper iPersonalInformationMapper, IApiFaceNetProvider apiFaceNetProvider,
                       IApiFaceMapper iApiFaceMapper) {
        this.loginMiddlewareProvider = provider;
        this.personalInformationNetProvider = providerPersonal;
        this.iPersonalInformationMapper = iPersonalInformationMapper;
        this.apiFaceNetProvider = apiFaceNetProvider;
        this.iApiFaceMapper = iApiFaceMapper;
    }

    @Override
    public GenericResponse changePassword(String personId, String personRoleId, ChangePasswordRequest changePasswordRequest, Map<String, String> parameters) throws IOException {
        IValidator<String> validator = new NotEqualsValidator<>(changePasswordRequest.getOldPassword(), new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.SAME_PASSWORD));
        validator.setNext(new MinLengthValidator(Constants.PASSWORD_MIN_LENGTH, new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)))
                .setNext(new MaxLengthValidator(Constants.PASSWORD_MAX_LENGTH, new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)))
                .setNext(new ContainsDigitValidator(new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)))
                .setNext(new ContainsLetterValidator(new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)));
        validator.validate(changePasswordRequest.getNewPassword());

        return loginMiddlewareProvider.changePassword(personId, personRoleId, changePasswordRequest, parameters);
    }

    @Override
    public BiometricsResponse getBiometrics(Integer personId, Map<String, String> parameter) throws IOException {
        BiometricStatusMWResponse mwResponse = loginMiddlewareProvider.getBiometricsMW(personId, parameter);
        return BiometricsResponse.builder()
                .status(Objects.equals(mwResponse.getData().getStatusBiometric(), "S"))
                .authenticationType(mwResponse.getData().getAuthenticationType())
                .build();
    }

    @Override
    public UpdateBiometricsResponse updateBiometrics(Integer personId, UpdateBiometricsRequest request, Map<String, String> parameter) throws IOException {
        if (request.getStatus() == null) {
            throw new GenericException("status no puede estar vacío", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
        }
        return loginMiddlewareProvider.updateBiometricsMW(personId, request, parameter);
    }

    @Override
    public ContactResponse getContactInfo() {
        return loginMiddlewareProvider.getContactInfo();
    }

    @Override
    public PersonalResponse getPersonalInformation(String personId, Map<String, String> parameter) throws IOException {
        return personalInformationNetProvider.getPersonalInformation(personId, parameter);
    }

    @Override
    public EconomicActivityResponse getEconomicActivity(Integer personId, Map<String, String> parameter) {
        return personalInformationNetProvider.getEconomicalActivity(personId);
    }

    @Override
    public DepartmentsResponse getDepartments(Map<String, String> parameter) throws IOException {
        DepartmentsNetResponse netResponse = apiFaceNetProvider.getDepartments(parameter);
        return iApiFaceMapper.mapToDepartmentsResponse(netResponse);
    }

    @Override
    public DistrictsResponse getDistricts(String departmentId, Map<String, String> parameter) throws IOException {
        DistrictsNetRequest requestData = iPersonalInformationMapper.mapToDistrictRequest(String.valueOf(departmentId));
        DistrictsNetResponse netResponse = personalInformationNetProvider.getDistricts(requestData, parameter);

        return iPersonalInformationMapper.mapToDistrictsResponse(netResponse);
    }

    @Override
    public MaritalStatusResponse getMaritalStatus(Map<String, String> parameter) {
        return personalInformationNetProvider.getMaritalStatuses();
    }
}
