package com.weimer.listingTest;

import org.apache.logging.log4j.message.ParameterizedMessage;

public class ApiException extends RuntimeException {
	private static final long serialVersionUID = 6240018970665693773L;


    public ApiException(final String description, String values) {
        super(String.valueOf(new ParameterizedMessage(description, values)));
    }
}
