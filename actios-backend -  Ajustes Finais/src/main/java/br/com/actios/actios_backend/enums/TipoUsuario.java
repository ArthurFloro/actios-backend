package br.com.actios.actios_backend.enums;

/**
 * Enumeração que representa os tipos de usuários do sistema.
 * <p>
 * Define os perfis de usuário suportados pela aplicação, distinguindo entre
 * usuários do tipo aluno e instituições de ensino (faculdades).
 *
 * <p>Utilizado principalmente para controle de acesso e personalização de fluxos
 * conforme o tipo de usuário.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public enum TipoUsuario {
    /**
     * Representa um usuário estudante/aluno.
     * <p>
     * Possui permissões para participar de eventos, visualizar certificados
     * e interagir com conteúdos acadêmicos.
     */
    ALUNO,

    /**
     * Representa uma instituição de ensino (faculdade/universidade).
     * <p>
     * Possui permissões para criar e gerenciar eventos, visualizar relatórios
     * e administrar participantes.
     */
    FACULDADE
}