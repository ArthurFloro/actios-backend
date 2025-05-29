package br.com.actios.actios_backend.model;

import java.io.Serializable;
import java.util.Objects;

public class EventoPalestranteId implements Serializable {

    private Integer evento;
    private Integer palestrante;

    public EventoPalestranteId() {}

    public EventoPalestranteId(Integer evento, Integer palestrante) {
        this.evento = evento;
        this.palestrante = palestrante;
    }

    public Integer getEvento() {
        return evento;
    }

    public void setEvento(Integer evento) {
        this.evento = evento;
    }

    public Integer getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(Integer palestrante) {
        this.palestrante = palestrante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventoPalestranteId)) return false;
        EventoPalestranteId that = (EventoPalestranteId) o;
        return Objects.equals(evento, that.evento) &&
                Objects.equals(palestrante, that.palestrante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evento, palestrante);
    }
}
