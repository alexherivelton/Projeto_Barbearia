package xela.chris.barbearia.Repositorios;

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

    public List<Produto> listar() {

        return repo.buscarTodos();
    }

    public void limpar() {

        repo.salvarTodos(new ArrayList<>());
    }
}
