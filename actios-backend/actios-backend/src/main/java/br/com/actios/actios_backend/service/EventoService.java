package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.*;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import br.com.actios.actios_backend.repositorys.EventoPalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EventoPalestranteRepository eventoPalestranteRepository;

    @Autowired
    public EventoService(EventoRepository eventoRepository, EventoPalestranteRepository eventoPalestranteRepository) {
        this.eventoRepository = eventoRepository;
        this.eventoPalestranteRepository = eventoPalestranteRepository;
    }

    @Transactional
    public Evento cadastrar(Evento evento) {
        validarEvento(evento);

        try {
            if (eventoRepository.existsByTitulo(evento.getTitulo())) {
                throw new RecursoExistenteException("Já existe um evento com este título");
            }

            return eventoRepository.save(evento);
        } catch (DataIntegrityViolationException e) {
            throw new OperacaoNaoPermitidaException("Erro ao cadastrar evento: " + e.getMessage());
        }
    }

    public Page<Evento> listarTodos(Pageable pageable) {
        Page<Evento> eventos = eventoRepository.findAll(pageable);
        if (eventos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum evento encontrado");
        }
        return eventos;
    }

    public Evento buscarPorId(Integer id) {
        if (id == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }

        return eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
    }

    @Transactional
    public Evento atualizar(Evento evento) {
        validarEvento(evento);

        return eventoRepository.findById(evento.getIdEvento())
                .map(eventoExistente -> {
                    if (!Objects.equals(eventoExistente.getTitulo(), evento.getTitulo())) {
                        if (eventoRepository.existsByTitulo(evento.getTitulo())) {
                            throw new RecursoExistenteException("Já existe um evento com este título");
                        }
                    }

                    eventoExistente.setTitulo(evento.getTitulo());
                    eventoExistente.setDescricao(evento.getDescricao());
                    eventoExistente.setData(evento.getData());
                    eventoExistente.setLocal(evento.getLocal());
                    // Atualize outros campos conforme necessário

                    try {
                        return eventoRepository.save(eventoExistente);
                    } catch (DataIntegrityViolationException e) {
                        throw new OperacaoNaoPermitidaException("Erro ao atualizar evento: " + e.getMessage());
                    }
                })
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado para atualização"));
    }

    @Transactional
    public void excluir(Integer id) {
        if (id == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado para exclusão"));

        try {
            eventoRepository.delete(evento);
        } catch (DataIntegrityViolationException e) {
            throw new OperacaoNaoPermitidaException("Não é possível excluir o evento pois está associado a outras entidades");
        }
    }

    public Page<Evento> listarEventosFuturosPorNomePalestrante(String nome, Pageable pageable) {
        if (nome == null || nome.isBlank()) {
            throw new CampoObrigatorioException("Nome do palestrante é obrigatório");
        }

        LocalDate hoje = LocalDate.now();
        Page<Evento> eventos = eventoPalestranteRepository.findEventosFuturosByNomeParcialDoPalestrante(hoje, nome, pageable);

        if (eventos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum evento futuro encontrado para o palestrante: " + nome);
        }

        return eventos;
    }

    private void validarEvento(Evento evento) {
        if (evento == null) {
            throw new CampoObrigatorioException("Evento é obrigatório");
        }
        if (evento.getTitulo() == null || evento.getTitulo().isBlank()) {
            throw new CampoObrigatorioException("Título do evento é obrigatório");
        }
        if (evento.getData() == null) {
            throw new CampoObrigatorioException("Data do evento é obrigatória");
        }
        if (evento.getData().isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Não é permitido cadastrar eventos com data passada");
        }
    }
}