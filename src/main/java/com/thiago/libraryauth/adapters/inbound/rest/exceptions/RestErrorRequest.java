package com.thiago.libraryauth.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RestErrorRequest {
    private HttpStatus Status;
    private List<RestFieldError> fields;

}
