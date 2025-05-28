package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Faculdade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaculdadeRepository extends JpaRepository<Faculdade, Integer> {

    // Verifica se já existe uma faculdade com o mesmo nome
    boolean existsByNome(String nome);

    // Busca uma faculdade com o mesmo nome mas com ID diferente (útil para atualização)
    Optional<Faculdade> findByNomeAndIdFaculdadeNot(String nome, Integer idFaculdade);
}
