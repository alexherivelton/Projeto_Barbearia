package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Produto;
import xela.chris.barbearia.models.Venda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsável pelo gerenciamento das Vendas ({@link Venda}) realizadas.
 *
 * Esta classe centraliza as operações de CRUD para vendas de produtos,
 * mantendo uma lista em memória para acesso rápido e coordenando a
 * persistência com um {@link RepositorioJson} (no arquivo "vendas.json").
 *
 * Funções principais:
 * - Carregar vendas do JSON na inicialização.
 * - Sincronizar o contador de ID estático da classe {@link Venda}.
 * - Adicionar, remover e buscar vendas (na lista em memória).
 * - Salvar a lista em memória para o arquivo JSON (operação manual).
 * - Calcular o valor total das vendas em memória.
 *
 * Atenção: A maioria das operações (adicionar, remover) modifica apenas
 * a lista em memória. É necessário chamar {@link #salvarTodasVendas()}
 * para persistir as alterações no arquivo. A exceção é o método
 * {@link #limpar()}, que persiste imediatamente.
 */
public class GerenciarVenda {

    /**
     * Lista de vendas mantida em memória, carregada do JSON.
     */
    private List<Venda> vendas = new ArrayList<>();

    /**
     * Repositório JSON para persistência das vendas ("vendas.json").
     */
    private final RepositorioJson<Venda> repo = new RepositorioJson<>(Venda.class, "vendas.json");

    /**
     * Construtor padrão.
     * Inicializa o gerenciador e chama {@link #carregar()} para popular
     * a lista de vendas a partir do arquivo JSON.
     */
    public GerenciarVenda() {
        this.carregar();
    }

    /**
     * Carrega (ou recarrega) todas as vendas do arquivo JSON para a lista
     * em memória ({@code this.vendas}).
     *
     * Se a lista carregada não estiver vazia, este método encontra o
     * ID mais alto e atualiza o contador estático na classe {@link Venda}
     * (via {@link Venda#atualizarContador(int)}) para evitar IDs
     * duplicados em novos cadastros.
     */
    public void carregar() {
        vendas = repo.buscarTodos();
        if (!vendas.isEmpty()) {
            int maiorId = vendas.stream()
                    .mapToInt(Venda::getId)
                    .max()
                    .orElse(0);
            Venda.atualizarContador(maiorId);
        }
    }

    /**
     * Adiciona uma nova venda à lista em memória.
     *
     * Atenção: Esta operação *não* persiste os dados automaticamente.
     * Chame {@link #salvarTodasVendas()} para gravar a alteração.
     *
     * @param venda O objeto {@link Venda} a ser adicionado.
     */
    public void adicionar(Venda venda) {
        vendas.add(venda);
    }

    /**
     * Remove uma venda da lista em memória com base em seu ID.
     *
     * Atenção: Esta operação *não* persiste os dados automaticamente.
     * Chame {@link #salvarTodasVendas()} para gravar a alteração.
     *
     * @param id O identificador da venda a ser removida.
     * @return {@code true} se a venda foi encontrada e removida da
     * lista em memória, {@code false} caso contrário.
     */
    public boolean removerPorId(int id) {
        boolean removido = vendas.removeIf(v -> v.getId() == id);
        if (removido) {
            System.out.println("Venda removida com sucesso!");
        }
        return removido;
    }

    /**
     * Busca uma venda na lista em memória pelo seu ID.
     *
     * @param id O identificador da venda.
     * @return O objeto {@link Venda} encontrado, ou {@code null} se não
     * existir na lista em memória.
     */
    public Venda buscarVenda(int id) {
        Iterator<Venda> iterator = vendas.iterator();
        while (iterator.hasNext()) {
            Venda venda = iterator.next();
            if (venda.getId() == id) {
                return venda;
            }
        }
        System.out.println("Venda não encontrado!");
        return null;
    }

    /**
     * Retorna a lista de todas as vendas atualmente registradas
     * (mantidas em memória).
     *
     * @return Uma {@link List} de {@link Venda}.
     */
    public List<Venda> listar() {
        return vendas;
    }

    /**
     * Remove todas as vendas do sistema (memória e persistência).
     *
     * Este método limpa a lista em memória ({@code this.vendas}) e,
     * em seguida, salva uma lista vazia no arquivo JSON
     * (via {@code repo.salvarTodos}), efetivamente limpando todos os
     * dados persistidos.
     */
    public void limpar() {
        vendas = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }

    /**
     * Calcula o valor total arrecadado (soma de {@code getValorTotal()})
     * de todas as vendas atualmente carregadas na lista em memória.
     *
     * @return A soma (double) dos valores totais de cada venda.
     */
    public double calcularTotalVendas() {
        return vendas.stream().mapToDouble(Venda::getValorTotal).sum();
    }

    /**
     * Salva a lista de vendas atualmente em memória ({@code this.vendas})
     * no arquivo JSON, sobrescrevendo o conteúdo anterior do arquivo.
     */
    public void salvarTodasVendas(){
        repo.salvarTodos(vendas);
    }

    /**
     * Busca uma venda na lista em memória pelo ID e retorna uma
     * mensagem (String) indicando o resultado.
     *
     * @param id O ID da venda a ser buscada.
     * @return Uma String "Venda(s) encontrada(s): ..." com os dados da
     * venda, ou "Venda(s) não encontrada!" caso contrário.
     */
    public String buscarVendaPorId(int id) {
        for (Venda p : vendas) {
            if (p.getId() == id) {
                return "Venda(s) encontrada(s): " + p.toString();
            }
        }
        return "Venda(s) não encontrada!";
    }


}