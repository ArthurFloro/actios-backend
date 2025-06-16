package br.com.actios.actios_backend.model;

import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.enums.FormatoEventoConverter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidade que representa os detalhes adicionais de um evento.
 * <p>
 * Contém informações complementares como data de término, formato do evento,
 * disponibilidade de certificado e valor. Está diretamente vinculada à entidade
 * Evento através de uma relação one-to-one.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "eventos_detalhes")
public class EventoDetalhe {

    @Id
    @Column(name = "id_evento")
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Convert(converter = FormatoEventoConverter.class)
    @Column(name = "formato")
    private FormatoEvento formato;

    private Boolean certificado;
    private BigDecimal valor;

    /**
     * Obtém o ID do detalhe do evento (compartilhado com o ID do evento).
     *
     * @return ID do detalhe do evento
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do detalhe do evento.
     *
     * @param id ID do detalhe (deve corresponder ao ID do evento associado)
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém o evento associado a estes detalhes.
     *
     * @return Instância do Evento associado
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Define o evento associado a estes detalhes.
     *
     * @param evento Instância do Evento a ser associada
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Obtém a data de término do evento.
     *
     * @return Data de término ou null se for um evento de um único dia
     */
    public LocalDate getDataFim() {
        return dataFim;
    }

    /**
     * Define a data de término do evento.
     *
     * @param dataFim Data de término (deve ser posterior ou igual à data de início do evento)
     * @throws IllegalArgumentException Se a data for anterior à data de início do evento
     */
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Obtém o formato do evento (presencial, online ou híbrido).
     *
     * @return Enum FormatoEvento representando o formato
     */
    public FormatoEvento getFormato() {
        return formato;
    }

    /**
     * Define o formato do evento.
     *
     * @param formato Enum FormatoEvento (não pode ser nulo)
     * @throws IllegalArgumentException Se o formato for nulo
     */
    public void setFormato(FormatoEvento formato) {
        this.formato = formato;
    }

    /**
     * Verifica se o evento emite certificado.
     *
     * @return true se emitir certificado, false caso contrário
     */
    public Boolean getCertificado() {
        return certificado;
    }

    /**
     * Define se o evento emite certificado.
     *
     * @param certificado true para eventos com certificado, false caso contrário
     */
    public void setCertificado(Boolean certificado) {
        this.certificado = certificado;
    }

    /**
     * Obtém o valor do evento, se houver custo.
     *
     * @return Valor do evento como BigDecimal ou null se for gratuito
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define o valor do evento.
     *
     * @param valor Valor do evento (deve ser positivo ou zero)
     * @throws IllegalArgumentException Se o valor for negativo
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}