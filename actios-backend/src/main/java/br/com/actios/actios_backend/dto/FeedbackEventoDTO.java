package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.FeedbackEvento;

import java.time.LocalDateTime;

public class FeedbackEventoDTO {
    private Integer id;
    private Integer nota;
    private String comentario;
    private String tituloEvento;
    private String nomeFaculdade;
    private LocalDateTime dataFeedback;

    // Construtor a partir da entidade
    public FeedbackEventoDTO(FeedbackEvento feedback) {
        this.id = feedback.getIdFeedback();
        this.nota = feedback.getNota();
        this.comentario = feedback.getComentario();
        this.dataFeedback = feedback.getDataFeedback();

        if (feedback.getEvento() != null) {
            this.tituloEvento = feedback.getEvento().getTitulo();
            if (feedback.getEvento().getFaculdade() != null) {
                this.nomeFaculdade = feedback.getEvento().getFaculdade().getNome();
            }
        }
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTituloEvento() {
        return tituloEvento;
    }

    public void setTituloEvento(String tituloEvento) {
        this.tituloEvento = tituloEvento;
    }

    public String getNomeFaculdade() {
        return nomeFaculdade;
    }

    public void setNomeFaculdade(String nomeFaculdade) {
        this.nomeFaculdade = nomeFaculdade;
    }

    public LocalDateTime getDataFeedback() {
        return dataFeedback;
    }

    public void setDataFeedback(LocalDateTime dataFeedback) {
        this.dataFeedback = dataFeedback;
    }
}
