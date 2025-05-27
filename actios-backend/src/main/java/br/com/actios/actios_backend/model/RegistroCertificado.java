package br.com.actios.actios_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "registro_certificados")
public class RegistroCertificado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_certificado")
    private Integer idCertificado;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;
    
    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;
    
    @Column(name = "codigo_validacao", nullable = false, unique = true, length = 100)
    private String codigoValidacao;

    public RegistroCertificado() {
        this.dataEmissao = LocalDate.now();
    }
    
    public Integer getIdCertificado() {
        return idCertificado;
    }

    public void setIdCertificado(Integer idCertificado) {
        this.idCertificado = idCertificado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getCodigoValidacao() {
        return codigoValidacao;
    }

    public void setCodigoValidacao(String codigoValidacao) {
        this.codigoValidacao = codigoValidacao;
    }
}
