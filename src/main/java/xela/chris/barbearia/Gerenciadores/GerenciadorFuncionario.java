package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Funcionario;
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

    public void carregar() {
        List<Funcionario> funcionarios = repoFuncionarios.buscarTodos();
        if (!funcionarios.isEmpty()) {
            int maiorId = funcionarios.stream()
                    .mapToInt(Funcionario::getId)
                    .max()
                    .orElse(0);
            Funcionario.atualizarContador(maiorId);
        }
    }

    /**
     * Adiciona um novo funcionário (somente quem tiver permissão de administrador pode).
     */
    public synchronized void adicionarFuncionario(Funcionario funcionario) {

        List<Funcionario> funcionarios = repoFuncionarios.listar();
        funcionarios.add(funcionario);
        repoFuncionarios.salvarTodos(funcionarios);
    }
    /**
     * Lista todos os funcionários (somente administrador).
     */
    public List<Funcionario> listarFuncionarios() {

        return repoFuncionarios.listar();
    }

    /**
     * Remove um funcionário (somente administrador).
     */
    public void removerFuncionario(int id) {

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
