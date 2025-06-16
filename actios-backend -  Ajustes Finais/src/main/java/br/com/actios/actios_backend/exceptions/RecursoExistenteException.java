package br.com.actios.actios_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando ocorre uma tentativa de criar ou atualizar um recurso que já existe no sistema.
 * <p>
 * Representa um erro de conflito onde a operação não pode ser concluída porque viola restrições
 * de unicidade do sistema. Resulta em uma resposta HTTP 409 (Conflict).
 *
 * <p>Tipicamente utilizado para casos como:
 * <ul>
 *   <li>Tentativa de cadastrar um e-mail já existente</li>
 *   <li>Criação de recurso com identificador duplicado</li>
 *   <li>Atualização que resultaria em dados duplicados</li>
 * </ul>
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class RecursoExistenteException extends RuntimeException {

    /**
     * Cria uma nova instância da exceção com uma mensagem específica.
     *
     * @param mensagem Descrição detalhada do conflito.
     *                Deve seguir o formato: "[Tipo do Recurso] já existe: [identificador]".
     *                Exemplo: "Usuário já existe: usuario@email.com"
     * @throws IllegalArgumentException Se a mensagem for nula ou vazia
     */
    public RecursoExistenteException(String mensagem) {
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