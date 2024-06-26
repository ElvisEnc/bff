package bg.com.bo.bff.integrations;

import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.integrations.embeddedRedis.EmbeddedRedisConfiguration;
import bg.com.bo.bff.integrations.embeddedRedis.TestCacheConfig;
import bg.com.bo.bff.integrations.embeddedRedis.TestHttpClientConfig;
import bg.com.bo.bff.providers.mappings.GenericsMapper;
import bg.com.bo.bff.mappings.implementations.keycloak.KeyCloakJsonMapper;
import bg.com.bo.bff.providers.mappings.keycloak.KeyCloakObjectMapper;
import bg.com.bo.bff.providers.mappings.keycloak.KeyCloakMapper;
import bg.com.bo.bff.models.jwt.JwtKey;
import bg.com.bo.bff.providers.dtos.response.keycloak.KeyCloakCertListResponse;
import bg.com.bo.bff.providers.dtos.response.keycloak.KeyCloakKeyResponse;
import bg.com.bo.bff.providers.implementations.JwtKeyCloakProvider;
import bg.com.bo.bff.providers.mappings.keycloak.KeyCloakObjectMapperImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * En esta clase se prueba la integración del uso de la cache en el servicio JwtKeyCloakService.
 * Se utilizan los siguientes componentes:
 * - EmbededdRedis: es un Redis embebido para utilizarlo de Cache.
 * - WireMock: levanta un api embebidopermitiendo mockear solo la respuesta, sin modificar el tratatamiento tanto al request como al response.
 * - TestCacheConfig: configuración de la cache para el test.
 * - TestHttpClientConfig: configuración para el http client para el test.
 * - KeyCloakObjectMapperImpl: integración de JwtKeyCloakService con KeyCloakObjectMapper.
 * - KeyCloakJsonMapper: integración de JwtKeyCloakService con KeyCloakJsonMapper.
 * - KeyCloakMapper: integración de JwtKeyCloakService con KeyCloakMapper.
 * - GenericsMapper: integración de JwtKeyCloakService con GenericsMapper.
 */
@Import({TestCacheConfig.class,
        TestHttpClientConfig.class,
        EmbeddedRedisConfiguration.class,
        KeyCloakObjectMapperImpl.class,
        KeyCloakJsonMapper.class,
        KeyCloakMapper.class,
        GenericsMapper.class,
        JwtKeyCloakProvider.class})
@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = {CacheAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableCaching
class JwtKeyCloakServiceWithCacheIntegrationTests {
    private KeyCloakObjectMapper keyCloakObjectMapper;

    @Autowired
    private JwtKeyCloakProvider jwtKeyCloakProvider;

    @Autowired
    private CacheManager cacheManager;

    private String url = "http://login.fake.api";

    private String urlCertsComplement = "/certs";

    @BeforeEach
    public void setup() {
        keyCloakObjectMapper = KeyCloakObjectMapper.INSTANCE;
    }

    /**
     *
     */
    @Test
    void givenRedisCachingWhenFindItThenItemReturnedFromCache() throws JsonProcessingException {
        //Arrange
        String keyCloakKeyId = "QzYxcSySXxmQVV8M_j1FUeFbloxuRFNtdPEEm6EG7xY";
        String keyCloakX5c = "\"MIIClzCCAX8CBgGMgl+kxzANBgkqhkiG9w0BAQsFADAPMQ0wCwYDVQQDDARrb25nMB4XDTIzMTIxOTEzNTczNVoXDTMzMTIxOTEzNTkxNVowDzENMAsGA1UEAwwEa29uZzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANTB4OEKXXJqK29y+1kfJI2z6w66ySd/CzhTNNBMVnwxsMOYoLj2a/VTMpi0TmwinyE6tnFbnTAAC1vcfb0R5hw4EYCnKD48PYPp0Rze+92qrpilQSXHViVJ9hTi/lyi0OV4LtyUU4kIa3hFxKwx1TR6UAi8x4QwuiXop0e66nHh0b40yDrbDT7lJSlBgGjG+4/2WjJVaZ0c6CRSALaDbY/WBlm3nD1+g95wnZtOOzA3lRz0gdks8LnUppNN/0QQNdnSWrZNkL7AvBt3FbkOu21NrjtCYYXy2wD7IS2Leb/grNtv8haf506zbJyNdeMD9/NfmYLpI/jsP6YTHRA1o80CAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAQ0gb4dhH+IP5OCBXCYtwcbHebxzeS15uE47dakqHb7Gp43S9bpz8Cr5NvkMDhsxYi9ARNKJ4LEeg5GmXzStLozHVJyLDizEUiCi7a6bwTNhYpFOdu+4E3an1ZnHGai5NyMsBmG2DGYDYWGKvykW2SFrSRbxRZIjRdawxQbxoB2Uxk9o1ITV8KRscwxOTTX6tvvlnP1hABOH2zYeOSrkNcvkyD4jyaE3VpHEjqJWv1TnV4lRaemdQ9QmPBSfIsA9oHclbduzkMi1uPABWCgk/VN+h/Ne9C0DuO61LZaaykpXzFcSo785CljElr77sVxUijRUY1Ajquk4T6rRgNL5PMg==\"";
        String algorithm = "RS256";
        String keyType = "RSA";
        String publicKeyUse = "sig";
        String rsaPublicExponent = "AQAB";
        String rsaPublicModulus = "1MHg4Qpdcmorb3L7WR8kjbPrDrrJJ38LOFM00ExWfDGww5iguPZr9VMymLRObCKfITq2cVudMAALW9x9vRHmHDgRgKcoPjw9g-nRHN773aqumKVBJcdWJUn2FOL-XKLQ5Xgu3JRTiQhreEXErDHVNHpQCLzHhDC6JeinR7rqceHRvjTIOtsNPuUlKUGAaMb7j_ZaMlVpnRzoJFIAtoNtj9YGWbecPX6D3nCdm047MDeVHPSB2SzwudSmk03_RBA12dJatk2QvsC8G3cVuQ67bU2uO0JhhfLbAPshLYt5v-Cs22_yFp_nTrNsnI114wP381-Zgukj-Ow_phMdEDWjzQ";
        String x5ts256 = "AWUCN3fOdP5LHJp_3VyYxSAsNa99ofAbcv5z__vvqj8";
        String x5t = "DfDVBEO9SJdhzSOwa4n4mIqNYT4";

        KeyCloakKeyResponse keyCloakKeyResponse = KeyCloakKeyResponse.builder()
                .id(keyCloakKeyId)
                .x5c(keyCloakX5c)
                .algorithm(algorithm)
                .keyType(keyType)
                .publicKeyUse(publicKeyUse)
                .rsaPublicExponent(rsaPublicExponent)
                .rsaPublicModulus(rsaPublicModulus)
                .x5ts256(x5ts256)
                .x5t(x5t)
                .build();

        KeyCloakCertListResponse keyCloakCertListResponse = KeyCloakCertListResponse.builder()
                .key(keyCloakKeyResponse)
                .build();

        HashMap<String, JwtKey> jwtMapped = new HashMap<>();
        jwtMapped.put(keyCloakKeyResponse.getId(), keyCloakObjectMapper.convert(keyCloakKeyResponse));

        // Propiedades cargadas por reflection dentro de JwtKeyCloakService.
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "urlBase", url);
        ReflectionTestUtils.setField(jwtKeyCloakProvider, "urlCertsComplement", urlCertsComplement);

        String jsonResponse = new ObjectMapper().writeValueAsString(keyCloakCertListResponse);

        stubFor(get(urlCertsComplement).willReturn(okJson(jsonResponse)));

        //Act
        Map<String, JwtKey> serviceResponse = jwtKeyCloakProvider.certs();
        Map<String, JwtKey> cacheResponse = jwtKeyCloakProvider.certs();

        //Assert
        assertEquals(jwtMapped, serviceResponse);
        assertEquals(jwtMapped, cacheResponse);
        assertEquals(jwtMapped, itemFromCache());
        verify(1, getRequestedFor(urlEqualTo(urlCertsComplement)));
    }

    private Object itemFromCache() {
        return cacheManager.getCache(CacheConstants.CERTS_CACHE_NAME).get("certs").get();
    }
}
