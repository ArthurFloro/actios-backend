package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoDetalhe;
import br.com.actios.actios_backend.model.EventoPalestrante;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) para representação completa de eventos.
 * <p>
 * Contém informações básicas do evento, detalhes adicionais e lista de palestrantes.
 * Utilizado para transferência segura de dados entre camadas da aplicação.
 *
 * @author Equipe Actios
 * @version 1.1
 * @since 2023-08-30
 */
public class EventoDTO {

    private Integer idEvento;
    private String titulo;
    private String descricao;
    private LocalDate data;
    private String horario;
    private String local;
    private Integer faculdadeId;
    private String nomeFaculdade;
    private Integer categoriaId;
    private String nomeCategoria;
    private List<String> nomesPalestrantes;
    private Boolean certificado;
    private BigDecimal valor;
    private String formato;
    private List<Integer> palestrantesIds;

    /**
     * Construtor padrão necessário para desserialização JSON.
     */
    public EventoDTO() {
    }

    /**
     * Construtor que converte uma entidade Evento para DTO.
     * <p>
     * Extrai informações básicas, detalhes adicionais e lista de palestrantes.
     *
     * @param evento Entidade Evento a ser convertida
     */
    public EventoDTO(Evento evento) {
        this.idEvento = evento.getIdEvento();
        this.titulo = evento.getTitulo();
        this.descricao = evento.getDescricao();
        this.data = evento.getData();
        this.horario = evento.getHorario();
        this.local = evento.getLocal();

        this.faculdadeId = evento.getFaculdade() != null ? evento.getFaculdade().getIdFaculdade() : null;
        this.nomeFaculdade = evento.getFaculdade() != null ? evento.getFaculdade().getNome() : null;

        this.categoriaId = evento.getCategoria() != null ? evento.getCategoria().getIdCategoria() : null;
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
            this.formato = detalhes.getFormato() != null ? detalhes.getFormato().name() : null;
        }
    }

    /**
     * Obtém o ID único do evento.
     *
     * @return ID do evento
     */
    public Integer getIdEvento() {
        return idEvento;
    }

    /**
     * Define o ID do evento.
     *
     * @param idEvento ID único do evento
     */
    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    /**
     * Obtém o título do evento.
     *
     * @return Título do evento
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o título do evento.
     *
     * @param titulo Título do evento
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtém a descrição detalhada do evento.
     *
     * @return Descrição do evento
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do evento.
     *
     * @param descricao Descrição detalhada do evento
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a data principal do evento.
     *
     * @return Data do evento
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Define a data principal do evento.
     *
     * @param data Data do evento
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Obtém o horário do evento.
     *
     * @return Horário no formato de string
     */
    public String getHorario() {
        return horario;
    }

    /**
     * Define o horário do evento.
     *
     * @param horario Horário no formato de string
     */
    public void setHorario(String horario) {
        this.horario = horario;
    }

    /**
     * Obtém o local do evento.
     *
     * @return Local do evento
     */
    public String getLocal() {
        return local;
    }

    /**
     * Define o local do evento.
     *
     * @param local Local do evento
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * Obtém o ID da faculdade associada ao evento.
     *
     * @return ID da faculdade ou null se não associado
     */
    public Integer getFaculdadeId() {
        return faculdadeId;
    }

    /**
     * Define o ID da faculdade associada ao evento.
     *
     * @param faculdadeId ID da faculdade
     */
    public void setFaculdadeId(Integer faculdadeId) {
        this.faculdadeId = faculdadeId;
    }

    /**
     * Obtém o nome da faculdade organizadora.
     *
     * @return Nome da faculdade ou null se não associada
     */
    public String getNomeFaculdade() {
        return nomeFaculdade;
    }

    /**
     * Define o nome da faculdade organizadora.
     *
     * @param nomeFaculdade Nome da faculdade
     */
    public void setNomeFaculdade(String nomeFaculdade) {
        this.nomeFaculdade = nomeFaculdade;
    }

    /**
     * Obtém o ID da categoria associada ao evento.
     *
     * @return ID da categoria ou null se não categorizado
     */
    public Integer getCategoriaId() {
        return categoriaId;
    }

    /**
     * Define o ID da categoria associada ao evento.
     *
     * @param categoriaId ID da categoria
     */
    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    /**
     * Obtém o nome da categoria do evento.
     *
     * @return Nome da categoria ou null se não categorizado
     */
    public String getNomeCategoria() {
        return nomeCategoria;
    }

    /**
     * Define o nome da categoria do evento.
     *
     * @param nomeCategoria Nome da categoria
     */
    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    /**
     * Obtém a lista de nomes dos palestrantes.
     *
     * @return Lista de nomes de palestrantes (pode ser vazia)
     */
    public List<String> getNomesPalestrantes() {
        return nomesPalestrantes;
    }

    /**
     * Define a lista de nomes dos palestrantes.
     *
     * @param nomesPalestrantes Lista de nomes de palestrantes
     */
    public void setNomesPalestrantes(List<String> nomesPalestrantes) {
        this.nomesPalestrantes = nomesPalestrantes;
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

    /**
     * Obtém o formato do evento como string.
     *
     * @return Nome do formato (online, presencial, hibrido) ou null
     */
    public String getFormato() {
        return formato;
    }

    /**
     * Obtém a lista de IDs dos palestrantes.
     *
     * @return Lista de IDs de palestrantes
     */
    public List<Integer> getPalestrantesIds() {
        return palestrantesIds;
    }

    /**
     * Define a lista de IDs dos palestrantes.
     *
     * @param palestrantesIds Lista de IDs de palestrantes
     */
    public void setPalestrantesIds(List<Integer> palestrantesIds) {
        this.palestrantesIds = palestrantesIds;
    }

    /**
     * Define o formato do evento.
     *
     * @param formato Nome do formato (online, presencial, hibrido)
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }
}
