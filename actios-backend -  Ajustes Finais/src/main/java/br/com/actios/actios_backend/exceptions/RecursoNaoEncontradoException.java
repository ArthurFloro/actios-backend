package br.com.actios.actios_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado no sistema.
 * <p>
 * Representa um erro de busca onde o recurso com o identificador especificado não existe
 * ou não está acessível para o usuário atual. Resulta em uma resposta HTTP 404 (Not Found).
 *
 * <p>Utilizada em operações de:
 * <ul>
 *   <li>Busca por ID inexistente</li>
 *   <li>Acesso a recurso removido</li>
 *   <li>Operações sobre entidades não persistidas</li>
 * </ul>
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException {

    /**
     * Cria uma nova instância da exceção com uma mensagem específica.
     *
     * @param mensagem Descrição detalhada do erro de recurso não encontrado.
     *                Deve seguir o formato: "[Tipo do Recurso] não encontrado: [identificador]".
     *                Exemplo: "Usuário não encontrado: 12345"
     * @throws IllegalArgumentException Se a mensagem for nula ou vazia
     */
    public RecursoNaoEncontradoException(String mensagem) {
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