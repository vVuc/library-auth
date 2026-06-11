package com.thiago.libraryauth.adapters.inbound.rest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage {
    private HttpStatus Status;
    private String message;
}
