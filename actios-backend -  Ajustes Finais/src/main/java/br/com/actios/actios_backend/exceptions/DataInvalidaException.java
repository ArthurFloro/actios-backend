package br.com.actios.actios_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando uma data inválida é fornecida em uma requisição.
 * <p>
 * Representa um erro de validação onde uma data está em formato incorreto, é anterior à data atual,
 * ou não atende às regras de negócio. Resulta em uma resposta HTTP 400 (Bad Request).
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataInvalidaException extends RuntimeException {

    /**
     * Cria uma nova instância da exceção com uma mensagem específica.
     *
     * @param mensagem Descrição detalhada do erro de validação da data.
     *                Deve seguir o formato: "Data inválida: [detalhe_do_erro]".
     *                Exemplo: "Data inválida: não pode ser anterior à data atual"
     * @throws IllegalArgumentException Se a mensagem for nula ou vazia
     */
    public DataInvalidaException(String mensagem) {
        super(validarMensagem(mensagem));
    }

    /**
     * Valida a mensagem de erro garantindo que não seja nula ou vazia.
     *
     * @param mensagem Mensagem a ser validada
     * @return A mensagem validada
     * @throws IllegalArgumentException Se a mensagem for inválida
     */
    private static String validarMensagem(String mensagem) {
        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem da exceção não pode ser vazia");
        }
        return mensagem;
    }
}