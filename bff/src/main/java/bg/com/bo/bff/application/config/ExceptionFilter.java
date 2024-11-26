package bg.com.bo.bff.application.config;

import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        try {
            chain.doFilter(request, response);
        } catch (GenericException e) {
            sendResponse(response, e);
        } catch (Exception e) {
            logger.error(e);
            sendResponse(response, DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendResponse(HttpServletResponse response, IMiddlewareError error) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.instance(error);
        response.setStatus(error.getHttpCode().value());
        response.setContentType("application/json");
        response.getWriter().write(Util.objectToString(errorResponse));
    }

    private static void sendResponse(HttpServletResponse response, GenericException e) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.instance(e);
        response.setStatus(e.getStatus().value());
        response.setContentType("application/json");
        response.getWriter().write(Util.objectToString(errorResponse));
    }
}