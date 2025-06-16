package br.com.actios.actios_backend.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para representação de registros de certificados.
 * <p>
 * Contém informações sobre certificados emitidos, incluindo dados do participante,
 * detalhes do curso e informações de validação. Utilizado para transferência segura
 * de dados entre camadas da aplicação, especialmente em operações relacionadas a
 * certificados de participação em eventos/cursos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class RegistroCertificadoDTO {
    private Integer idCertificado;
    private String nomeUsuario;
    private String emailUsuario;
    private String nomeCurso;
    private String codigoValidacao;
    private LocalDate dataEmissao;

    /**
     * Obtém o ID único do certificado.
     *
     * @return ID do certificado
     */
    public Integer getIdCertificado() {
        return idCertificado;
    }

    /**
     * Define o ID único do certificado.
     *
     * @param idCertificado ID do certificado (deve ser positivo)
     * @throws IllegalArgumentException Se o ID for negativo
     */
    public void setIdCertificado(Integer idCertificado) {
        this.idCertificado = idCertificado;
    }

    /**
     * Obtém o nome completo do usuário que recebeu o certificado.
     *
     * @return Nome do participante
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * Define o nome do participante que recebeu o certificado.
     *
     * @param nomeUsuario Nome completo (não pode ser vazio ou nulo)
     * @throws IllegalArgumentException Se o nome for vazio ou nulo
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    /**
     * Obtém o email do participante que recebeu o certificado.
     *
     * @return Email válido do participante
     */
    public String getEmailUsuario() {
        return emailUsuario;
    }

    /**
     * Define o email do participante.
     *
     * @param emailUsuario Email válido (deve seguir formato padrão de email)
     * @throws IllegalArgumentException Se o email for inválido
     */
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    /**
     * Obtém o nome do curso/evento associado ao certificado.
     *
     * @return Nome do curso/evento
     */
    public String getNomeCurso() {
        return nomeCurso;
    }

    /**
     * Define o nome do curso/evento associado ao certificado.
     *
     * @param nomeCurso Nome do curso/evento (não pode ser vazio ou nulo)
     * @throws IllegalArgumentException Se o nome do curso for vazio ou nulo
     */
    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    /**
     * Obtém o código único de validação do certificado.
     *
     * @return Código alfanumérico de validação
     */
    public String getCodigoValidacao() {
        return codigoValidacao;
    }

    /**
     * Define o código único de validação do certificado.
     *
     * @param codigoValidacao Código alfanumérico (não pode ser vazio ou nulo)
     * @throws IllegalArgumentException Se o código for vazio ou nulo
     */
    public void setCodigoValidacao(String codigoValidacao) {
        this.codigoValidacao = codigoValidacao;
    }

    /**
     * Obtém a data de emissão do certificado.
     *
     * @return Data de emissão (não pode ser futura)
     */
    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Define a data de emissão do certificado.
     *
     * @param dataEmissao Data de emissão (não pode ser futura)
     * @throws IllegalArgumentException Se a data for futura
     */
    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
}