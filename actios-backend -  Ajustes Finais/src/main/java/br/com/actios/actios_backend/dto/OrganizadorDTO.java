package br.com.actios.actios_backend.dto;

/**
 * Data Transfer Object (DTO) para representação de organizadores de eventos.
 * <p>
 * Contém informações básicas sobre organizadores e sua relação com faculdades.
 * Utilizado para transferência segura de dados entre camadas da aplicação.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class OrganizadorDTO {
    private String nome;
    private String email;
    private Integer idFaculdade;

    /**
     * Obtém o nome completo do organizador.
     *
     * @return Nome do organizador
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do organizador.
     *
     * @param nome Nome completo do organizador (não pode ser vazio)
     * @throws IllegalArgumentException Se o nome for vazio ou nulo
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o email do organizador.
     *
     * @return Endereço de email válido
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do organizador.
     *
     * @param email Endereço de email válido
     * @throws IllegalArgumentException Se o email for inválido
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém o ID da faculdade associada ao organizador.
     *
     * @return ID da faculdade ou null se não associado
     */
    public Integer getIdFaculdade() {
        return idFaculdade;
    }

    /**
     * Define a faculdade associada ao organizador.
     *
     * @param idFaculdade ID da faculdade organizadora
     */
    public void setIdFaculdade(Integer idFaculdade) {
        this.idFaculdade = idFaculdade;
    }
}