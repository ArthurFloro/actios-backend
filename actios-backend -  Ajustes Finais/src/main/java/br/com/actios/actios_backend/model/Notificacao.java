package br.com.actios.actios_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma notificação enviada a um usuário.
 * <p>
 * Pode estar associada a um evento específico ou ser uma notificação geral.
 * Armazena a mensagem, data de envio e os relacionamentos com usuário e evento.
 * Utiliza {@code @JsonBackReference} para evitar serialização circular.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "notificacoes")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacao")
    private Integer idNotificacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    @JsonBackReference
    private Evento evento;

    @Column(name = "data_envio", nullable = false, updatable = false)
    private LocalDateTime dataEnvio;

    @Column(name = "mensagem", columnDefinition = "TEXT")
    private String mensagem;

    /**
     * Construtor padrão que inicializa a data de envio com a data/hora atual.
     */
    public Notificacao() {
        this.dataEnvio = LocalDateTime.now();
    }

    /**
     * Obtém o ID único da notificação.
     *
     * @return ID da notificação
     */
    public Integer getIdNotificacao() {
        return idNotificacao;
    }

    /**
     * Define o ID único da notificação.
     *
     * @param idNotificacao ID da notificação (deve ser positivo)
     */
    public void setIdNotificacao(Integer idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

    /**
     * Obtém o usuário destinatário da notificação.
     *
     * @return Instância do Usuario ou null se notificação geral
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário destinatário da notificação.
     *
     * @param usuario Instância do Usuario (pode ser null para notificações gerais)
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtém o evento relacionado à notificação.
     *
     * @return Instância do Evento ou null se notificação geral
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Define o evento relacionado à notificação.
     *
     * @param evento Instância do Evento (pode ser null para notificações gerais)
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Obtém a data e hora em que a notificação foi enviada.
     * <p>
     * Este campo é definido automaticamente no momento da criação e não pode ser alterado.
     *
     * @return Data/hora do envio
     */
    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    /**
     * Obtém o conteúdo textual da notificação.
     *
     * @return Mensagem da notificação ou null se não definida
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Define o conteúdo textual da notificação.
     *
     * @param mensagem Texto da notificação (não pode ser vazio)
     * @throws IllegalArgumentException Se a mensagem for vazia ou contiver apenas espaços
     */
    public void setMensagem(String mensagem) {
        if (mensagem != null && mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem não pode ser vazia");
        }
        this.mensagem = mensagem;
    }
}