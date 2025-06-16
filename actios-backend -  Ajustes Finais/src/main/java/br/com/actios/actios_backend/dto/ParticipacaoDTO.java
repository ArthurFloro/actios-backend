package br.com.actios.actios_backend.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representação de participações em eventos.
 * <p>
 * Contém informações sobre a relação entre usuários e eventos, incluindo status de check-in,
 * feedback e dados de criação. Utilizado para transferência segura de dados entre camadas
 * da aplicação, especialmente em operações relacionadas a participações em eventos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class ParticipacaoDTO {

    private Integer idParticipacao;
    private Integer idUsuario;
    private String nomeUsuario;
    private Integer idEvento;
    private String tituloEvento;
    private Boolean checkin;
    private String feedback;
    private LocalDateTime dataCriacao;

    /**
     * Construtor padrão sem parâmetros.
     */
    public ParticipacaoDTO() {
    }

    /**
     * Construtor completo com todos os campos da participação.
     *
     * @param idParticipacao ID único da participação
     * @param idUsuario ID do usuário participante
     * @param nomeUsuario Nome completo do usuário
     * @param idEvento ID do evento relacionado
     * @param tituloEvento Título do evento
     * @param checkin Status de check-in do participante
     * @param feedback Comentários/avaliação do participante sobre o evento
     * @param dataCriacao Data e hora de criação do registro de participação
     */
    public ParticipacaoDTO(Integer idParticipacao, Integer idUsuario, String nomeUsuario,
                           Integer idEvento, String tituloEvento, Boolean checkin,
                           String feedback, LocalDateTime dataCriacao) {
        this.idParticipacao = idParticipacao;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.idEvento = idEvento;
        this.tituloEvento = tituloEvento;
        this.checkin = checkin;
        this.feedback = feedback;
        this.dataCriacao = dataCriacao;
    }

    /**
     * Obtém o ID único da participação.
     *
     * @return ID da participação
     */
    public Integer getIdParticipacao() {
        return idParticipacao;
    }

    /**
     * Define o ID único da participação.
     *
     * @param idParticipacao ID da participação (deve ser positivo)
     */
    public void setIdParticipacao(Integer idParticipacao) {
        this.idParticipacao = idParticipacao;
    }

    /**
     * Obtém o ID do usuário participante.
     *
     * @return ID do usuário
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Define o ID do usuário participante.
     *
     * @param idUsuario ID do usuário (deve ser positivo)
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtém o nome completo do usuário participante.
     *
     * @return Nome do usuário
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * Define o nome do usuário participante.
     *
     * @param nomeUsuario Nome completo (não pode ser vazio ou nulo)
     * @throws IllegalArgumentException Se o nome for vazio ou nulo
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    /**
     * Obtém o ID do evento relacionado.
     *
     * @return ID do evento
     */
    public Integer getIdEvento() {
        return idEvento;
    }

    /**
     * Define o ID do evento relacionado.
     *
     * @param idEvento ID do evento (deve ser positivo)
     */
    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    /**
     * Obtém o título do evento.
     *
     * @return Título do evento
     */
    public String getTituloEvento() {
        return tituloEvento;
    }

    /**
     * Define o título do evento.
     *
     * @param tituloEvento Título do evento (não pode ser vazio ou nulo)
     * @throws IllegalArgumentException Se o título for vazio ou nulo
     */
    public void setTituloEvento(String tituloEvento) {
        this.tituloEvento = tituloEvento;
    }

    /**
     * Verifica se o check-in foi realizado.
     *
     * @return true se o check-in foi realizado, false caso contrário
     */
    public Boolean getCheckin() {
        return checkin;
    }

    /**
     * Define o status de check-in do participante.
     *
     * @param checkin Status do check-in (true = realizado, false = não realizado)
     */
    public void setCheckin(Boolean checkin) {
        this.checkin = checkin;
    }

    /**
     * Obtém o feedback/avaliação do participante sobre o evento.
     *
     * @return Texto do feedback ou null se não houver feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Define o feedback/avaliação do participante.
     *
     * @param feedback Texto do feedback (pode ser null se não houver feedback)
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Obtém a data e hora de criação do registro de participação.
     *
     * @return Data e hora de criação
     */
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    /**
     * Define a data e hora de criação do registro.
     *
     * @param dataCriacao Data e hora de criação (não pode ser futura)
     * @throws IllegalArgumentException Se a data for futura
     */
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}