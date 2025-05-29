package br.com.actios.actios_backend.dto;

import br.com.actios.actios_backend.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String curso;
    private String faculdade;
    private LocalDateTime dataCadastro;

    public static UsuarioDTO fromUsuario(Usuario usuario) {
        if (usuario == null) return null;

        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .curso(usuario.getCurso())
                .faculdade(usuario.getFaculdade() != null ? usuario.getFaculdade().getNome() : null)
                .dataCadastro(usuario.getDataCadastro())
                .build();
    }
}