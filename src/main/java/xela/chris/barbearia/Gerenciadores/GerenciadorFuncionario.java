package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar os funcionários do sistema.
 */
public class GerenciadorFuncionario {

    private List<Funcionario> funcionarios = new ArrayList<>();
    private final RepositorioJson<Funcionario> repo = new RepositorioJson<>(Funcionario.class, "funcionarios.json");;



    public void carregar() {
        funcionarios = repo.buscarTodos();
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

        List<Funcionario> funcionarios = repo.listar();
        funcionarios.add(funcionario);
        repo.salvarTodos(funcionarios);
    }
    /**
     * Lista todos os funcionários (somente administrador).
     */
    public List<Funcionario> listarFuncionarios() {

        return funcionarios;
    }

    /**
     * Remove um funcionário (somente administrador).
     */
    public void removerFuncionario(int id) {

        List<Funcionario> funcionarios = repo.listar();
        funcionarios.removeIf(f -> f.getId() == id);
        repo.salvarTodos(funcionarios);
    }

    public Funcionario buscarFuncionario(int id) {
        for (Funcionario c : funcionarios) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }


    public List<Funcionario> listar() {
        return repo.buscarTodos();
    }

    public void limpar() {
        repo.salvarTodos(new ArrayList<>());
    }

}
