package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoPalestrante;
import br.com.actios.actios_backend.model.EventoPalestranteId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EventoPalestranteRepository extends JpaRepository<EventoPalestrante, EventoPalestranteId> {

    @Query("SELECT ep.evento FROM EventoPalestrante ep " +
            "WHERE ep.evento.data >= :agora " +
            "AND LOWER(ep.palestrante.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Evento> findEventosFuturosByNomeParcialDoPalestrante(LocalDate agora, String nome, Pageable pageable);

}
