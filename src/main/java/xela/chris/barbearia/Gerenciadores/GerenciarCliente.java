package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Cliente;

import java.util.ArrayList;
import java.util.List;

public class GerenciarCliente {
    private List<Cliente> clientes = new ArrayList<>();
    private RepositorioJson<Cliente> repo = new RepositorioJson<>(Cliente.class, "clientes.json");

    public void carregar (){
        clientes = repo.buscarTodos();
    }

    public void adicionar(Cliente cliente) {
        clientes.add(cliente);
        repo.salvarTodos(clientes);
    }

    public boolean removerPorCpf(String cpf) {
        boolean removido = clientes.removeIf(c -> cpf.equals(c.getCpf()));
        if (removido) {
            repo.salvarTodos(clientes);
        }
        return removido;
    }

    public List<Cliente> listar() {
        return clientes;
    }

    public void limpar() {
        clientes = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}