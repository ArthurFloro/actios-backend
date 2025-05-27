package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.EventoDetalhe;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class EventoDetalheDTO {

    private Integer idEvento;
    private String dataFim;
    private String formato;
    private Boolean certificado;
    private BigDecimal valor;

    public EventoDetalheDTO() {}

    public EventoDetalheDTO(EventoDetalhe detalhe) {
        this.idEvento = detalhe.getEvento() != null ? detalhe.getEvento().getIdEvento() : null;
        this.dataFim = detalhe.getDataFim() != null ? detalhe.getDataFim().format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
        this.formato = detalhe.getFormato() != null ? detalhe.getFormato().name() : null;
        this.certificado = detalhe.getCertificado();
        this.valor = detalhe.getValor();
    }

    // getters e setters
    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
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

