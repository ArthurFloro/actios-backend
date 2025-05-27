package br.com.actios.actios_backend.dto;

public class CursoDTO {
    private Integer id;
    private String nome;
    private String areaAcademica;

    public CursoDTO() {}

    public CursoDTO(Integer id, String nome, String areaAcademica) {
        this.id = id;
        this.nome = nome;
        this.areaAcademica = areaAcademica;
    }

    // Getters e setters
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
}

