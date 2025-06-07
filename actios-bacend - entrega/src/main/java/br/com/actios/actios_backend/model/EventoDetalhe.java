package br.com.actios.actios_backend.model;

import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.enums.FormatoEventoConverter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Convert(converter = FormatoEventoConverter.class) // ✅ CORRETO
    @Column(name = "formato") // adicione explicitamente se quiser deixar claro
    private FormatoEvento formato;

    private Boolean certificado;
    private BigDecimal valor;

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public FormatoEvento getFormato() {
        return formato;
    }

    public void setFormato(FormatoEvento formato) {
        this.formato = formato;
    }

    public Boolean getCertificado() {
        return certificado;
    }

    public void setCertificado(Boolean certificado) {
        this.certificado = certificado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
