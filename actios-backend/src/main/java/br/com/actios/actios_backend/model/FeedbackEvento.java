package br.com.actios.actios_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    public FeedbackEvento() {
        this.dataFeedback = LocalDateTime.now();
    }
    
    public Integer getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(Integer idFeedback) {
        this.idFeedback = idFeedback;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        if (nota != null && (nota < 1 || nota > 5)) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5");
        }
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDataFeedback() {
        return dataFeedback;
    }
}
