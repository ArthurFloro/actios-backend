package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.Participacao;
import br.com.actios.actios_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link Participacao}.
 * <p>
 * Oferece operações para gerenciamento de participações em eventos, incluindo buscas por
 * usuário, evento, status de check-in e feedback, além de contagens estatísticas.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface ParticipacaoRepository extends JpaRepository<Participacao, Integer> {

    /**
     * Busca todas as participações de um usuário específico.
     *
     * @param usuario Usuário para filtrar as participações
     * @return Lista de participações do usuário, ordenadas por data de participação
     * @throws IllegalArgumentException se o usuário for nulo
     */
    List<Participacao> findByUsuario(Usuario usuario);

    /**
     * Busca todas as participações em um evento específico.
     *
     * @param evento Evento para filtrar as participações
     * @return Lista de participações no evento, ordenadas por data de inscrição
     * @throws IllegalArgumentException se o evento for nulo
     */
    List<Participacao> findByEvento(Evento evento);

    /**
     * Verifica se um usuário já está participando de um determinado evento.
     *
     * @param usuario Usuário a ser verificado
     * @param evento Evento a ser verificado
     * @return true se o usuário já estiver participando do evento, false caso contrário
     * @throws IllegalArgumentException se usuário ou evento forem nulos
     */
    boolean existsByUsuarioAndEvento(Usuario usuario, Evento evento);

    /**
     * Busca todas as participações com check-in realizado.
     *
     * @return Lista de participações confirmadas (com check-in), ordenadas por data de check-in
     */
    List<Participacao> findByCheckinTrue();

    /**
     * Busca todas as participações que ainda não receberam feedback.
     *
     * @return Lista de participações sem feedback, ordenadas por data de participação
     */
    List<Participacao> findByFeedbackIsNull();

    /**
     * Conta o número total de participações em um evento.
     *
     * @param evento Evento para contagem
     * @return Quantidade total de participações no evento
     * @throws IllegalArgumentException se o evento for nulo
     */
    long countByEvento(Evento evento);

    /**
     * Conta o número de participações confirmadas (com check-in) em um evento.
     *
     * @param evento Evento para contagem
     * @return Quantidade de participações com check-in realizado no evento
     * @throws IllegalArgumentException se o evento for nulo
     */
    long countByEventoAndCheckinTrue(Evento evento);
}