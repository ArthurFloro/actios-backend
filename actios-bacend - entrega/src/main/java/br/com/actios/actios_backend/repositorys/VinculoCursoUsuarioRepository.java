package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.VinculoCursoUsuario;
import br.com.actios.actios_backend.model.VinculoCursoUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VinculoCursoUsuarioRepository extends JpaRepository<VinculoCursoUsuario, VinculoCursoUsuarioId> {

    // Buscar cursos de um usuário específico
    List<VinculoCursoUsuario> findByUsuario(Usuario usuario);

    // Buscar usuários de um curso específico
    List<VinculoCursoUsuario> findByCurso(Curso curso);

    // Verificar se existe um vínculo específico
    boolean existsById(VinculoCursoUsuarioId id);
}
