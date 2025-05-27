package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.CampoObrigatorioException;
import br.com.actios.actios_backend.exceptions.DataInvalidaException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import br.com.actios.actios_backend.repositorys.EventoPalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EventoPalestranteRepository eventoPalestranteRepository;

    @Autowired
    public EventoService(EventoRepository eventoRepository, EventoPalestranteRepository eventoPalestranteRepository) {
        this.eventoRepository = eventoRepository;
        this.eventoPalestranteRepository = eventoPalestranteRepository;
    }

    public Evento cadastrar(Evento evento) throws Exception {
        if (evento.getData() == null) {
            throw new CampoObrigatorioException("Data do evento é obrigatória.");
        }
        if (evento.getData().isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Não é permitido cadastrar eventos com data passada.");
        }
        if (evento.getTitulo() == null || evento.getTitulo().isBlank()) {
            throw new CampoObrigatorioException("Título do evento é obrigatório.");
        }
        // Outros campos obrigatórios podem ser validados aqui, se necessário

        return eventoRepository.save(evento);
    }

    public Page<Evento> listarTodos(Pageable pageable) {
        return eventoRepository.findAll(pageable);
    }

    public Evento buscarPorId(Integer id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado."));
    }

    public Evento atualizar(Evento evento) throws Exception {
        if (evento.getIdEvento() == null || !eventoRepository.existsById(evento.getIdEvento())) {
            throw new RecursoNaoEncontradoException("Evento não encontrado para atualização.");
        }
        if (evento.getData() == null) {
            throw new CampoObrigatorioException("Data do evento é obrigatória.");
        }
        if (evento.getData().isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Não é permitido atualizar para data passada.");
        }
        if (evento.getTitulo() == null || evento.getTitulo().isBlank()) {
            throw new CampoObrigatorioException("Título do evento é obrigatório.");
        }

        return eventoRepository.save(evento);
    }

    public void excluir(Integer id) throws Exception {
        if (!eventoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Evento não encontrado para exclusão.");
        }
        eventoRepository.deleteById(id);
    }

    public Page<Evento> listarEventosFuturosPorNomePalestrante(String nome, Pageable pageable) {
        LocalDate hoje = LocalDate.now();
        return eventoPalestranteRepository.findEventosFuturosByNomeParcialDoPalestrante(hoje, nome, pageable);

    }

}
