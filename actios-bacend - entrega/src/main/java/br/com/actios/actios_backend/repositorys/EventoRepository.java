package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
}
