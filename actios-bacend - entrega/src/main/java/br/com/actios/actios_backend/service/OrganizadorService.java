package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Organizador;
import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.repositorys.OrganizadorRepository;
import br.com.actios.actios_backend.repositorys.FaculdadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizadorService {
    
    @Autowired
    private OrganizadorRepository organizadorRepository;
    
    @Autowired
    private FaculdadeRepository faculdadeRepository;

    @Transactional
    public Organizador criarOrganizador(String nome, String email, Integer idFaculdade) {
        if (organizadorRepository.existsByEmail(email)) {
            throw new RecursoExistenteException("Email já cadastrado para outro organizador");
        }
        
        Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada"));
        
        Organizador organizador = new Organizador();
        organizador.setNome(nome);
        organizador.setEmail(email);
        organizador.setFaculdade(faculdade);
        
        return organizadorRepository.save(organizador);
    }

    @Transactional
    public Organizador atualizarOrganizador(Integer idOrganizador, String nome, String email, Integer idFaculdade) {
        Organizador organizador = organizadorRepository.findById(idOrganizador)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Organizador não encontrado"));

        if (email != null && !email.equals(organizador.getEmail()) &&
                organizadorRepository.existsByEmail(email)) {
            throw new RecursoExistenteException("Email já cadastrado para outro organizador");
        }

        if (nome != null) {
            organizador.setNome(nome);
        }

        if (email != null) {
            organizador.setEmail(email);
        }

        if (idFaculdade != null) {
            Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada"));
            organizador.setFaculdade(faculdade);
        }

        return organizadorRepository.save(organizador);
    }


    public Optional<Organizador> buscarPorEmail(String email) {
        return organizadorRepository.findByEmail(email);
    }

    public List<Organizador> buscarPorNome(String nome) {
        return organizadorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Organizador> listarPorFaculdade(Integer idFaculdade) {
        Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada"));
        
        return organizadorRepository.findByFaculdade(faculdade);
    }

    public long contarOrganizadoresPorFaculdade(Integer idFaculdade) {
        Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada"));
        
        return organizadorRepository.countByFaculdade(faculdade);
    }
}
