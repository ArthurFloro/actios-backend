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
    List<RegistroCertificado> findByUsuario(Usuario usuario);

    // Buscar certificados por curso
    List<RegistroCertificado> findByCurso(Curso curso);

    // Buscar certificado por código de validação
    RegistroCertificado findByCodigoValidacao(String codigoValidacao);

    // Verificar existência por código de validação
    boolean existsByCodigoValidacao(String codigoValidacao); // ← ADICIONAR ESTA LINHA

    // Contar certificados por usuário
    long countByUsuario(Usuario usuario);

    // Contar certificados por curso
    long countByCurso(Curso curso);

    // Buscar certificados por usuário e curso
    List<RegistroCertificado> findByUsuarioAndCurso(Usuario usuario, Curso curso);
}
