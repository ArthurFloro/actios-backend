package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.EventoDetalhe;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

/**
 * Data Transfer Object (DTO) para informações detalhadas de eventos.
 * <p>
 * Representa os detalhes adicionais de um evento, incluindo data final, formato,
 * disponibilidade de certificado e valor. Utilizado para transferência segura de dados
 * entre camadas da aplicação.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class EventoDetalheDTO {

    private Integer idEvento;
    private String dataFim;
    private String formato;
    private Boolean certificado;
    private BigDecimal valor;

    /**
     * Construtor padrão sem argumentos.
     */
    public EventoDetalheDTO() {}

    /**
     * Construtor que converte uma entidade EventoDetalhe para DTO.
     *
     * @param detalhe Entidade EventoDetalhe a ser convertida
     */
    public EventoDetalheDTO(EventoDetalhe detalhe) {
        this.idEvento = detalhe.getEvento() != null ? detalhe.getEvento().getIdEvento() : null;
        this.dataFim = detalhe.getDataFim() != null ? detalhe.getDataFim().format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
        this.formato = detalhe.getFormato() != null ? detalhe.getFormato().name() : null;
        this.certificado = detalhe.getCertificado();
        this.valor = detalhe.getValor();
    }

    /**
     * Obtém o ID do evento relacionado.
     *
     * @return ID do evento ou null se não existir relação
     */
    public Integer getIdEvento() {
        return idEvento;
    }

    /**
     * Define o ID do evento relacionado.
     *
     * @param idEvento ID do evento
     */
    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    /**
     * Obtém a data final do evento no formato ISO (yyyy-MM-dd).
     *
     * @return Data formatada como string ou null se não definida
     */
    public String getDataFim() {
        return dataFim;
    }

    /**
     * Define a data final do evento.
     *
     * @param dataFim Data no formato ISO (yyyy-MM-dd)
     */
    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Obtém o formato do evento como string.
     *
     * @return Nome do formato (online, presencial, hibrido) ou null
     */
    public String getFormato() {
        return formato;
    }

    /**
     * Define o formato do evento.
     *
     * @param formato Nome do formato (online, presencial, hibrido)
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * Verifica se o evento emite certificado.
     *
     * @return true se emite certificado, false caso contrário
     */
    public Boolean getCertificado() {
        return certificado;
    }

    /**
     * Define se o evento emite certificado.
     *
     * @param certificado true para eventos com certificado
     */
    public void setCertificado(Boolean certificado) {
        this.certificado = certificado;
    }

    /**
     * Obtém o valor do evento.
     *
     * @return Valor como BigDecimal ou null se gratuito
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define o valor do evento.
     *
     * @param valor Valor como BigDecimal
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}