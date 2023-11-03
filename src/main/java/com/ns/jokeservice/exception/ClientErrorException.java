package com.ns.jokeservice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;

@Getter
@Setter
public class ClientErrorException extends RuntimeException {

    public ClientErrorException(String msg) {
        super(msg);
    }

}
