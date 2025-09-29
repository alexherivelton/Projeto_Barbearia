package xela.chris.barbearia.Repositorios;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioJson {
    private RepositorioJson<Funcionario> repo = new RepositorioJson<>(Funcionario.class, "funcionarios.json");

    public void adicionar(Funcionario funcionario) {
        List<Funcionario> funcionarios = listar();
        funcionarios.add(funcionario);
        repo.salvarTodos(funcionarios);
    }

    public boolean removerPorCpf(String cpf) {
        List<Funcionario> funcionarios = listar();
        boolean removido = funcionarios.removeIf(f -> cpf.equals(f.getCpf()));
        if (removido) {
            repo.salvarTodos(funcionarios);
        }
        return removido;
    }

    public List<Funcionario> listar() {

        return repo.buscarTodos();
    }

    public void limpar() {

        repo.salvarTodos(new ArrayList<>());
    }
}