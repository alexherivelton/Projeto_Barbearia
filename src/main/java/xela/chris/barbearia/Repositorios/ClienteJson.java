package xela.chris.barbearia.Repositorios;

import xela.chris.barbearia.models.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteJson {
    private RepositorioJson<Cliente> repo = new RepositorioJson<>(Cliente.class, "clientes.json");

    public void adicionar(Cliente cliente) {
        List<Cliente> clientes = listar();
        clientes.add(cliente);
        repo.salvarTodos(clientes);
    }

    public boolean removerPorCpf(String cpf) {
        List<Cliente> clientes = listar();
        boolean removido = clientes.removeIf(c -> cpf.equals(c.getCpf()));
        if (removido) {
            repo.salvarTodos(clientes);
        }
        return removido;
    }

    public List<Cliente> listar() {

        return repo.buscarTodos();
    }

    public void limpar() {

        repo.salvarTodos(new ArrayList<>());
    }
}