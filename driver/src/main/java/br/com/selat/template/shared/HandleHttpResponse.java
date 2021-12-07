package br.com.selat.template.shared;

import br.com.selat.template.shared.exceptions.InternalErrorException;
import br.com.selat.template.shared.exceptions.NotFoundException;
import br.com.selat.template.shared.exceptions.ServiceValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

public class HandleHttpResponse <T>{

    public T handle(ResponseEntity<T> clientResponse){
        if (clientResponse.getStatusCode().is2xxSuccessful()){
            return clientResponse.getBody();
        }
        switch (clientResponse.getStatusCode()){
            case NOT_FOUND -> throw new NotFoundException();
            case BAD_REQUEST -> throw new ServiceValidationException();
            default -> throw new InternalErrorException();
        }
    }

    public void handleException(RestClientResponseException e) {
        switch (HttpStatus.valueOf(e.getRawStatusCode())){
            case NOT_FOUND -> throw new NotFoundException(e);
            case BAD_REQUEST -> throw new ServiceValidationException(e);
            default -> throw new InternalErrorException(e);
        }
    }
}
