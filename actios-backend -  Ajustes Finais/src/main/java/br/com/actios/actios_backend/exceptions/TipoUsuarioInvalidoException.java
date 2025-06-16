package br.com.actios.actios_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um tipo de usuário inválido é fornecido ou acessado.
 * <p>
 * Representa um erro de validação onde o tipo de usuário especificado não existe
 * ou não é permitido para a operação solicitada. Resulta em uma resposta HTTP 400 (Bad Request).
 *
 * <p>Utilizada em situações como:
 * <ul>
 *   <li>Tentativa de cadastro com tipo de usuário inexistente</li>
 *   <li>Acesso a funcionalidade não permitida para o tipo de usuário</li>
 *   <li>Atribuição de perfil incorreto</li>
 * </ul>
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TipoUsuarioInvalidoException extends RuntimeException {

    /**
     * Cria uma nova instância da exceção com uma mensagem específica.
     *
     * @param mensagem Descrição detalhada do erro de tipo de usuário inválido.
     *                Deve seguir o formato: "Tipo de usuário inválido: [detalhe_do_erro]".
     *                Exemplo: "Tipo de usuário inválido: 'ADMIN' não é um tipo válido"
     * @throws IllegalArgumentException Se a mensagem for nula ou vazia
     */
    public TipoUsuarioInvalidoException(String mensagem) {
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