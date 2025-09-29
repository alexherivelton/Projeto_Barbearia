package xela.chris.barbearia.Repositorios;

import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.Produto;

import java.util.ArrayList;
import java.util.List;


public class ProdutoJson {
    private RepositorioJson<Produto> repo = new RepositorioJson<>(Produto.class, "produtos.json");

    public void adicionar(Produto produto) {
        List<Produto> produtos = listar();
        produtos.add(produto);
        repo.salvarTodos(produtos);
    }

    public boolean removerPorCpf(String id) {
        List<Produto> produtos = listar();
        boolean removido = produtos.removeIf(p -> id.equals(p.getId()));
        if (removido) {
            repo.salvarTodos(produtos);
        }
        return removido;
    }

    public List<Produto> listar() {

        return repo.buscarTodos();
    }

    public void limpar() {

        repo.salvarTodos(new ArrayList<>());
    }
}
