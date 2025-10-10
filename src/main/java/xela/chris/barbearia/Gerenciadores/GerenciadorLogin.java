package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.Administrador;
import xela.chris.barbearia.security.TokenService;
import java.util.List;

/**
 * Classe responsável por autenticar usuários (Administrador ou Funcionário)
 * com base nos dados armazenados em JSON.
 */
public class GerenciadorLogin {

    private final RepositorioJson<Funcionario> repoFuncionarios;

    /**
     * Construtor padrão.
     * Inicializa o repositório de funcionários.
     */
    public GerenciadorLogin() {
        this.repoFuncionarios = new RepositorioJson<>(Funcionario.class, "funcionarios.json");
    }

    /**
     * Tenta autenticar um usuário com base em seu nome de usuário e senha.
     *
     * @param usuario o nome de usuário informado
     * @param senha   a senha informada
     * @return o token JWT gerado se a autenticação for bem-sucedida, ou {@code null} caso contrário
     */
    public String autenticar(String usuario, String senha) {
        List<Funcionario> funcionarios = repoFuncionarios.listar();

        for (Funcionario f : funcionarios) {
            if (f.getUsuario().equals(usuario) && f.getSenha().equals(senha)) {
                return TokenService.gerarToken(f);
            }
        }

        // Caso nenhum funcionário seja encontrado
        return null;
    }

    /**
     * Verifica se um token é válido.
     *
     * @param token o token JWT
     * @return {@code true} se for válido; {@code false} caso contrário
     */
    public boolean tokenValido(String token) {
        return TokenService.tokenValido(token);
    }
}
