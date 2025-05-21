package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.ListDevicesMWResponse;

import java.io.IOException;

public interface IOnboardingManagerProvider {

    ListDevicesMWResponse listDeviceOnboardingManager(int personId) throws IOException;

    GenericResponse disableDevice(int personId, String deviceId) throws IOException;

}
