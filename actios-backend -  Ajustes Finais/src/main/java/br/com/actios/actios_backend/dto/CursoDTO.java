package br.com.actios.actios_backend.dto;

/**
 * Data Transfer Object (DTO) para representação de cursos.
 * <p>
 * Utilizado para transferir dados de cursos entre as camadas da aplicação,
 * evitando expor a entidade completa do domínio.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class CursoDTO {
    private Integer id;
    private String nome;
    private String areaAcademica;

    /**
     * Construtor padrão sem argumentos.
     */
    public CursoDTO() {}

    /**
     * Construtor com todos os campos.
     *
     * @param id Identificador único do curso
     * @param nome Nome completo do curso
     * @param areaAcademica Área acadêmica do curso
     */
    public CursoDTO(Integer id, String nome, String areaAcademica) {
        this.id = id;
        this.nome = nome;
        this.areaAcademica = areaAcademica;
    }

    /**
     * Obtém o ID do curso.
     *
     * @return ID do curso
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do curso.
     *
     * @param id ID do curso
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
     * @param nome Nome do curso
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
}