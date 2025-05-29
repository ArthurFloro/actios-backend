package br.com.actios.actios_backend.model;

import br.com.actios.actios_backend.model.EventoPalestranteId;

import jakarta.persistence.*;

@Entity
@IdClass(EventoPalestranteId.class)
public class EventoPalestrante {

    @Id
    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Id
    @ManyToOne
    @JoinColumn(name = "palestrante_id", nullable = false)
    private Palestrante palestrante;

    public EventoPalestrante() {}

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Palestrante getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(Palestrante palestrante) {
        this.palestrante = palestrante;
    }
}
