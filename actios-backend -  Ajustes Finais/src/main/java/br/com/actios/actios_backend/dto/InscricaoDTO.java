package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.Inscricao;
import br.com.actios.actios_backend.model.EventoPalestrante;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) para representação de inscrições em eventos.
 * <p>
 * Contém informações completas sobre a inscrição, incluindo dados do usuário,
 * detalhes do evento e lista de palestrantes associados.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class InscricaoDTO {
    private Integer idInscricao;
    private String numeroInscricao;
    private String dataInscricao;
    private Integer idUsuario;
    private String nomeUsuario;
    private Integer idEvento;
    private String tituloEvento;
    private List<String> nomesPalestrantes;

    /**
     * Construtor que converte uma entidade Inscricao para DTO.
     * <p>
     * Extrai informações básicas da inscrição, dados do usuário e do evento,
     * incluindo a lista de palestrantes associados.
     *
     * @param inscricao Entidade Inscricao a ser convertida
     */
    public InscricaoDTO(Inscricao inscricao) {
        this.idInscricao = inscricao.getIdInscricao();
        this.numeroInscricao = inscricao.getNumeroInscricao();

        this.dataInscricao = inscricao.getDataInscricao() != null
                ? inscricao.getDataInscricao().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : null;

        if (inscricao.getUsuario() != null) {
            this.idUsuario = inscricao.getUsuario().getIdUsuario();
            this.nomeUsuario = inscricao.getUsuario().getNome();
        }

        if (inscricao.getEvento() != null) {
            this.idEvento = inscricao.getEvento().getIdEvento();
            this.tituloEvento = inscricao.getEvento().getTitulo();

            if (inscricao.getEvento().getEventoPalestrantes() != null) {
                this.nomesPalestrantes = inscricao.getEvento().getEventoPalestrantes()
                        .stream()
                        .map(EventoPalestrante::getPalestrante)
                        .filter(p -> p != null)
                        .map(p -> p.getNome())
                        .collect(Collectors.toList());
            }
        }
    }

    /**
     * Obtém o ID único da inscrição.
     *
     * @return ID da inscrição
     */
    public Integer getIdInscricao() { return idInscricao; }

    /**
     * Obtém o número único da inscrição.
     *
     * @return Número de identificação da inscrição
     */
    public String getNumeroInscricao() { return numeroInscricao; }

    /**
     * Obtém a data e hora da inscrição formatada.
     *
     * @return Data no formato "yyyy-MM-dd HH:mm:ss" ou null se não disponível
     */
    public String getDataInscricao() { return dataInscricao; }

    /**
     * Obtém o ID do usuário inscrito.
     *
     * @return ID do usuário ou null se não disponível
     */
    public Integer getIdUsuario() { return idUsuario; }

    /**
     * Obtém o nome do usuário inscrito.
     *
     * @return Nome do usuário ou null se não disponível
     */
    public String getNomeUsuario() { return nomeUsuario; }

    /**
     * Obtém o ID do evento relacionado.
     *
     * @return ID do evento ou null se não disponível
     */
    public Integer getIdEvento() { return idEvento; }

    /**
     * Obtém o título do evento.
     *
     * @return Título do evento ou null se não disponível
     */
    public String getTituloEvento() { return tituloEvento; }

    /**
     * Obtém a lista de nomes dos palestrantes do evento.
     *
     * @return Lista de nomes de palestrantes (pode ser vazia)
     */
    public List<String> getNomesPalestrantes() { return nomesPalestrantes; }
}