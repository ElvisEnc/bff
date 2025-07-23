package bg.com.bo.bff.application.config;

import bg.com.bo.bff.application.config.fixtures.JwtAuthenticationFilterFixture;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.services.auth.IAuthMapper;
import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtAccess;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    JwtAuthenticationFilter filter;
    IJwtProvider service;
    IAuthMapper mapper;
    HttpServletRequest request;
    HttpServletResponse response;
    FilterChain chain;
    SecurityContext securityContext;
    Authentication authentication;
    String path;
    String method;
    String authorization;
    MockedStatic<SecurityContextHolder> mockedSecurityContextHolder;

    @BeforeEach
    void setUp() {
        mapper = mock(IAuthMapper.class);
        service = mock(IJwtProvider.class);
        filter = spy(new JwtAuthenticationFilter(service, mapper));

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);

        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);

        path = "/api/v1/login";
        method = "GET";
        authorization = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRSmc5dDcxWDI0VVk2QXhJR09USTdHdVp0YS1aa2s3N0dNRlpIbm56U2NZIn0.eyJleHAiOjE3Mjk3MDk1NDUsImlhdCI6MTcyOTcwODk0NSwianRpIjoiNmYwNjg2ODUtYWZkNC00N2Y4LWJiYmMtODExMzMwZWRiZWE3IiwiaXNzIjoiaHR0cDovL2tleWNsb2FrLmdhbmFtb3ZpbC5kZXYuYmcuY29tLmJvL3JlYWxtcy9rb25nIiwiYXVkIjoiZ2FuYW1vdmlsLWJmZi1xYSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImdhbmFtb3ZpbC1iZmYtcWEiLCJzaWQiOiI2N2RlZWVhMi1iYWQyLTQxNTMtYjNmNS0zMzA0ZmQ5YjdkMzUiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIi8qIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwiZGVmYXVsdC1yb2xlcy1rb25nIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZ2FuYW1vdmlsLWJmZi1xYSI6eyJyb2xlcyI6WyJ1bWFfcHJvdGVjdGlvbiJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwiYXV0aG9yaXphdGlvbiI6eyJwZXJtaXNzaW9ucyI6W3siY2xhaW1zIjp7InJvbGVzIjpbIkxPR0dFRF9VU0VSIl0sInBlcnNvbklkIjpbIjE2MTEzNzUwIl0sInNpZCI6WyI3ZDQ0YWI4MS1jNGVlLTQ4OTEtOTg1ZS05NjliZjliYWZjN2MiXX0sInJzaWQiOiI4YWRhNTI5YS1mMTNmLTQwN2QtYjNiOS03NTdlNWViYzJjODkiLCJyc25hbWUiOiJEZWZhdWx0IFJlc291cmNlIn1dfSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiY2xpZW50SG9zdCI6IjEwLjI0NC4xOTkuMTY2IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtZ2FuYW1vdmlsLWJmZi1xYSIsImNsaWVudEFkZHJlc3MiOiIxMC4yNDQuMTk5LjE2NiIsImNsaWVudF9pZCI6ImdhbmFtb3ZpbC1iZmYtcWEifQ.MicxgGk0GpnIQxywiKYhx9p1XiCQ3xqsSTrdmIELEISPtAJdUpsHrIZuU5NQxqR0qldRJ-CxlI-wbVjYqSwIySRwpgBsA_MiyKq-a5WlnnhsVP0_yxnusMtr_GMDM5zMPJVi127rdnLWuC7JiM0cXyZpB1fNzMVDckimynryqhrpEWVCnjKAmLIpmuJfCYk0AoMq4gANT9ktyQP_L1SneZwX2kIQphcbUsIW9ebvWFCUu4_JRQMORZin2OhLFGM7wSQ5_5-6HVlicoSrzikXYOwcjJZpu3WmrJEjL5A2_nVFIXfGZr-lHgT1wpPYnfnoPo3m8cFhpThYGWyjMlOKyw";
    }

    @AfterEach
    void close() {
        if (mockedSecurityContextHolder != null && !mockedSecurityContextHolder.isClosed())
            mockedSecurityContextHolder.close();
    }

    @Test
    void givenValidAuthenticationWhenDoFilterThenEndSuccessfullyAuthorized() throws ServletException, IOException {
        // Arrange
        mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class);
        mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

        JwtAccess access = JwtAuthenticationFilterFixture.withDefaultJwtAccess();
        UserData userData = JwtAuthenticationFilterFixture.withDefaultUserData();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(access.getPayload().getRoles().get(0)));
        UsernamePasswordAuthenticationToken authenticationExpected = new UsernamePasswordAuthenticationToken(userData, "", authorities);
        authenticationExpected.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        when(request.getServletPath()).thenReturn(path);
        when(request.getMethod()).thenReturn(method);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authorization);
        when(service.parseJwtAccess(any())).thenReturn(access);
        when(mapper.convert(any())).thenReturn(userData);

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(securityContext, times(1)).setAuthentication(authenticationExpected);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    void givenNoTokenWhenDoFilterThenEndSuccessfullyWithoutAuthorize(String authorization) throws ServletException, IOException {
        // Arrange
        mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class);
        mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

        when(request.getServletPath()).thenReturn(path);
        when(request.getMethod()).thenReturn(method);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authorization);

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(securityContext, times(0)).setAuthentication(any());
    }

    @Test
    void givenInvalidTokenWhenDoFilterThenThrowGenericException() {
        // Arrange
        mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class);
        mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
        GenericException expected = new GenericException(DefaultMiddlewareError.INVALID_ACCESS_JWT);

        when(request.getServletPath()).thenReturn(path);
        when(request.getMethod()).thenReturn(method);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authorization);
        when(service.parseJwtAccess(any())).thenThrow(expected);

        // Act
        Exception result = assertThrows(Exception.class, () -> filter.doFilter(request, response, chain));

        // Assert
        assertThat(result).usingRecursiveComparison().ignoringFields("source").isEqualTo(expected);
    }

    @Test
    void givenExceptionWhenDoFilterThenThrowInternalError() {
        // Arrange
        mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class);
        mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
        GenericException expected = new GenericException(DefaultMiddlewareError.AUTHENTICATION_FILTER_FAILURE);

        when(request.getServletPath()).thenReturn(path);
        when(request.getMethod()).thenReturn(method);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authorization);
        when(service.parseJwtAccess(any())).thenThrow(new JwtException("Error"));

        // Act
        Exception result = assertThrows(Exception.class, () -> filter.doFilter(request, response, chain));

        // Assert
        assertThat(result).usingRecursiveComparison().ignoringFields("source").isEqualTo(expected);
    }

    @Test
    void givenNotExecuteUrlWhenDoFilterThenNoExecuteFilter() throws ServletException, IOException {
        // Arrange
        String path = "/api/v1/login/1/refresh";
        String method = "POST";

        when(request.getServletPath()).thenReturn(path);
        when(request.getMethod()).thenReturn(method);

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(filter, times(0)).doFilterInternal(any(), any(), any());
    }
}
