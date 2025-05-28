package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoDetalhe;
import br.com.actios.actios_backend.model.EventoPalestrante;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EventoDTO {

    private Integer idEvento;
    private String titulo;
    private String descricao;
    private LocalDate data;
    private String nomeFaculdade;
    private String nomeCategoria;
    private List<String> nomesPalestrantes;

    private Boolean certificado;      // novo campo
    private BigDecimal valor;         // novo campo

    public EventoDTO(Evento evento) {
        this.idEvento = evento.getIdEvento();
        this.titulo = evento.getTitulo();
        this.descricao = evento.getDescricao();
        this.data = evento.getData();
        this.nomeFaculdade = evento.getFaculdade() != null ? evento.getFaculdade().getNome() : null;
        this.nomeCategoria = evento.getCategoria() != null ? evento.getCategoria().getNome() : null;

        this.nomesPalestrantes = evento.getEventoPalestrantes() != null
                ? evento.getEventoPalestrantes().stream()
                .map(EventoPalestrante::getPalestrante)
                .map(p -> p.getNome())
                .collect(Collectors.toList())
                : List.of();

        EventoDetalhe detalhes = evento.getDetalhes();
        if (detalhes != null) {
            this.certificado = detalhes.getCertificado();
            this.valor = detalhes.getValor();
        }
    }

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

    public String getNomeFaculdade() {
        return nomeFaculdade;
    }

    public void setNomeFaculdade(String nomeFaculdade) {
        this.nomeFaculdade = nomeFaculdade;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public List<String> getNomesPalestrantes() {
        return nomesPalestrantes;
    }

    public void setNomesPalestrantes(List<String> nomesPalestrantes) {
        this.nomesPalestrantes = nomesPalestrantes;
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
