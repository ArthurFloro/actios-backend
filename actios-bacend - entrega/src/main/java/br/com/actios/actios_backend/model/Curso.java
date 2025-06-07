package br.com.actios.actios_backend.model;

import br.com.actios.actios_backend.model.VinculoCursoUsuario;
import br.com.actios.actios_backend.model.RegistroCertificado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "cursos")
public class Curso {

    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private Set<VinculoCursoUsuario> vinculosUsuarios;

    public Set<VinculoCursoUsuario> getVinculosUsuarios() {
        return vinculosUsuarios;
    }

    public void setVinculosUsuarios(Set<VinculoCursoUsuario> vinculosUsuarios) {
        this.vinculosUsuarios = vinculosUsuarios;
    }

    public List<Usuario> getUsuarios() {
        if (vinculosUsuarios == null) return Collections.emptyList();
        return vinculosUsuarios.stream()
                .map(vinculo -> vinculo.getUsuario())
                .collect(Collectors.toList());
    }

    public void addUser(Usuario usuario) {
        if (vinculosUsuarios == null) {
            vinculosUsuarios = new HashSet<>();
        }
        VinculoCursoUsuario vinculo = new VinculoCursoUsuario(usuario, this);
        vinculosUsuarios.add(vinculo);
    }

    public void removeUser(Usuario usuario) {
        if (vinculosUsuarios != null) {
            vinculosUsuarios.removeIf(vinculo -> vinculo.getUsuario().equals(usuario));
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "area_academica", length = 100)
    private String areaAcademica;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RegistroCertificado> certificados = new HashSet<>();

    // Construtores
    public Curso() {}

    public Curso(String nome, String areaAcademica) {
        this.nome = nome;
        this.areaAcademica = areaAcademica;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAreaAcademica() {
        return areaAcademica;
    }

    public void setAreaAcademica(String areaAcademica) {
        this.areaAcademica = areaAcademica;
    }

    public Set<RegistroCertificado> getCertificados() {
        return Collections.unmodifiableSet(certificados);
    }

    public void adicionarCertificado(RegistroCertificado certificado) {
        certificados.add(certificado);
        certificado.setCurso(this);
    }

    public void removerCertificado(RegistroCertificado certificado) {
        certificados.remove(certificado);
        certificado.setCurso(null);
    }

    // toString para facilitar debug e logs
    @Override
    public String toString() {
        return nome + " (" + areaAcademica + ")";
    }
}