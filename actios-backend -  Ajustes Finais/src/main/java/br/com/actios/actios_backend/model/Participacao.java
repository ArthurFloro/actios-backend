package br.com.actios.actios_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa a participação de um usuário em um evento.
 * <p>
 * Registra informações como presença confirmada (check-in), feedback do participante
 * e datas de criação/atualização. Atualiza automaticamente a data de modificação
 * quando campos são alterados.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "participacoes")
public class Participacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_participacao")
    private Integer idParticipacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @Column(name = "checkin")
    private Boolean checkin;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    /**
     * Construtor padrão que inicializa as datas de criação e atualização.
     */
    public Participacao() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
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
     * @throws IllegalArgumentException Se o ID for negativo
     */
    public void setIdParticipacao(Integer idParticipacao) {
        if (idParticipacao != null && idParticipacao < 0) {
            throw new IllegalArgumentException("ID da participação deve ser positivo");
        }
        this.idParticipacao = idParticipacao;
    }

    /**
     * Obtém o usuário participante.
     *
     * @return Instância do Usuario associado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário participante e atualiza a data de modificação.
     *
     * @param usuario Instância do Usuario (não pode ser nulo)
     * @throws IllegalArgumentException Se o usuário for nulo
     */
    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        this.usuario = usuario;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Obtém o evento relacionado.
     *
     * @return Instância do Evento associado
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Define o evento relacionado e atualiza a data de modificação.
     *
     * @param evento Instância do Evento (não pode ser nulo)
     * @throws IllegalArgumentException Se o evento for nulo
     */
    public void setEvento(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Evento é obrigatório");
        }
        this.evento = evento;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Verifica se o check-in foi realizado.
     *
     * @return true se o check-in foi realizado, false caso contrário, ou null se não definido
     */
    public Boolean getCheckin() {
        return checkin;
    }

    /**
     * Define o status do check-in e atualiza a data de modificação.
     *
     * @param checkin Status do check-in (true = realizado)
     */
    public void setCheckin(Boolean checkin) {
        this.checkin = checkin;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Obtém o feedback do participante sobre o evento.
     *
     * @return Texto do feedback ou null se não houver
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Define o feedback do participante e atualiza a data de modificação.
     *
     * @param feedback Texto do feedback (máximo 2000 caracteres)
     * @throws IllegalArgumentException Se o feedback exceder o limite
     */
    public void setFeedback(String feedback) {
        if (feedback != null && feedback.length() > 2000) {
            throw new IllegalArgumentException("Feedback não pode exceder 2000 caracteres");
        }
        this.feedback = feedback;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Obtém a data de criação da participação.
     * <p>
     * Este campo é definido automaticamente no construtor e não pode ser modificado.
     *
     * @return Data/hora da criação
     */
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    /**
     * Obtém a data da última atualização da participação.
     *
     * @return Data/hora da última atualização
     */
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
}