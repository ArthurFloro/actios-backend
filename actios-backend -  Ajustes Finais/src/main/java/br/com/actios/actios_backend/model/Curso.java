package br.com.actios.actios_backend.model;

import br.com.actios.actios_backend.model.VinculoCursoUsuario;
import br.com.actios.actios_backend.model.RegistroCertificado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Entidade que representa um curso no sistema.
 * <p>
 * Esta classe modela os cursos oferecidos, contendo informações como nome, área acadêmica
 * e relacionamentos com usuários e certificados. Utilizada para gerenciar todas as operações
 * relacionadas a cursos na aplicação.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "cursos")
public class Curso {

    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private Set<VinculoCursoUsuario> vinculosUsuarios;

    /**
     * Obtém os vínculos de usuários com este curso.
     *
     * @return Conjunto de vínculos usuário-curso
     */
    public Set<VinculoCursoUsuario> getVinculosUsuarios() {
        return vinculosUsuarios;
    }

    /**
     * Define os vínculos de usuários com este curso.
     *
     * @param vinculosUsuarios Conjunto de vínculos usuário-curso
     */
    public void setVinculosUsuarios(Set<VinculoCursoUsuario> vinculosUsuarios) {
        this.vinculosUsuarios = vinculosUsuarios;
    }

    /**
     * Obtém a lista de usuários vinculados a este curso.
     *
     * @return Lista de usuários (pode ser vazia se não houver vínculos)
     */
    public List<Usuario> getUsuarios() {
        if (vinculosUsuarios == null) return Collections.emptyList();
        return vinculosUsuarios.stream()
                .map(vinculo -> vinculo.getUsuario())
                .collect(Collectors.toList());
    }

    /**
     * Adiciona um usuário ao curso, criando um novo vínculo.
     *
     * @param usuario Usuário a ser adicionado ao curso
     */
    public void addUser(Usuario usuario) {
        if (vinculosUsuarios == null) {
            vinculosUsuarios = new HashSet<>();
        }
        VinculoCursoUsuario vinculo = new VinculoCursoUsuario(usuario, this);
        vinculosUsuarios.add(vinculo);
    }

    /**
     * Remove um usuário do curso, eliminando o vínculo existente.
     *
     * @param usuario Usuário a ser removido do curso
     */
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

    /**
     * Construtor padrão sem parâmetros.
     */
    public Curso() {}

    /**
     * Construtor com parâmetros básicos do curso.
     *
     * @param nome Nome do curso (não pode ser nulo)
     * @param areaAcademica Área acadêmica do curso
     */
    public Curso(String nome, String areaAcademica) {
        this.nome = nome;
        this.areaAcademica = areaAcademica;
    }

    /**
     * Obtém o ID único do curso.
     *
     * @return ID do curso
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID único do curso.
     *
     * @param id ID do curso (deve ser positivo)
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém o nome do curso.
     *
     * @return Nome do curso
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do curso.
     *
     * @param nome Nome do curso (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException Se o nome for nulo ou vazio
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a área acadêmica do curso.
     *
     * @return Área acadêmica do curso
     */
    public String getAreaAcademica() {
        return areaAcademica;
    }

    /**
     * Define a área acadêmica do curso.
     *
     * @param areaAcademica Área acadêmica do curso
     */
    public void setAreaAcademica(String areaAcademica) {
        this.areaAcademica = areaAcademica;
    }

    /**
     * Obtém os certificados associados a este curso (coleção imutável).
     *
     * @return Conjunto imutável de certificados
     */
    public Set<RegistroCertificado> getCertificados() {
        return Collections.unmodifiableSet(certificados);
    }

    /**
     * Adiciona um novo certificado ao curso.
     *
     * @param certificado Certificado a ser adicionado
     */
    public void adicionarCertificado(RegistroCertificado certificado) {
        certificados.add(certificado);
        certificado.setCurso(this);
    }

    /**
     * Remove um certificado do curso.
     *
     * @param certificado Certificado a ser removido
     */
    public void removerCertificado(RegistroCertificado certificado) {
        certificados.remove(certificado);
        certificado.setCurso(null);
    }

    /**
     * Retorna uma representação em string do curso.
     *
     * @return String no formato "Nome (Área Acadêmica)"
     */
    @Override
    public String toString() {
        return nome + " (" + areaAcademica + ")";
    }
}