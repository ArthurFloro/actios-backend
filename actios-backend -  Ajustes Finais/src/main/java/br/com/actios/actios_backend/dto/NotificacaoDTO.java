package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.Notificacao;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representação de notificações.
 * <p>
 * Contém informações sobre notificações enviadas aos usuários, incluindo
 * dados do evento e faculdade relacionada.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class NotificacaoDTO {
    private Long id;
    private String mensagem;
    private LocalDateTime dataEnvio;
    private String nomeEvento;
    private String nomeFaculdade;

    /**
     * Construtor que converte uma entidade Notificacao para DTO.
     * <p>
     * Extrai informações básicas da notificação e dados relacionados ao evento
     * e faculdade organizadora.
     *
     * @param n Entidade Notificacao a ser convertida
     * @throws NullPointerException Se o evento ou faculdade forem nulos
     */
    public NotificacaoDTO(Notificacao n) {
        this.id = n.getIdNotificacao().longValue();
        this.mensagem = n.getMensagem();
        this.dataEnvio = n.getDataEnvio();
        this.nomeEvento = n.getEvento().getTitulo();
        this.nomeFaculdade = n.getEvento().getFaculdade().getNome();
    }

    /**
     * Obtém o ID único da notificação.
     *
     * @return ID da notificação
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID da notificação.
     *
     * @param id ID único da notificação
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o conteúdo da mensagem da notificação.
     *
     * @return Texto completo da notificação
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Define o conteúdo da mensagem da notificação.
     *
     * @param mensagem Texto da notificação
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Obtém a data e hora de envio da notificação.
     *
     * @return Data/hora do envio
     */
    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    /**
     * Define a data e hora de envio da notificação.
     *
     * @param dataEnvio Data/hora do envio
     */
    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    /**
     * Obtém o nome do evento relacionado à notificação.
     *
     * @return Título do evento
     */
    public String getNomeEvento() {
        return nomeEvento;
    }

    /**
     * Define o nome do evento relacionado.
     *
     * @param nomeEvento Título do evento
     */
    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    /**
     * Obtém o nome da faculdade organizadora do evento.
     *
     * @return Nome da faculdade
     */
    public String getNomeFaculdade() {
        return nomeFaculdade;
    }

    /**
     * Define o nome da faculdade organizadora.
     *
     * @param nomeFaculdade Nome da faculdade
     */
    public void setNomeFaculdade(String nomeFaculdade) {
        this.nomeFaculdade = nomeFaculdade;
    }
}