package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.FeedbackEvento;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representação de feedbacks sobre eventos.
 * <p>
 * Contém informações sobre avaliações de participantes, incluindo nota, comentário
 * e dados contextuais sobre o evento avaliado.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class FeedbackEventoDTO {
    private Integer id;
    private Integer nota;
    private String comentario;
    private String tituloEvento;
    private String nomeFaculdade;
    private LocalDateTime dataFeedback;

    /**
     * Construtor que converte uma entidade FeedbackEvento para DTO.
     * <p>
     * Extrai informações básicas do feedback e dados relacionados ao evento.
     *
     * @param feedback Entidade FeedbackEvento a ser convertida
     */
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

    /**
     * Obtém o ID único do feedback.
     *
     * @return ID do feedback
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do feedback.
     *
     * @param id ID único do feedback
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém a nota atribuída ao evento (1-5).
     *
     * @return Nota de avaliação (1 a 5)
     */
    public Integer getNota() {
        return nota;
    }

    /**
     * Define a nota do feedback.
     *
     * @param nota Nota de avaliação (1 a 5)
     * @throws IllegalArgumentException Se a nota estiver fora do intervalo válido
     */
    public void setNota(Integer nota) {
        this.nota = nota;
    }

    /**
     * Obtém o comentário sobre o evento.
     *
     * @return Texto do comentário ou null se não existir
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Define o comentário sobre o evento.
     *
     * @param comentario Texto do comentário
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Obtém o título do evento avaliado.
     *
     * @return Título do evento ou null se não disponível
     */
    public String getTituloEvento() {
        return tituloEvento;
    }

    /**
     * Define o título do evento avaliado.
     *
     * @param tituloEvento Título do evento
     */
    public void setTituloEvento(String tituloEvento) {
        this.tituloEvento = tituloEvento;
    }

    /**
     * Obtém o nome da faculdade organizadora do evento.
     *
     * @return Nome da faculdade ou null se não disponível
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

    /**
     * Obtém a data/hora em que o feedback foi registrado.
     *
     * @return Data e hora do feedback
     */
    public LocalDateTime getDataFeedback() {
        return dataFeedback;
    }

    /**
     * Define a data/hora do feedback.
     *
     * @param dataFeedback Data e hora do registro
     */
    public void setDataFeedback(LocalDateTime dataFeedback) {
        this.dataFeedback = dataFeedback;
    }
}