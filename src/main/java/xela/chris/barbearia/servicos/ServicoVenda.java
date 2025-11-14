package xela.chris.barbearia.servicos;

import xela.chris.barbearia.Gerenciadores.GerenciadorProduto;
import xela.chris.barbearia.Gerenciadores.GerenciarVenda;
import xela.chris.barbearia.models.Produto;
import xela.chris.barbearia.models.Venda;
import java.time.LocalDate;

/**
 * Serviço responsável por realizar operações relacionadas a vendas na barbearia.
 *
 * <p>
 * Esta classe atua como uma camada de regra de negócio, intermediando a comunicação
 * entre os gerenciadores de produtos e vendas.
 * Suas principais responsabilidades são:
 * </p>
 *
 * <ul>
 *     <li>Realizar a venda de um produto</li>
 *     <li>Atualizar o estoque após a venda</li>
 *     <li>Registrar e salvar vendas</li>
 *     <li>Calcular o total arrecadado em vendas</li>
 * </ul>
 *
 * <p>
 * O serviço sempre carrega os dados de produtos e vendas antes de cada operação,
 * garantindo consistência com os arquivos JSON utilizados no sistema.
 * </p>
 */
public class ServicoVenda {

    /** Gerenciador responsável por manipular os produtos. */
    private final GerenciadorProduto gerenciadorProduto;

    /** Gerenciador responsável por salvar e recuperar vendas. */
    private final GerenciarVenda gerenciarVenda;

    /**
     * Construtor padrão que inicializa os gerenciadores internamente.
     * <p>
     * Ideal para uso direto no sistema principal.
     * </p>
     */
    public ServicoVenda() {
        this.gerenciadorProduto = new GerenciadorProduto();
        this.gerenciarVenda = new GerenciarVenda();
    }

    /**
     * Construtor alternativo que recebe instâncias externas dos gerenciadores.
     * <p>
     * Útil em cenários de testes unitários ou injeção de dependência.
     * </p>
     *
     * @param gerenciadorProduto gerenciador de produtos
     * @param gerenciarVenda gerenciador de vendas
     */
    public ServicoVenda(GerenciadorProduto gerenciadorProduto, GerenciarVenda gerenciarVenda) {
        this.gerenciadorProduto = gerenciadorProduto;
        this.gerenciarVenda = gerenciarVenda;
    }

    /**
     * Efetua uma venda de produto, atualizando o estoque e registrando a venda.
     *
     * <p>A operação consiste nos seguintes passos:</p>
     * <ol>
     *     <li>Carregar lista de produtos</li>
     *     <li>Buscar o produto pelo ID</li>
     *     <li>Verificar se existe estoque suficiente</li>
     *     <li>Atualizar o estoque</li>
     *     <li>Registrar a venda no gerenciador de vendas</li>
     * </ol>
     *
     * @param produtoId ID do produto a ser vendido
     * @param quantidade quantidade vendida
     * @param dataVenda data da venda no formato "dd/MM/yyyy"
     * @return {@code true} se a venda for concluída, {@code false} caso o produto não exista
     *         ou não haja estoque suficiente
     */
    public boolean efetuarVenda(int produtoId, int quantidade, String dataVenda) {
        gerenciadorProduto.carregar();

        Produto produto = gerenciadorProduto.buscarPorId(produtoId);
        if (produto == null) {
            return false;
        }

        if (!gerenciadorProduto.atualizarEstoque(produtoId, quantidade)) {
            return false;
        }

        gerenciarVenda.carregar();
        Venda venda = new Venda(produto, quantidade, dataVenda);
        gerenciarVenda.adicionar(venda);

        return true;
    }

    /**
     * Calcula o valor total arrecadado em todas as vendas registradas.
     *
     * @return soma de todas as vendas em formato {@code double}
     */
    public double calcularTotalVendas() {
        gerenciarVenda.carregar();
        return gerenciarVenda.calcularTotalVendas();
    }
}
