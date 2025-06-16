package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link Categoria}.
 * <p>
 * Oferece operações CRUD básicas através da extensão {@link JpaRepository} e métodos
 * adicionais para consultas específicas de categorias.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    /**
     * Verifica se existe uma categoria com o nome especificado.
     *
     * @param nome Nome da categoria a ser verificada (case-sensitive)
     * @return true se uma categoria com o nome existir, false caso contrário
     * @throws IllegalArgumentException Se o nome fornecido for nulo
     */
    boolean existsByNome(String nome);

    /**
     * Busca uma categoria pelo nome exato.
     *
     * @param nome Nome da categoria a ser buscada (case-sensitive)
     * @return Um {@link Optional} contendo a categoria encontrada ou vazio se não existir
     * @throws IllegalArgumentException Se o nome fornecido for nulo
     */
    Optional<Categoria> findByNome(String nome);
}