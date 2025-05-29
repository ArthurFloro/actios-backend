package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoDetalheRepository extends JpaRepository<EventoDetalhe, Integer> {

    List<EventoDetalhe> findByFormato(FormatoEvento formato);

    List<EventoDetalhe> findByCertificado(Boolean certificado);

    List<EventoDetalhe> findByValorLessThanEqual(BigDecimal valorMax);

    List<EventoDetalhe> findByDataFimGreaterThanEqual(LocalDate data);

    Optional<EventoDetalhe> findByEvento_IdEvento(Integer idEvento);

    boolean existsByEvento(Evento evento);
}
