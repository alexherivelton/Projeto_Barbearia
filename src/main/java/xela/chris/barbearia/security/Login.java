package xela.chris.barbearia.security;

/**
 * Classe que representa os dados de login do usuário.
 * <p>
 * Contém o nome de usuário e a senha informados durante o processo de autenticação.
 * </p>
 */
public class Login {

    private String usuario;
    private String senha;

    /**
     * Construtor padrão utilizado por frameworks de serialização.
     */
    public Login() {}

    /**
     * Construtor completo da classe {@code Login}.
     *
     * @param usuario o nome de usuário informado
     * @param senha   a senha informada
     */
    public Login(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    /**
     * Retorna o nome de usuário informado.
     *
     * @return o nome de usuário
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Define o nome de usuário.
     *
     * @param usuario o nome de usuário
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna a senha informada.
     *
     * @return a senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha.
     *
     * @param senha a senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
