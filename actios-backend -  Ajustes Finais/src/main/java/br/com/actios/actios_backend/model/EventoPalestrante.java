package br.com.actios.actios_backend.model;

import br.com.actios.actios_backend.model.EventoPalestranteId;
import jakarta.persistence.*;

/**
 * Entidade que representa a relação entre eventos e palestrantes.
 * <p>
 * Esta classe implementa uma relação many-to-many entre Evento e Palestrante
 * utilizando uma chave primária composta (EventoPalestranteId). Serve como
 * entidade de junção com possibilidade de adicionar atributos adicionais
 * no futuro.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
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

    /**
     * Construtor padrão sem parâmetros.
     */
    public EventoPalestrante() {}

    /**
     * Obtém o evento associado a esta relação.
     *
     * @return Instância do Evento associado (não pode ser nulo)
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Define o evento para esta relação.
     *
     * @param evento Instância do Evento (não pode ser nula)
     * @throws IllegalArgumentException Se o evento for nulo
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Obtém o palestrante associado a esta relação.
     *
     * @return Instância do Palestrante associado (não pode ser nulo)
     */
    public Palestrante getPalestrante() {
        return palestrante;
    }

    /**
     * Define o palestrante para esta relação.
     *
     * @param palestrante Instância do Palestrante (não pode ser nula)
     * @throws IllegalArgumentException Se o palestrante for nulo
     */
    public void setPalestrante(Palestrante palestrante) {
        this.palestrante = palestrante;
    }
}