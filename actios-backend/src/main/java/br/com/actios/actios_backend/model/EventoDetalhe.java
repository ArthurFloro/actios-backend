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
    private Integer idEvento;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Convert(converter = FormatoEventoConverter.class)
    @Column(name = "formato")
    private FormatoEvento formato;

    @Column(name = "certificado")
    private Boolean certificado;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
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

