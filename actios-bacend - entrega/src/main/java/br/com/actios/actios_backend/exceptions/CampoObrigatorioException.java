package br.com.actios.actios_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CampoObrigatorioException extends RuntimeException {
    public CampoObrigatorioException(String mensagem) {
        super(mensagem);
    }
}

