package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.Inscricao;
import br.com.actios.actios_backend.model.EventoPalestrante;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class InscricaoDTO {
    private Integer idInscricao;
    private String numeroInscricao;
    private String dataInscricao;
    private Integer idUsuario;
    private String nomeUsuario;
    private Integer idEvento;
    private String tituloEvento;
    private List<String> nomesPalestrantes;  // lista de nomes dos palestrantes

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

            // Pega a lista de nomes dos palestrantes do evento
            if (inscricao.getEvento().getEventoPalestrantes() != null) {
                this.nomesPalestrantes = inscricao.getEvento().getEventoPalestrantes()
                        .stream()
                        .map(EventoPalestrante::getPalestrante)   // pega o palestrante
                        .filter(p -> p != null)
                        .map(p -> p.getNome())                    // pega o nome do palestrante
                        .collect(Collectors.toList());
            }
        }
    }

    // Getters
    public Integer getIdInscricao() { return idInscricao; }
    public String getNumeroInscricao() { return numeroInscricao; }
    public String getDataInscricao() { return dataInscricao; }
    public Integer getIdUsuario() { return idUsuario; }
    public String getNomeUsuario() { return nomeUsuario; }
    public Integer getIdEvento() { return idEvento; }
    public String getTituloEvento() { return tituloEvento; }
    public List<String> getNomesPalestrantes() { return nomesPalestrantes; }
}
