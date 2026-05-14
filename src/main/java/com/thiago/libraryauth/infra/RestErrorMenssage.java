package com.thiago.libraryauth.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMenssage {
    private HttpStatus Status;
    private String message;
}
