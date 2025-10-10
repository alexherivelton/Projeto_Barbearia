package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.security.TokenService;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar os funcionários do sistema.
 */
public class GerenciadorFuncionario {

    private final RepositorioJson<Funcionario> repoFuncionarios;

    public GerenciadorFuncionario() {
        this.repoFuncionarios = new RepositorioJson<>(Funcionario.class, "funcionarios.json");
    }

    /**
     * Adiciona um novo funcionário (somente quem tiver permissão de administrador pode).
     */
    public synchronized void adicionarFuncionario(Funcionario funcionario, String token) {
        List<String> permissoes = TokenService.getPermissoesDoToken(token);

        // Só deixa adicionar se tiver a permissão de ADMINISTRAR_USUARIOS (ou outra que vc definir)
        if (permissoes == null || !permissoes.contains("GERENCIAR_FUNCIONARIOS")) {
            throw new SecurityException("Você não tem permissão para adicionar funcionários.");
        }

        List<Funcionario> funcionarios = repoFuncionarios.listar();
        funcionarios.add(funcionario);
        repoFuncionarios.salvarTodos(funcionarios);
    }

    /**
     * Lista todos os funcionários (somente administrador).
     */
    public List<Funcionario> listarFuncionarios(String token) {
        List<String> permissoes = TokenService.getPermissoesDoToken(token);

        if (permissoes == null || !permissoes.contains("GERENCIAR_FUNCIONARIOS")) {
            throw new SecurityException("Você não tem permissão para listar funcionários.");
        }

        return repoFuncionarios.listar();
    }

    /**
     * Remove um funcionário (somente administrador).
     */
    public void removerFuncionario(int id, String token) {
        List<String> permissoes = TokenService.getPermissoesDoToken(token);

        if (permissoes == null || !permissoes.contains("GERENCIAR_FUNCIONARIOS")) {
            throw new SecurityException("Você não tem permissão para remover funcionários.");
        }

        List<Funcionario> funcionarios = repoFuncionarios.listar();
        funcionarios.removeIf(f -> f.getId() == id);
        repoFuncionarios.salvarTodos(funcionarios);
    }


    public List<Funcionario> listar() {
        return repoFuncionarios.buscarTodos();
    }

    public void limpar() {
        repoFuncionarios.salvarTodos(new ArrayList<>());
    }

}
