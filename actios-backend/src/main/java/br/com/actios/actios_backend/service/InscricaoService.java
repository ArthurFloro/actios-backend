package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.DataInvalidaException;
import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.exceptions.TipoUsuarioInvalidoException;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.Inscricao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import br.com.actios.actios_backend.repositorys.InscricaoRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.enums.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Inscricao inscrever(Integer idUsuario, Integer idEvento) throws Exception {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado."));

        if (usuario.getTipo() != TipoUsuario.ALUNO) {
            throw new TipoUsuarioInvalidoException("Apenas usuários do tipo 'ALUNO' podem se inscrever em eventos.");
        }

        if (evento.getData().isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Não é possível se inscrever em eventos passados.");
        }

        Optional<Inscricao> existente = inscricaoRepository.findByUsuarioAndEvento(usuario, evento);
        if (existente.isPresent()) {
            throw new RecursoExistenteException("Usuário já está inscrito neste evento.");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setUsuario(usuario);
        inscricao.setEvento(evento);
        inscricao.setNumeroInscricao(gerarNumeroInscricaoUnico());
        inscricao.setDataInscricao(LocalDateTime.now());  // <<< seta a data atual aqui

        return inscricaoRepository.save(inscricao);
    }

    public List<Inscricao> listarTodas() {
        return inscricaoRepository.findAll();
    }

    public void cancelarInscricao(Integer idInscricao) throws Exception {
        if (!inscricaoRepository.existsById(idInscricao)) {
            throw new RecursoNaoEncontradoException("Inscrição não encontrada.");
        }

        inscricaoRepository.deleteById(idInscricao);
    }

    private String gerarNumeroInscricaoUnico() {
        return UUID.randomUUID().toString();
    }
}
