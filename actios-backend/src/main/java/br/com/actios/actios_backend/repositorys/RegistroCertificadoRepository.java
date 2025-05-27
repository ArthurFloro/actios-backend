package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.model.RegistroCertificado;
import br.com.actios.actios_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroCertificadoRepository extends JpaRepository<RegistroCertificado, Integer> {
    
    // Buscar certificados por usuário
    public List<RegistroCertificado> findByUsuario(Usuario usuario);
    
    // Buscar certificados por curso
    public List<RegistroCertificado> findByCurso(Curso curso);
    
    // Buscar certificado por código de validação
    public RegistroCertificado findByCodigoValidacao(String codigoValidacao);
    
    // Contar certificados por usuário
    public long countByUsuario(Usuario usuario);
    
    // Contar certificados por curso
    public long countByCurso(Curso curso);
    
    // Buscar certificados por usuário e curso
    public List<RegistroCertificado> findByUsuarioAndCurso(Usuario usuario, Curso curso);
}
