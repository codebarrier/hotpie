package com.codebarrier.hotpie.rest.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnknownProfileException extends Exception {
    public UnknownProfileException(String profileName) {
        super("Unable to find a loaded profile " + profileName);
    }
}
