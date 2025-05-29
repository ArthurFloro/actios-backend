package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.exceptions.*;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoDetalhe;
import br.com.actios.actios_backend.repositorys.EventoDetalheRepository;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventoDetalheService {

    @Autowired
    private EventoDetalheRepository detalheRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Transactional
    public EventoDetalhe salvar(EventoDetalhe detalhe) {
        validarDetalheEvento(detalhe);

        try {
            Evento evento = eventoRepository.findById(detalhe.getEvento().getIdEvento())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

            if (detalheRepository.existsByEvento(evento)) {
                throw new RecursoExistenteException("Já existe detalhe cadastrado para este evento");
            }

            validarDataFim(detalhe.getDataFim());
            validarValor(detalhe.getValor());

            detalhe.setEvento(evento);
            return detalheRepository.save(detalhe);

        } catch (DataIntegrityViolationException e) {
            throw new OperacaoNaoPermitidaException("Erro de integridade ao salvar detalhes do evento");
        }
    }

    public EventoDetalhe buscarPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }

        return detalheRepository.findByEvento_IdEvento(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Detalhes do evento não encontrados"));
    }

    public List<EventoDetalhe> listarTodos() {
        List<EventoDetalhe> detalhes = detalheRepository.findAll();
        if (detalhes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum detalhe de evento encontrado");
        }
        return detalhes;
    }

    public List<EventoDetalhe> listarPorFormato(FormatoEvento formato) {
        if (formato == null) {
            throw new CampoObrigatorioException("Formato do evento é obrigatório");
        }

        List<EventoDetalhe> detalhes = detalheRepository.findByFormato(formato);
        if (detalhes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum evento encontrado para o formato " + formato);
        }
        return detalhes;
    }

    public List<EventoDetalhe> listarPorCertificado(Boolean certificado) {
        if (certificado == null) {
            throw new CampoObrigatorioException("Parâmetro certificado é obrigatório");
        }

        List<EventoDetalhe> detalhes = detalheRepository.findByCertificado(certificado);
        if (detalhes.isEmpty()) {
            throw new RecursoNaoEncontradoException(
                    "Nenhum evento encontrado com certificado " + (certificado ? "disponível" : "indisponível"));
        }
        return detalhes;
    }

    public List<EventoDetalhe> listarPorValorMaximo(BigDecimal valorMax) {
        if (valorMax == null) {
            throw new CampoObrigatorioException("Valor máximo é obrigatório");
        }
        if (valorMax.compareTo(BigDecimal.ZERO) < 0) {
            throw new OperacaoNaoPermitidaException("Valor máximo não pode ser negativo");
        }

        List<EventoDetalhe> detalhes = detalheRepository.findByValorLessThanEqual(valorMax);
        if (detalhes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum evento encontrado com valor até " + valorMax);
        }
        return detalhes;
    }

    public List<EventoDetalhe> listarPorDataFimApos(LocalDate data) {
        if (data == null) {
            throw new CampoObrigatorioException("Data de referência é obrigatória");
        }
        if (data.isAfter(LocalDate.now().plusYears(1))) {
            throw new DataInvalidaException("Data não pode ser mais de 1 ano no futuro");
        }

        List<EventoDetalhe> detalhes = detalheRepository.findByDataFimGreaterThanEqual(data);
        if (detalhes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum evento encontrado após " + data);
        }
        return detalhes;
    }

    @Transactional
    public EventoDetalhe atualizar(Integer idEvento, EventoDetalhe detalheAtualizado) {
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }

        EventoDetalhe detalhe = detalheRepository.findByEvento_IdEvento(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Detalhes do evento não encontrados"));

        try {
            if (detalheAtualizado.getDataFim() != null) {
                validarDataFim(detalheAtualizado.getDataFim());
                detalhe.setDataFim(detalheAtualizado.getDataFim());
            }

            if (detalheAtualizado.getFormato() != null) {
                detalhe.setFormato(detalheAtualizado.getFormato());
            }

            if (detalheAtualizado.getCertificado() != null) {
                detalhe.setCertificado(detalheAtualizado.getCertificado());
            }

            if (detalheAtualizado.getValor() != null) {
                validarValor(detalheAtualizado.getValor());
                detalhe.setValor(detalheAtualizado.getValor());
            }

            return detalheRepository.save(detalhe);

        } catch (DataIntegrityViolationException e) {
            throw new OperacaoNaoPermitidaException("Falha ao atualizar detalhes do evento");
        }
    }

    // Métodos auxiliares de validação
    private void validarDetalheEvento(EventoDetalhe detalhe) {
        if (detalhe == null) {
            throw new CampoObrigatorioException("Detalhes do evento são obrigatórios");
        }
        if (detalhe.getEvento() == null || detalhe.getEvento().getIdEvento() == null) {
            throw new CampoObrigatorioException("Evento é obrigatório");
        }
    }

    private void validarDataFim(LocalDate dataFim) {
        if (dataFim != null && dataFim.isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Data fim não pode ser no passado");
        }
    }

    private void validarValor(BigDecimal valor) {
        if (valor == null) {
            throw new CampoObrigatorioException("Valor é obrigatório");
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new OperacaoNaoPermitidaException("Valor não pode ser negativo");
        }
    }
}