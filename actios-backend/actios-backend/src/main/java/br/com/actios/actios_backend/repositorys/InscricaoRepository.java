package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Inscricao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {
    Optional<Inscricao> findByUsuarioAndEvento(Usuario usuario, Evento evento);
    List<Inscricao> findByUsuarioId(Integer idUsuario);
    List<Inscricao> findByEventoId(Integer idEvento);
    boolean existsByNumeroInscricao(String numeroInscricao);
    long countByEvento(Evento evento);
}
