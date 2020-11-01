package com.weimer.listingTest.resources;

import com.weimer.listingTest.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Resource {

    private static final Logger LOGGER = LoggerFactory.getLogger(Resource.class);

    public static ApiException getApiException(final ErrorCodes errorCodes, final String... descriptionValues) {
        final ApiError apiError = new ApiError(errorCodes.getCode(), errorCodes.getStatusCode());
        apiError.description(errorCodes.getDescription());
        apiError.setDescriptionValues(descriptionValues);
        return new ApiException(apiError);
    }

    static void throwApiException(final ErrorCodes errorCodes) {
        throw getApiException(errorCodes);
    }

    public static void throwApiException(final ErrorCodes errorCodes, final String... descriptionValues) {
        final ApiError apiError = new ApiError(errorCodes.getCode(), errorCodes.getStatusCode());
        apiError.description(errorCodes.getDescription());
        apiError.setDescriptionValues(descriptionValues);
        throw getApiException(errorCodes, descriptionValues);
    }
}
