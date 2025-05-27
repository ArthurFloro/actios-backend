package br.com.actios.actios_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RecursoExistenteException extends RuntimeException {
    public RecursoExistenteException(String mensagem) {
        super(mensagem);
    }
}


