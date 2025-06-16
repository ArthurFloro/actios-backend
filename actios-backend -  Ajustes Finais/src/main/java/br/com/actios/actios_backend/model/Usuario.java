package br.com.actios.actios_backend.model;

import br.com.actios.actios_backend.enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Entidade que representa um usuário do sistema.
 * <p>
 * Armazena informações pessoais, credenciais e relacionamentos com outras entidades
 * como notificações, certificados, feedbacks e cursos. Gerencia automaticamente
 * a data de cadastro e tipo de usuário padrão.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Notificacao> notificacoes = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RegistroCertificado> certificados = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FeedbackEvento> feedbacks = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<VinculoCursoUsuario> vinculosCursos = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @Column(name = "curso", length = 100)
    private String curso;

    @ManyToOne
    @JoinColumn(name = "faculdade_id")
    private Faculdade faculdade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoUsuario tipo = TipoUsuario.ALUNO;

    @Column(name = "data_cadastro", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    /**
     * Construtor padrão que inicializa o tipo como ALUNO.
     */
    public Usuario() {
        this.tipo = TipoUsuario.ALUNO;
    }

    /**
     * Obtém o ID único do usuário.
     *
     * @return ID do usuário
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Define o ID único do usuário.
     *
     * @param idUsuario ID do usuário (deve ser positivo)
     * @throws IllegalArgumentException Se o ID for negativo
     */
    public void setIdUsuario(Integer idUsuario) {
        if (idUsuario != null && idUsuario < 0) {
            throw new IllegalArgumentException("ID do usuário deve ser positivo");
        }
        this.idUsuario = idUsuario;
    }

    /**
     * Obtém o nome completo do usuário.
     *
     * @return Nome do usuário
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome completo do usuário.
     *
     * @param nome Nome do usuário (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException Se o nome for inválido
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (nome.length() > 100) {
            throw new IllegalArgumentException("Nome não pode exceder 100 caracteres");
        }
        this.nome = nome;
    }

    /**
     * Obtém o email do usuário.
     *
     * @return Email único do usuário
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do usuário.
     *
     * @param email Email do usuário (deve ser único e válido)
     * @throws IllegalArgumentException Se o email for inválido
     */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        if (email.length() > 100) {
            throw new IllegalArgumentException("Email não pode exceder 100 caracteres");
        }
        this.email = email;
    }

    /**
     * Obtém a senha criptografada do usuário.
     *
     * @return Senha criptografada
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário (deve ser criptografada antes de armazenar).
     *
     * @param senha Senha criptografada (não pode ser nula ou vazia)
     * @throws IllegalArgumentException Se a senha for inválida
     */
    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        if (senha.length() > 255) {
            throw new IllegalArgumentException("Senha não pode exceder 255 caracteres");
        }
        this.senha = senha;
    }

    /**
     * Obtém o curso do usuário.
     *
     * @return Nome do curso ou null se não definido
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Define o curso do usuário.
     *
     * @param curso Nome do curso (até 100 caracteres)
     */
    public void setCurso(String curso) {
        if (curso != null && curso.length() > 100) {
            throw new IllegalArgumentException("Curso não pode exceder 100 caracteres");
        }
        this.curso = curso;
    }

    /**
     * Obtém a faculdade associada ao usuário.
     *
     * @return Instância de Faculdade ou null se não associado
     */
    public Faculdade getFaculdade() {
        return faculdade;
    }

    /**
     * Define a faculdade associada ao usuário.
     *
     * @param faculdade Instância de Faculdade
     */
    public void setFaculdade(Faculdade faculdade) {
        this.faculdade = faculdade;
    }

    /**
     * Obtém o tipo de usuário (ALUNO por padrão).
     *
     * @return TipoUsuario enum
     */
    public TipoUsuario getTipo() {
        return tipo;
    }

    /**
     * Define o tipo de usuário.
     *
     * @param tipo TipoUsuario enum (não pode ser nulo)
     * @throws IllegalArgumentException Se o tipo for nulo
     */
    public void setTipo(TipoUsuario tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de usuário é obrigatório");
        }
        this.tipo = tipo;
    }

    /**
     * Obtém a data de cadastro do usuário.
     * <p>
     * Este campo é definido automaticamente e não pode ser modificado.
     *
     * @return Data/hora do cadastro
     */
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    /**
     * Define a data de cadastro (usado apenas para carga de dados).
     *
     * @param dataCadastro Data/hora do cadastro
     */
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    // Métodos para gerenciar relacionamentos

    /**
     * Obtém as notificações do usuário (coleção imutável).
     *
     * @return Conjunto de Notificacao
     */
    public Set<Notificacao> getNotificacoes() {
        return Collections.unmodifiableSet(notificacoes);
    }

    /**
     * Adiciona uma notificação ao usuário.
     *
     * @param notificacao Notificação a ser adicionada
     */
    public void addNotificacao(Notificacao notificacao) {
        notificacoes.add(notificacao);
        notificacao.setUsuario(this);
    }

    /**
     * Obtém os certificados do usuário (coleção imutável).
     *
     * @return Conjunto de RegistroCertificado
     */
    public Set<RegistroCertificado> getCertificados() {
        return Collections.unmodifiableSet(certificados);
    }

    /**
     * Adiciona um certificado ao usuário.
     *
     * @param certificado Certificado a ser adicionado
     */
    public void addCertificado(RegistroCertificado certificado) {
        certificados.add(certificado);
        certificado.setUsuario(this);
    }

    /**
     * Obtém os feedbacks do usuário (coleção imutável).
     *
     * @return Conjunto de FeedbackEvento
     */
    public Set<FeedbackEvento> getFeedbacks() {
        return Collections.unmodifiableSet(feedbacks);
    }

    /**
     * Adiciona um feedback ao usuário.
     *
     * @param feedback Feedback a ser adicionado
     */
    public void addFeedback(FeedbackEvento feedback) {
        feedbacks.add(feedback);
        feedback.setUsuario(this);
    }

    /**
     * Obtém os vínculos de cursos do usuário (coleção imutável).
     *
     * @return Conjunto de VinculoCursoUsuario
     */
    public Set<VinculoCursoUsuario> getVinculosCursos() {
        return Collections.unmodifiableSet(vinculosCursos);
    }
}