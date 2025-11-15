package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Produto;
import xela.chris.barbearia.models.Venda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsável pelo gerenciamento das vendas realizadas.
 *
 * Esta classe fornece métodos para carregar vendas a partir de um
 * arquivo JSON, adicionar novas vendas, remover vendas existentes,
 * listar todas as vendas e limpar o registro. Também possui um
 * método utilitário para calcular o valor total arrecadado em
 * vendas. A persistência é realizada via {@link RepositorioJson}.
 */
public class GerenciarVenda {

    /**
     * Lista de vendas mantida em memória.
     */
    private List<Venda> vendas = new ArrayList<>();

    /**
     * Repositório JSON para persistência das vendas.
     */
    private final RepositorioJson<Venda> repo = new RepositorioJson<>(Venda.class, "vendas.json");

    /**
     * Carrega todas as vendas do arquivo JSON para a lista interna. Se
     * o arquivo não existir ou estiver vazio, a lista ficará vazia.
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
     * Adiciona uma nova venda à lista e salva as alterações no arquivo JSON.
     *
     * @param venda a venda a ser adicionada
     */
    public void adicionar(Venda venda) {
        vendas.add(venda);
    }

    /**
     * Remove uma venda da lista com base em seu ID.
     *
     * @param id identificador da venda a ser removida
     * @return {@code true} se a venda foi removida; {@code false} caso
     * nenhuma venda com o ID informado seja encontrada
     */
    public boolean removerPorId(int id) {
        boolean removido = vendas.removeIf(v -> v.getId() == id);
        if (removido) {
            System.out.println("Venda removida com sucesso!");
        }
        return removido;
    }

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
     * Retorna a lista de todas as vendas atualmente registradas.
     *
     * @return lista de vendas
     */
    public List<Venda> listar() {
        return vendas;
    }

    /**
     * Remove todas as vendas da lista e limpa o conteúdo do arquivo JSON.
     */
    public void limpar() {
        vendas = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }

    /**
     * Calcula o valor total arrecadado em todas as vendas atualmente
     * carregadas.
     *
     * @return soma dos valores totais de cada venda
     */
    public double calcularTotalVendas() {
        return vendas.stream().mapToDouble(Venda::getValorTotal).sum();
    }

    public void salvarTodasVendas(){
        repo.salvarTodos(vendas);
    }

    /**
     * Busca venda pelo id da mesma
     * @param id
     * @return retorna uma mensagem caso encontre e tambem caso nao encontrar
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
