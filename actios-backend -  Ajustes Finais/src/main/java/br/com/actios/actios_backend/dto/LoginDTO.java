package br.com.actios.actios_backend.dto;

/**
 * Data Transfer Object (DTO) para credenciais de autenticação.
 * <p>
 * Utilizado para transferir dados de login entre as camadas da aplicação,
 * contendo email e senha do usuário.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class LoginDTO {
    private String email;
    private String senha;

    /**
     * Obtém o email do usuário para autenticação.
     *
     * @return Email do usuário
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do usuário para autenticação.
     *
     * @param email Email válido do usuário
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém a senha do usuário para autenticação.
     *
     * @return Senha do usuário (não criptografada)
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário para autenticação.
     *
     * @param senha Senha do usuário (não criptografada)
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}