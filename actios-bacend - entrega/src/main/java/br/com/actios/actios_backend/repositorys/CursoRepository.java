package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    // Verifica se já existe um curso com o nome informado
    boolean existsByNome(String nome);

    // Busca cursos por nome (contendo, ignorando caixa)
    List<Curso> findByNomeContainingIgnoreCase(String nome);

    // Busca cursos por área acadêmica
    List<Curso> findByAreaAcademicaIgnoreCase(String areaAcademica);
}