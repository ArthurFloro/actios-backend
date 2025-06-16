package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representação de usuários do sistema.
 * <p>
 * Contém informações básicas do usuário, dados acadêmicos e temporal de cadastro.
 * Utilizado para transferência segura de dados entre camadas da aplicação,
 * especialmente em operações de gestão de usuários.
 *
 * <p>Inclui método utilitário para conversão de entidade {@link Usuario} para DTO.
 *
 * <p>Versão 1.1 - Adicionado suporte para faculdadeId no cadastro
 *
 * @author Equipe Actios
 * @version 1.1
 * @since 2023-08-30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    /**
     * ID único do usuário no sistema
     */
    private Integer idUsuario;

    /**
     * Nome completo do usuário
     */
    private String nome;

    /**
     * Email institucional do usuário
     */
    private String email;

    /**
     * Senha do usuário (usada apenas no cadastro)
     */
    private String senha;

    /**
     * Nome do curso do usuário
     */
    private String curso;

    /**
     * Nome da faculdade do usuário (pode ser null se não vinculado)
     */
    private String faculdade;

    /**
     * ID da faculdade do usuário (usado para cadastro/atualização)
     */
    private Integer faculdadeId;

    /**
     * Data e hora do cadastro do usuário no sistema
     */
    private LocalDateTime dataCadastro;

    /**
     * Converte uma entidade {@link Usuario} para seu respectivo DTO.
     * <p>
     * Realiza o mapeamento dos campos básicos e trata a conversão da relação com Faculdade.
     *
     * @param usuario Entidade Usuario a ser convertida (pode ser null)
     * @return Instância de UsuarioDTO ou null se o parâmetro for null
     */
    public static UsuarioDTO fromUsuario(Usuario usuario) {
        if (usuario == null) return null;

        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .curso(usuario.getCurso())
                .faculdade(usuario.getFaculdade() != null ? usuario.getFaculdade().getNome() : null)
                .faculdadeId(usuario.getFaculdade() != null ? usuario.getFaculdade().getIdFaculdade() : null)
                .dataCadastro(usuario.getDataCadastro())
                .build();
    }
}