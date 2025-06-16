package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Inscricao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link Inscricao}.
 * <p>
 * Oferece operações para gerenciamento de inscrições de usuários em eventos, incluindo
 * buscas por usuário e evento, e contagem de inscrições por evento.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {

    /**
     * Busca uma inscrição específica combinando usuário e evento.
     *
     * @param usuario Usuário que realizou a inscrição
     * @param evento Evento para o qual o usuário se inscreveu
     * @return {@link Optional} contendo a inscrição se encontrada, ou vazio caso contrário
     * @throws IllegalArgumentException se usuário ou evento forem nulos
     */
    Optional<Inscricao> findByUsuarioAndEvento(Usuario usuario, Evento evento);

    /**
     * Verifica se existe inscrição para um usuário e evento específicos.
     *
     * @param usuario Usuário que realizou a inscrição
     * @param evento Evento para o qual o usuário se inscreveu
     * @return true se existir inscrição, false caso contrário
     * @throws IllegalArgumentException se usuário ou evento forem nulos
     */
    boolean existsByUsuarioAndEvento(Usuario usuario, Evento evento);

    /**
     * Conta o número total de inscrições para um determinado evento.
     *
     * @param evento Evento para contagem de inscrições
     * @return Quantidade de inscrições para o evento
     * @throws IllegalArgumentException se o evento for nulo
     */
    long countByEvento(Evento evento);

    /**
     * Lista todas as inscrições ordenadas por data de inscrição em ordem decrescente.
     *
     * @return Lista de inscrições ordenadas por data
     */
    List<Inscricao> findAllByOrderByDataInscricaoDesc();
}