package br.com.actios.actios_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um campo obrigatório não é informado em uma requisição.
 * <p>
 * Representa um erro de validação onde campos marcados como obrigatórios não foram
 * fornecidos ou estão vazios. Resulta em uma resposta HTTP 400 (Bad Request).
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CampoObrigatorioException extends RuntimeException {

    /**
     * Cria uma nova instância da exceção com uma mensagem específica.
     *
     * @param mensagem Descrição detalhada do campo obrigatório que está faltando.
     *                Deve seguir o formato: "O campo [nome_do_campo] é obrigatório"
     * @throws IllegalArgumentException Se a mensagem for nula ou vazia
     */
    public CampoObrigatorioException(String mensagem) {
        super(mensagem);
        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem da exceção não pode ser vazia");
        }
    }
}