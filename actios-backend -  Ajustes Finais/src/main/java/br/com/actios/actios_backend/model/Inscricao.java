package br.com.actios.actios_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa a inscrição de um usuário em um evento.
 * <p>
 * Garante que um usuário só possa se inscrever uma vez em cada evento através
 * de uma constraint única. Armazena informações como número de inscrição e
 * data/hora da inscrição.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "inscricoes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "evento_id"})
})
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscricao")
    private Integer idInscricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @Column(name = "numero_inscricao", nullable = false, unique = true)
    private String numeroInscricao;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDateTime dataInscricao;

    /**
     * Obtém o ID único da inscrição.
     *
     * @return ID da inscrição
     */
    public Integer getIdInscricao() {
        return idInscricao;
    }

    /**
     * Define o ID único da inscrição.
     *
     * @param idInscricao ID da inscrição (deve ser positivo)
     */
    public void setIdInscricao(Integer idInscricao) {
        this.idInscricao = idInscricao;
    }

    /**
     * Obtém o usuário inscrito no evento.
     *
     * @return Instância do Usuario associado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário inscrito no evento.
     *
     * @param usuario Instância do Usuario (não pode ser nulo)
     * @throws IllegalArgumentException Se o usuário for nulo
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtém o evento relacionado à inscrição.
     *
     * @return Instância do Evento associado
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Define o evento relacionado à inscrição.
     *
     * @param evento Instância do Evento (não pode ser nulo)
     * @throws IllegalArgumentException Se o evento for nulo
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Obtém o número único da inscrição.
     *
     * @return Número da inscrição (formato livre)
     */
    public String getNumeroInscricao() {
        return numeroInscricao;
    }

    /**
     * Define o número único da inscrição.
     *
     * @param numeroInscricao Número de identificação da inscrição (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException Se o número for nulo ou vazio
     */
    public void setNumeroInscricao(String numeroInscricao) {
        this.numeroInscricao = numeroInscricao;
    }

    /**
     * Obtém a data e hora em que a inscrição foi realizada.
     *
     * @return Data/hora da inscrição
     */
    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    /**
     * Define a data e hora da inscrição.
     *
     * @param dataInscricao Data/hora da inscrição (não pode ser nula ou futura)
     * @throws IllegalArgumentException Se a data for nula ou futura
     */
    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }
}