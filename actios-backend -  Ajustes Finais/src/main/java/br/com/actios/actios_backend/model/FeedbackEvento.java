package br.com.actios.actios_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa um feedback sobre um evento.
 * <p>
 * Armazena avaliações e comentários de usuários sobre eventos específicos,
 * incluindo uma nota de 1 a 5 e um comentário textual. Mantém relacionamentos
 * com as entidades Usuario e Evento.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "feedback_eventos")
public class FeedbackEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feedback")
    private Integer idFeedback;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @Column(name = "nota")
    private Integer nota;

    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "data_feedback", nullable = false, updatable = false)
    private LocalDateTime dataFeedback;

    /**
     * Construtor padrão que inicializa a data do feedback com a data/hora atual.
     */
    public FeedbackEvento() {
        this.dataFeedback = LocalDateTime.now();
    }

    /**
     * Obtém o ID único do feedback.
     *
     * @return ID do feedback
     */
    public Integer getIdFeedback() {
        return idFeedback;
    }

    /**
     * Define o ID único do feedback.
     *
     * @param idFeedback ID do feedback (deve ser positivo)
     */
    public void setIdFeedback(Integer idFeedback) {
        this.idFeedback = idFeedback;
    }

    /**
     * Obtém o usuário que enviou o feedback.
     *
     * @return Instância do Usuario associado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário que enviou o feedback.
     *
     * @param usuario Instância do Usuario (não pode ser nulo)
     * @throws IllegalArgumentException Se o usuário for nulo
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtém o evento relacionado ao feedback.
     *
     * @return Instância do Evento associado
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Define o evento relacionado ao feedback.
     *
     * @param evento Instância do Evento (não pode ser nulo)
     * @throws IllegalArgumentException Se o evento for nulo
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Obtém a nota atribuída ao evento (1-5).
     *
     * @return Nota do evento (entre 1 e 5) ou null se não avaliado
     */
    public Integer getNota() {
        return nota;
    }

    /**
     * Define a nota atribuída ao evento.
     *
     * @param nota Valor da nota (deve estar entre 1 e 5, inclusive)
     * @throws IllegalArgumentException Se a nota estiver fora do intervalo válido
     */
    public void setNota(Integer nota) {
        if (nota != null && (nota < 1 || nota > 5)) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5");
        }
        this.nota = nota;
    }

    /**
     * Obtém o comentário sobre o evento.
     *
     * @return Texto do comentário ou null se não houver
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Define o comentário sobre o evento.
     *
     * @param comentario Texto do comentário (pode ser null)
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Obtém a data e hora em que o feedback foi registrado.
     * <p>
     * Este campo é automaticamente definido no momento da criação e não pode ser modificado.
     *
     * @return Data e hora do feedback
     */
    public LocalDateTime getDataFeedback() {
        return dataFeedback;
    }
}