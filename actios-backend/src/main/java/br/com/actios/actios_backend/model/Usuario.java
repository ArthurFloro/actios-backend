package br.com.actios.actios_backend.model;

import br.com.actios.actios_backend.enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // << aqui a alteração principal
    private Integer idUsuario;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "curso")
    private String curso;

    @ManyToOne
    @JoinColumn(name = "faculdade_id")
    private Faculdade faculdade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoUsuario tipo = TipoUsuario.ALUNO;

    @Column(name = "data_cadastro", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    // demais getters e setters...

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Faculdade getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(Faculdade faculdade) {
        this.faculdade = faculdade;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
