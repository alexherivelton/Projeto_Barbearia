package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia os produtos da barbearia, permitindo adicionar, remover,
 * listar e carregar produtos, persistindo os dados em um arquivo JSON.
 */
public class GerenciadorProduto {

    private List<Produto> produtos = new ArrayList<>();
    private RepositorioJson<Produto> repo = new RepositorioJson<>(Produto.class, "produtos.json");

    /**
     * Carrega todos os produtos do arquivo JSON para a lista interna.
     */
    public void carregar() {
        produtos = repo.buscarTodos();
        if(!produtos.isEmpty()) {
            int maiorId = produtos.stream()
                    .mapToInt(Produto::getId)
                    .max()
                    .orElse(0);
            Produto.atualizarContador(maiorId);
        }
    }

    /**
     * Adiciona um novo produto à lista e atualiza o arquivo JSON.
     *
     * @param produto Produto a ser adicionado.
     */
    public void adicionar(Produto produto) {
        this.produtos.add(produto);
        repo.salvarTodos(produtos);
    }

    /**
     * Remove um produto da lista pelo seu ID e atualiza o arquivo JSON.
     *
     * @param id Identificador do produto a ser removido.
     * @return true se o produto foi encontrado e removido; false caso contrário.
     */
    public boolean removerPorCpf(String id) {
        boolean removido = this.produtos.removeIf(p -> id.equals(p.getId()));
        if (removido) {
            repo.salvarTodos(produtos);
        }
        return removido;
    }

    public Produto buscarPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean atualizarEstoque(int produtoId, int quantidadeVendida) {
        Produto p = buscarPorId(produtoId);
        if (p != null && p.getQuantidade() >= quantidadeVendida) {
            p.setQuantidade(p.getQuantidade() - quantidadeVendida);
            repo.salvarTodos(produtos); // persistir a lista atualizada
            return true;
        }
        return false;
    }


    /**
     * Retorna a lista de todos os produtos atualmente gerenciados.
     *
     * @return Lista de produtos.
     */
    public List<Produto> listar() {
        return produtos;
    }

    /**
     * Limpa todos os produtos da lista e do arquivo JSON.
     */
    public void limpar() {
        produtos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}
