package com.weimer.listingTest;

import com.weimer.listingTest.resources.ApiError;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 6240018970665693773L;

    private final ApiError apiError;

    public ApiException(final ApiError apiError) {
        super(new ParameterizedMessage(apiError.getDescription(), (Object[]) apiError.getDescriptionValues()).getFormattedMessage());
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
