package br.com.actios.actios_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando as credenciais de autenticação são inválidas ou incorretas.
 * <p>
 * Representa um erro de segurança onde as credenciais fornecidas (como login/senha)
 * não correspondem a nenhum usuário válido no sistema. Resulta em uma resposta HTTP 401 (Unauthorized).
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CredenciaisInvalidasException extends RuntimeException {

    /**
     * Cria uma nova instância da exceção com uma mensagem específica.
     *
     * @param mensagem Descrição detalhada do erro de autenticação.
     *                Deve seguir o formato: "Credenciais inválidas: [detalhe_do_erro]"
     * @throws IllegalArgumentException Se a mensagem for nula ou vazia
     */
    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem da exceção não pode ser vazia");
        }
    }
}