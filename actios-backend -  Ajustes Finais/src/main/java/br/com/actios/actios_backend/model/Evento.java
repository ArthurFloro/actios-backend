package br.com.actios.actios_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Entidade que representa um evento no sistema.
 * <p>
 * Contém informações sobre eventos acadêmicos, culturais ou corporativos,
 * incluindo detalhes como data, local, palestrantes e feedbacks. Esta classe
 * gerencia os relacionamentos com faculdades, categorias e participantes.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Integer idEvento;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate data;

    private String horario;
    private String local;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculdade_id")
    private Faculdade faculdade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<EventoPalestrante> eventoPalestrantes = new HashSet<>();

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<FeedbackEvento> feedbacks = new HashSet<>();

    @OneToOne(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private EventoDetalhe detalhes;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Notificacao> notificacoes = new HashSet<>();

    /**
     * Obtém o ID único do evento.
     *
     * @return ID do evento
     */
    public Integer getIdEvento() {
        return idEvento;
    }

    /**
     * Define o ID único do evento.
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
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o título do evento.
     *
     * @param titulo Título do evento (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException Se o título for nulo ou vazio
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtém a descrição detalhada do evento.
     *
     * @return Descrição do evento ou null se não houver
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do evento.
     *
     * @param descricao Texto descritivo do evento
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a data do evento.
     *
     * @return Data do evento no formato yyyy-MM-dd
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Define a data do evento.
     *
     * @param data Data do evento (não pode ser nula ou anterior à data atual)
     * @throws IllegalArgumentException Se a data for inválida
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Obtém o horário do evento.
     *
     * @return Horário no formato de string ou null se não definido
     */
    public String getHorario() {
        return horario;
    }

    /**
     * Define o horário do evento.
     *
     * @param horario Horário no formato de string (ex: "14:00 às 18:00")
     */
    public void setHorario(String horario) {
        this.horario = horario;
    }

    /**
     * Obtém o local do evento.
     *
     * @return Local do evento ou null se não definido
     */
    public String getLocal() {
        return local;
    }

    /**
     * Define o local do evento.
     *
     * @param local Local físico do evento
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * Obtém a faculdade associada ao evento.
     *
     * @return Entidade Faculdade ou null se não associada
     */
    public Faculdade getFaculdade() {
        return faculdade;
    }

    /**
     * Define a faculdade organizadora do evento.
     *
     * @param faculdade Instância de Faculdade
     */
    public void setFaculdade(Faculdade faculdade) {
        this.faculdade = faculdade;
    }

    /**
     * Obtém a categoria do evento.
     *
     * @return Entidade Categoria ou null se não categorizado
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do evento.
     *
     * @param categoria Instância de Categoria
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtém os palestrantes associados ao evento.
     *
     * @return Conjunto de EventoPalestrante (pode ser vazio)
     */
    public Set<EventoPalestrante> getEventoPalestrantes() {
        return eventoPalestrantes;
    }

    /**
     * Define os palestrantes do evento.
     *
     * @param eventoPalestrantes Conjunto de EventoPalestrante
     */
    public void setEventoPalestrantes(Set<EventoPalestrante> eventoPalestrantes) {
        this.eventoPalestrantes = eventoPalestrantes;
    }

    /**
     * Obtém os feedbacks sobre o evento.
     *
     * @return Conjunto de FeedbackEvento (pode ser vazio)
     */
    public Set<FeedbackEvento> getFeedbacks() {
        return feedbacks;
    }

    /**
     * Define os feedbacks do evento.
     *
     * @param feedbacks Conjunto de FeedbackEvento
     */
    public void setFeedbacks(Set<FeedbackEvento> feedbacks) {
        this.feedbacks = feedbacks;
    }

    /**
     * Adiciona um feedback ao evento.
     *
     * @param feedback Instância de FeedbackEvento a ser adicionada
     */
    public void adicionarFeedback(FeedbackEvento feedback) {
        feedbacks.add(feedback);
        feedback.setEvento(this);
    }

    /**
     * Remove um feedback do evento.
     *
     * @param feedback Instância de FeedbackEvento a ser removida
     */
    public void removerFeedback(FeedbackEvento feedback) {
        feedbacks.remove(feedback);
        feedback.setEvento(null);
    }

    /**
     * Obtém os detalhes adicionais do evento.
     *
     * @return Instância de EventoDetalhe ou null se não houver
     */
    public EventoDetalhe getDetalhes() {
        return detalhes;
    }

    /**
     * Define os detalhes do evento.
     *
     * @param detalhes Instância de EventoDetalhe
     */
    public void setDetalhes(EventoDetalhe detalhes) {
        this.detalhes = detalhes;
    }

    /**
     * Adiciona detalhes ao evento.
     *
     * @param detalhes Instância de EventoDetalhe a ser associada
     */
    public void adicionarDetalhes(EventoDetalhe detalhes) {
        this.detalhes = detalhes;
        detalhes.setEvento(this);
    }

    /**
     * Remove os detalhes do evento.
     */
    public void removerDetalhes() {
        if (detalhes != null) {
            detalhes.setEvento(null);
            this.detalhes = null;
        }
    }
}