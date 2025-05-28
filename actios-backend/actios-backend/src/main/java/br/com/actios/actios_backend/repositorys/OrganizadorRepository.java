package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.model.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Integer> {
    
    // Buscar por email (único)
    public Optional<Organizador> findByEmail(String email);
    
    // Buscar por nome
    public List<Organizador> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar por faculdade
    public List<Organizador> findByFaculdade(Faculdade faculdade);
    
    // Verificar existência por email
    public boolean existsByEmail(String email);
    
    // Contar organizadores por faculdade
    public long countByFaculdade(Faculdade faculdade);
}
