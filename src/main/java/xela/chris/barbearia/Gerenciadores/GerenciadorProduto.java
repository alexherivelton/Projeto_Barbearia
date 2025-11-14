package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar os produtos da barbearia.
 * <p>
 * Esta classe controla operações como adicionar, remover, atualizar estoque
 * e consultar produtos, além de realizar a persistência em arquivo JSON
 * por meio da classe {@link RepositorioJson}.
 * </p>
 *
 * <p>Principais funcionalidades:</p>
 * <ul>
 *     <li>Carregar produtos salvos no arquivo</li>
 *     <li>Adicionar novos produtos</li>
 *     <li>Remover produtos</li>
 *     <li>Buscar produtos por ID</li>
 *     <li>Atualizar estoque após vendas</li>
 *     <li>Listar todos os produtos</li>
 *     <li>Limpar o repositório</li>
 * </ul>
 */
public class GerenciadorProduto {

    /** Lista de produtos mantidos em memória. */
    private List<Produto> produtos = new ArrayList<>();

    /** Repositório responsável por persistência dos produtos no arquivo JSON. */
    private RepositorioJson<Produto> repo =
            new RepositorioJson<>(Produto.class, "produtos.json");

    /**
     * Carrega todos os produtos do arquivo JSON para a lista interna.
     * <p>
     * Também atualiza o contador estático de IDs da classe {@link Produto},
     * garantindo que novos produtos continuem a sequência correta.
     * </p>
     */
    public void carregar() {
        produtos = repo.buscarTodos();
        if (!produtos.isEmpty()) {
            int maiorId = produtos.stream()
                    .mapToInt(Produto::getId)
                    .max()
                    .orElse(0);
            Produto.atualizarContador(maiorId);
        }
    }

    /**
     * Adiciona um novo produto à lista e salva a lista atualizada no arquivo JSON.
     *
     * @param produto Produto a ser adicionado.
     */
    public void adicionar(Produto produto) {
        this.produtos.add(produto);
    }

    /**
     * Remove um produto da lista com base em seu ID e persiste a alteração.
     * <p><b>Observação:</b> o nome do método está incorreto
     * (“removerPorCpf”), pois a remoção é feita por ID.</p>
     *
     * @param id ID do produto a ser removido.
     * @return {@code true} se o produto foi removido, {@code false} caso não exista.
     */
    public boolean removerPorCpf(String id) {
        boolean removido = this.produtos.removeIf(p -> id.equals(p.getId()));
        if (removido) {
            System.out.println("Removido com sucesso!");
        }
        return removido;
    }

    /**
     * Busca um produto pelo seu identificador numérico.
     *
     * @param id ID do produto.
     * @return Produto encontrado ou {@code null} se não existir.
     */
    public Produto buscarPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Atualiza o estoque de um produto após uma venda.
     * <p>
     * Caso o produto exista e tenha quantidade suficiente, a quantidade é
     * reduzida e a alteração é salva no arquivo.
     * </p>
     *
     * @param produtoId ID do produto vendido.
     * @param quantidadeVendida quantidade a ser removida do estoque.
     * @return {@code true} se o estoque foi atualizado;
     *         {@code false} caso o produto não exista ou o estoque seja insuficiente.
     */
    public boolean atualizarEstoque(int produtoId, int quantidadeVendida) {
        Produto p = buscarPorId(produtoId);
        if (p != null && p.getQuantidade() >= quantidadeVendida) {
            p.setQuantidade(p.getQuantidade() - quantidadeVendida);
            repo.salvarTodos(produtos);
            return true;
        }
        return false;
    }

    /**
     * Retorna uma lista contendo todos os produtos atualmente cadastrados.
     *
     * @return lista de produtos.
     */
    public List<Produto> listar() {
        return produtos;
    }

    public void salvarTodosProdutos(){
        repo.salvarTodos(produtos);
    }

    /**
     * Remove todos os produtos do repositório, limpando a lista interna
     * e sobrescrevendo o arquivo JSON com uma lista vazia.
     */
    public void limpar() {
        produtos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}
