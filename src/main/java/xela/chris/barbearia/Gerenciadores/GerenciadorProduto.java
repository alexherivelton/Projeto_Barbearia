package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Produto;

import java.util.ArrayList;
import java.util.List;


public class GerenciadorProduto {
    private List<Produto> produtos = new ArrayList<>();
    private RepositorioJson<Produto> repo = new RepositorioJson<>(Produto.class, "produtos.json");

    public void carregar (){
        produtos = repo.buscarTodos();
    }

    public void adicionar(Produto produto) {
        this.produtos.add(produto);
        repo.salvarTodos(produtos);
    }

    public boolean removerPorCpf(String id) {
        boolean removido = this.produtos.removeIf(p -> id.equals(p.getId()));
        if (removido) {
            repo.salvarTodos(produtos);
        }
        return removido;
    }

    public List<Produto> listar() {

        return produtos;
    }

    public void limpar() {
        produtos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}
