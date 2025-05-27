package br.com.actios.actios_backend.dto;

import java.time.LocalDateTime;

public class ParticipacaoDTO {

    private Integer idParticipacao;
    private Integer idUsuario;
    private String nomeUsuario;
    private Integer idEvento;
    private String tituloEvento;
    private Boolean checkin;
    private String feedback;
    private LocalDateTime dataCriacao;

    public ParticipacaoDTO() {
    }

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

    // Getters e setters

    public Integer getIdParticipacao() {
        return idParticipacao;
    }

    public void setIdParticipacao(Integer idParticipacao) {
        this.idParticipacao = idParticipacao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getTituloEvento() {
        return tituloEvento;
    }

    public void setTituloEvento(String tituloEvento) {
        this.tituloEvento = tituloEvento;
    }

    public Boolean getCheckin() {
        return checkin;
    }

    public void setCheckin(Boolean checkin) {
        this.checkin = checkin;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
