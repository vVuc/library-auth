package com.thiago.libraryauth.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RestFieldError {
    private String field;
    private String error;
}
