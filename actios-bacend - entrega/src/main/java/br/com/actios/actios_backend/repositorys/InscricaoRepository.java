package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Inscricao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {
    Optional<Inscricao> findByUsuarioAndEvento(Usuario usuario, Evento evento);
    long countByEvento(Evento evento);
}
