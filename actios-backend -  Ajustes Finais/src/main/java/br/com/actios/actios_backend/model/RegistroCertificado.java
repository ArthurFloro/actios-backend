package br.com.actios.actios_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidade que representa o registro de um certificado emitido para um usuário.
 * <p>
 * Armazena informações sobre certificados de cursos, incluindo data de emissão
 * e um código único de validação. Relaciona um usuário com um curso específico.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "registro_certificados")
public class RegistroCertificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_certificado")
    private Integer idCertificado;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "codigo_validacao", nullable = false, unique = true, length = 100)
    private String codigoValidacao;

    /**
     * Construtor padrão que inicializa a data de emissão com a data atual.
     */
    public RegistroCertificado() {
        this.dataEmissao = LocalDate.now();
    }

    /**
     * Obtém o ID único do certificado.
     *
     * @return ID do certificado
     */
    public Integer getIdCertificado() {
        return idCertificado;
    }

    /**
     * Define o ID único do certificado.
     *
     * @param idCertificado ID do certificado (deve ser positivo)
     * @throws IllegalArgumentException Se o ID for negativo
     */
    public void setIdCertificado(Integer idCertificado) {
        if (idCertificado != null && idCertificado < 0) {
            throw new IllegalArgumentException("ID do certificado deve ser positivo");
        }
        this.idCertificado = idCertificado;
    }

    /**
     * Obtém o usuário associado ao certificado.
     *
     * @return Instância do Usuario (não pode ser nulo)
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário associado ao certificado.
     *
     * @param usuario Instância do Usuario (não pode ser nulo)
     * @throws IllegalArgumentException Se o usuário for nulo
     */
    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário é obrigatório para o certificado");
        }
        this.usuario = usuario;
    }

    /**
     * Obtém o curso associado ao certificado.
     *
     * @return Instância do Curso (não pode ser nulo)
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * Define o curso associado ao certificado.
     *
     * @param curso Instância do Curso (não pode ser nulo)
     * @throws IllegalArgumentException Se o curso for nulo
     */
    public void setCurso(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("Curso é obrigatório para o certificado");
        }
        this.curso = curso;
    }

    /**
     * Obtém a data de emissão do certificado.
     *
     * @return Data de emissão (inicializada com data atual por padrão)
     */
    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Define a data de emissão do certificado.
     *
     * @param dataEmissao Data de emissão (não pode ser futura)
     * @throws IllegalArgumentException Se a data for no futuro
     */
    public void setDataEmissao(LocalDate dataEmissao) {
        if (dataEmissao != null && dataEmissao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de emissão não pode ser futura");
        }
        this.dataEmissao = dataEmissao;
    }

    /**
     * Obtém o código único de validação do certificado.
     *
     * @return Código de validação (único, até 100 caracteres)
     */
    public String getCodigoValidacao() {
        return codigoValidacao;
    }

    /**
     * Define o código único de validação do certificado.
     *
     * @param codigoValidacao Código de validação (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException Se o código for inválido
     */
    public void setCodigoValidacao(String codigoValidacao) {
        if (codigoValidacao == null || codigoValidacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de validação é obrigatório");
        }
        if (codigoValidacao.length() > 100) {
            throw new IllegalArgumentException("Código de validação não pode exceder 100 caracteres");
        }
        this.codigoValidacao = codigoValidacao;
    }
}