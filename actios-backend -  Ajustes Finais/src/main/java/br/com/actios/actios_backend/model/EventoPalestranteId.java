package br.com.actios.actios_backend.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que representa a chave primária composta para a entidade EventoPalestrante.
 * <p>
 * Implementa Serializable para permitir serialização e é usada como ID composto
 * na entidade de junção entre Evento e Palestrante. Deve implementar equals() e hashCode()
 * para garantir o correto funcionamento do JPA.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class EventoPalestranteId implements Serializable {

    private Integer evento;
    private Integer palestrante;

    /**
     * Construtor padrão sem argumentos, requerido pelo JPA.
     */
    public EventoPalestranteId() {}

    /**
     * Construtor com todos os campos da chave composta.
     *
     * @param evento ID do evento (não pode ser nulo)
     * @param palestrante ID do palestrante (não pode ser nulo)
     * @throws IllegalArgumentException Se algum dos parâmetros for nulo
     */
    public EventoPalestranteId(Integer evento, Integer palestrante) {
        this.evento = evento;
        this.palestrante = palestrante;
    }

    /**
     * Obtém o ID do evento que compõe a chave primária.
     *
     * @return ID do evento
     */
    public Integer getEvento() {
        return evento;
    }

    /**
     * Define o ID do evento que compõe a chave primária.
     *
     * @param evento ID do evento (deve ser positivo)
     */
    public void setEvento(Integer evento) {
        this.evento = evento;
    }

    /**
     * Obtém o ID do palestrante que compõe a chave primária.
     *
     * @return ID do palestrante
     */
    public Integer getPalestrante() {
        return palestrante;
    }

    /**
     * Define o ID do palestrante que compõe a chave primária.
     *
     * @param palestrante ID do palestrante (deve ser positivo)
     */
    public void setPalestrante(Integer palestrante) {
        this.palestrante = palestrante;
    }

    /**
     * Compara esta chave composta com outro objeto para igualdade.
     * <p>
     * Requerido para o correto funcionamento do JPA com chaves compostas.
     *
     * @param o Objeto a ser comparado
     * @return true se os objetos forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventoPalestranteId)) return false;
        EventoPalestranteId that = (EventoPalestranteId) o;
        return Objects.equals(evento, that.evento) &&
                Objects.equals(palestrante, that.palestrante);
    }

    /**
     * Retorna o valor hash para esta chave composta.
     * <p>
     * Requerido para o correto funcionamento do JPA com chaves compostas.
     *
     * @return Valor hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(evento, palestrante);
    }
}