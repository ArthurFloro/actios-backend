package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.Notificacao;

import java.time.LocalDateTime;

public class NotificacaoDTO {
    private Long id;
    private String mensagem;
    private LocalDateTime dataEnvio;
    private String nomeEvento;
    private String nomeFaculdade;

    public NotificacaoDTO(Notificacao n) {
        this.id = n.getIdNotificacao().longValue();
        this.mensagem = n.getMensagem();
        this.dataEnvio = n.getDataEnvio();
        this.nomeEvento = n.getEvento().getTitulo();
        this.nomeFaculdade = n.getEvento().getFaculdade().getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getNomeFaculdade() {
        return nomeFaculdade;
    }

    public void setNomeFaculdade(String nomeFaculdade) {
        this.nomeFaculdade = nomeFaculdade;
    }
}

