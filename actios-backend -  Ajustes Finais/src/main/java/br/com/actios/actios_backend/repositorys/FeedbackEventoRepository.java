package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.FeedbackEvento;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link FeedbackEvento}.
 * <p>
 * Oferece operações para gerenciamento de feedbacks de eventos, incluindo buscas por usuário,
 * evento, cálculos de média e contagem de avaliações.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface FeedbackEventoRepository extends JpaRepository<FeedbackEvento, Integer> {

    /**
     * Busca todos os feedbacks associados a um usuário específico.
     *
     * @param usuario Usuário para filtrar os feedbacks
     * @return Lista de feedbacks do usuário
     * @throws IllegalArgumentException se o usuário for nulo
     */
    List<FeedbackEvento> findByUsuario(Usuario usuario);

    /**
     * Busca todos os feedbacks associados a um evento específico.
     *
     * @param evento Evento para filtrar os feedbacks
     * @return Lista de feedbacks do evento
     * @throws IllegalArgumentException se o evento for nulo
     */
    List<FeedbackEvento> findByEvento(Evento evento);

    /**
     * Verifica se existe feedback para um usuário e evento específicos.
     *
     * @param usuario Usuário que fez o feedback
     * @param evento Evento que recebeu o feedback
     * @return true se existir feedback, false caso contrário
     * @throws IllegalArgumentException se usuário ou evento forem nulos
     */
    boolean existsByUsuarioAndEvento(Usuario usuario, Evento evento);

    /**
     * Busca um feedback específico combinando usuário e evento.
     *
     * @param usuario Usuário que fez o feedback
     * @param evento Evento que recebeu o feedback
     * @return Optional contendo o feedback correspondente ou vazio se não encontrado
     * @throws IllegalArgumentException se usuário ou evento forem nulos
     */
    Optional<FeedbackEvento> findByUsuarioAndEvento(Usuario usuario, Evento evento);

    /**
     * Conta a quantidade de feedbacks recebidos por um evento.
     *
     * @param evento Evento para contagem
     * @return Número total de feedbacks para o evento
     * @throws IllegalArgumentException se o evento for nulo
     */
    long countByEvento(Evento evento);

    /**
     * Conta a quantidade de feedbacks com uma nota específica.
     *
     * @param nota Nota para filtrar (geralmente entre 1-5)
     * @return Número de feedbacks com a nota informada
     * @throws IllegalArgumentException se a nota for nula
     */
    long countByNota(Integer nota);

    /**
     * Calcula a média das notas dos feedbacks para um evento específico.
     *
     * @param evento Evento para cálculo da média
     * @return Média das notas ou null se não houver feedbacks
     * @throws IllegalArgumentException se o evento for nulo
     */
    @Query("SELECT AVG(f.nota) FROM FeedbackEvento f WHERE f.evento = :evento")
    Double calcularMediaNotaPorEvento(@Param("evento") Evento evento);
}