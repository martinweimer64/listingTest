package com.weimer.listingTest.resources;

import com.weimer.listingTest.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Deque;
import java.util.LinkedList;

public class UncaughtExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UncaughtExceptionMapper.class);
    @Context
    private ContainerRequestContext context;
    @Context
    private UriInfo uriInfo;
    @Inject
    Environment environment;
    @Context
    private ResourceInfo resourceInfo;
    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(final Throwable cause) {
        final ApiError error;

        if (cause instanceof ApiException) {
            error = ((ApiException) cause).getApiError();
            error.transactionId((String) context.getProperty(LoggingInterceptor.LOGGING_ID_PROPERTY));
            if (error.getDescription() == null || error.getDescription().isEmpty()) {
                error.description(getThrowableCause(cause));
            }
            error.path(uriInfo.getRequestUri().toString());
            LOGGER.error("ApiException, api error: {}", error);
            Response.Status statusCode = Response.Status.fromStatusCode(error.getCode());
            if (statusCode == null) {
                statusCode = Response.Status.INTERNAL_SERVER_ERROR;
            }
            return Response.status(statusCode).type(MediaType.APPLICATION_JSON_TYPE).entity(error.toString()).build();
        } else {

            int responseStatusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
            if (cause instanceof NotFoundException) {
                responseStatusCode = Response.Status.NOT_FOUND.getStatusCode();
            }

            // FIXME check the second params in this method
            error = new ApiError(responseStatusCode, responseStatusCode);
            error.transactionId((String) context.getProperty(LoggingInterceptor.LOGGING_ID_PROPERTY));
            error.path(uriInfo.getRequestUri().toString()).description(getThrowableCause(cause));
            LOGGER.error("Uncaught exception, api error: {}", error, cause);
            return Response.status(responseStatusCode).type(MediaType.APPLICATION_JSON_TYPE).entity(error.toString())
                    .build();
        }
    }

    public static String getThrowableCause(Throwable exception) {
        final Deque<Throwable> exceptionStack = new LinkedList<>();
        while (exception != null && !exceptionStack.contains(exception)) {
            exceptionStack.push(exception);
            exception = exception.getCause();
        }

        final StringBuilder errorDescription = new StringBuilder();
        while (!exceptionStack.isEmpty()) {
            final Throwable cause = exceptionStack.pop();
            if (errorDescription.length() > 0) {
                errorDescription.append(" --> ");
            }

            errorDescription.append(cause.getClass().getName() + ": " + cause.getLocalizedMessage());
        }

        return errorDescription.toString();
    }

}
