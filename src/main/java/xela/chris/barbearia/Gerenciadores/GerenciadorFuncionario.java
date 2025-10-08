package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorFuncionario {
    private List<Funcionario> funcionarios = new ArrayList<>();
    private RepositorioJson<Funcionario> repo = new RepositorioJson<>(Funcionario.class, "funcionarios.json");

    public void carregar (){
        funcionarios = repo.buscarTodos();
    }

    public void adicionar(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        repo.salvarTodos(funcionarios);
    }

    public boolean removerPorCpf(String cpf) {
        boolean removido = this.funcionarios.removeIf(f -> cpf.equals(f.getCpf()));
        if (removido) {
            repo.salvarTodos(funcionarios);
        }
        return removido;
    }

    public List<Funcionario> listar() {
        return funcionarios;
    }

    public void limpar() {
        funcionarios = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}