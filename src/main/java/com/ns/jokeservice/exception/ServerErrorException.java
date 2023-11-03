package com.ns.jokeservice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.reactive.function.client.ClientResponse;

@Getter
@Setter
public class ServerErrorException extends RuntimeException{

    public ServerErrorException(String msg) {
        super(msg);
    }
}
