package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia o ciclo de vida (CRUD) dos produtos da barbearia.
 *
 * Esta classe controla o inventário de produtos, lidando com operações
 * como adição, remoção, busca e atualização de estoque.
 *
 * Ela mantém uma lista de produtos em memória e coordena a persistência
 * desses dados em um arquivo "produtos.json" através do {@link RepositorioJson}.
 *
 * A persistência não é automática na maioria dos métodos;
 * {@link #salvarTodosProdutos()} deve ser chamado para gravar as alterações,
 * exceto em operações críticas como {@link #atualizarEstoque(int, int)}.
 */
public class GerenciadorProduto {

    /** Lista de produtos mantidos em memória, carregada do JSON. */
    private List<Produto> produtos = new ArrayList<>();

    /** Repositório para persistência dos produtos no arquivo "produtos.json". */
    private RepositorioJson<Produto> repo =
            new RepositorioJson<>(Produto.class, "produtos.json");

    /**
     * Construtor padrão.
     * Inicializa o gerenciador e chama {@link #carregar()}
     * para popular a lista de produtos a partir do arquivo JSON.
     */
    public GerenciadorProduto() {
        this.carregar();
    }

    /**
     * Carrega (ou recarrega) todos os produtos do arquivo JSON para a lista em memória.
     *
     * Este método também varre a lista carregada para encontrar o ID mais alto
     * e atualiza o contador estático na classe {@link Produto}
     * (via {@link Produto#atualizarContador(int)}) para evitar IDs duplicados
     * em novos cadastros.
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
     * Adiciona um novo produto à lista em memória.
     *
     * Esta operação *não* persiste os dados automaticamente.
     * Chame {@link #salvarTodosProdutos()} para gravar a alteração no arquivo JSON.
     *
     * @param produto O objeto {@link Produto} a ser adicionado.
     */
    public void adicionar(Produto produto) {
        this.produtos.add(produto);
    }

    /**
     * Remove um produto da lista em memória com base em seu ID.
     *
     * Observação: O nome do método ("removerPorCpf") está inconsistente
     * com a lógica, que opera sobre o ID (comparando a String {@code id}
     * com o {@code getId()} do produto).
     *
     * Esta operação *não* persiste os dados. Chame {@link #salvarTodosProdutos()}
     * para gravar a alteração.
     *
     * @param id ID do produto a ser removido (passado como String).
     * @return {@code true} se o produto foi removido, {@code false} caso não exista.
     */
    public boolean removerPorCpf(String id) {
        // A lógica de comparação (String.equals(int)) é inerentemente problemática
        // e provavelmente falhará, pois o Java autobox a int para Integer,
        // e um String nunca é igual a um Integer.
        boolean removido = this.produtos.removeIf(p -> id.equals(p.getId()));
        if (removido) {
            System.out.println("Removido com sucesso!");
        }
        return removido;
    }

    /**
     * Busca um produto na lista em memória pelo seu identificador numérico.
     *
     * @param id O ID (int) do produto.
     * @return O objeto {@link Produto} encontrado, ou {@code null} se não existir.
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
     * Atualiza o estoque de um produto após uma venda (dando baixa).
     *
     * Este método busca o produto pelo ID. Se o produto existir e tiver
     * estoque suficiente, a quantidade é subtraída.
     *
     * Esta operação *salva automaticamente* a lista inteira de produtos
     * no arquivo JSON (via {@code repo.salvarTodos}) após a atualização bem-sucedida.
     *
     * @param produtoId O ID do produto vendido.
     * @param quantidadeVendida A quantidade a ser removida do estoque.
     * @return {@code true} se o estoque foi atualizado com sucesso (na
     * memória e no arquivo); {@code false} caso o produto não seja
     * encontrado ou o estoque seja insuficiente.
     */
    public boolean atualizarEstoque(int produtoId, int quantidadeVendida) {
        Produto p = buscarPorId(produtoId);
        if (p != null && p.getQuantidade() >= quantidadeVendida) {
            p.setQuantidade(p.getQuantidade() - quantidadeVendida);
            repo.salvarTodos(produtos); // Persistência imediata
            return true;
        }
        return false;
    }

    /**
     * Retorna a lista completa de produtos mantida em memória.
     *
     * @return Uma {@link List} de {@link Produto}.
     */
    public List<Produto> listar() {
        return produtos;
    }

    /**
     * Salva a lista de produtos atualmente em memória no arquivo JSON.
     *
     * Esta ação sobrescreve o conteúdo anterior do arquivo.
     */
    public void salvarTodosProdutos(){
        repo.salvarTodos(produtos);
    }

    /**
     * Remove todos os produtos do sistema (memória e persistência).
     *
     * Este método limpa a lista em memória ({@code this.produtos}) e, em seguida,
     * salva uma lista vazia no arquivo JSON (via {@code repo.salvarTodos}),
     * efetivamente limpando todos os dados persistidos.
     */
    public void limpar() {
        produtos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}