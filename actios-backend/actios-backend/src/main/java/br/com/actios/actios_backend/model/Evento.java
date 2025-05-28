package br.com.actios.actios_backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @Column(nullable = false)
    private boolean ativo = true;

    // Getters e setters

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Faculdade getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(Faculdade faculdade) {
        this.faculdade = faculdade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<EventoPalestrante> getEventoPalestrantes() {
        return eventoPalestrantes;
    }

    public void setEventoPalestrantes(Set<EventoPalestrante> eventoPalestrantes) {
        this.eventoPalestrantes = eventoPalestrantes;
    }

    public Set<FeedbackEvento> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<FeedbackEvento> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public void adicionarFeedback(FeedbackEvento feedback) {
        feedbacks.add(feedback);
        feedback.setEvento(this);
    }

    public void removerFeedback(FeedbackEvento feedback) {
        feedbacks.remove(feedback);
        feedback.setEvento(null);
    }

    public EventoDetalhe getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(EventoDetalhe detalhes) {
        this.detalhes = detalhes;
    }

    public void adicionarDetalhes(EventoDetalhe detalhes) {
        this.detalhes = detalhes;
        detalhes.setEvento(this);
    }

    public void removerDetalhes() {
        if (detalhes != null) {
            detalhes.setEvento(null);
            this.detalhes = null;
        }
    }

    public Set<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Set<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
