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

@Repository
public interface FeedbackEventoRepository extends JpaRepository<FeedbackEvento, Integer> {

    // Buscar feedbacks por usuário
    List<FeedbackEvento> findByUsuario(Usuario usuario);

    // Buscar feedbacks por evento
    List<FeedbackEvento> findByEvento(Evento evento);

    // Buscar feedback específico por usuário e evento (retorna Optional para usar orElseThrow)
    Optional<FeedbackEvento> findByUsuarioAndEvento(Usuario usuario, Evento evento);

    // Verificar se existe feedback de um usuário para um evento
    boolean existsByUsuarioAndEvento(Usuario usuario, Evento evento);

    // Contar quantidade de feedbacks para um evento
    long countByEvento(Evento evento);

    // Contar quantidade de feedbacks com uma nota específica
    long countByNota(Integer nota);

    // Calcular média de nota por evento (pode retornar null se não houver feedbacks)
    @Query("SELECT AVG(f.nota) FROM FeedbackEvento f WHERE f.evento = :evento")
    Double calcularMediaNotaPorEvento(@Param("evento") Evento evento);
}
