package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.request.onboarding.manager.mw.DisableDeviceMWRequest;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.ListDevicesMWResponse;

import java.io.IOException;

public interface IOnboardingManagerProvider {

    ListDevicesMWResponse listDeviceOnboardingManager(int personId) throws IOException;

    GenericResponse disableDevice(DisableDeviceMWRequest request) throws IOException;

}
