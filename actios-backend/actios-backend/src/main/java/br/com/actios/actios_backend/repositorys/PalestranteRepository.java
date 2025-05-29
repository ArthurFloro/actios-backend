package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Palestrante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PalestranteRepository extends JpaRepository<Palestrante, Integer> {
    boolean existsByEmail(String email);
    Optional<Palestrante> findByEmail(String email);
}