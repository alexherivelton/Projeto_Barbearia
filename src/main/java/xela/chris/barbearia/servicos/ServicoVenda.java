package xela.chris.barbearia.servicos;

import xela.chris.barbearia.Gerenciadores.GerenciadorProduto;
import xela.chris.barbearia.Gerenciadores.GerenciarVenda;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;

import xela.chris.barbearia.models.Produto;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Venda;
import java.time.LocalDate;

/**
 * Serviço responsável por realizar operações relacionadas a vendas na barbearia.
 *
 *
 * Esta classe atua como uma camada de regra de negócio, intermediando a comunicação
 * entre os gerenciadores de produtos, vendas e clientes.
 * Suas principais responsabilidades são:
 *
 *
 *
 *     Realizar a venda de um produto
 *     Atualizar o estoque após a venda
 *     Registrar e salvar vendas
 *     Calcular o total arrecadado em vendas
 *
 *
 *
 * O serviço sempre carrega os dados de produtos e vendas antes de cada operação,
 * garantindo consistência com os arquivos JSON utilizados no sistema.
 *
 */
public class ServicoVenda {

    /** Gerenciador responsável por manipular os produtos. */
    private final GerenciadorProduto gerenciadorProduto;

    /** Gerenciador responsável por salvar e recuperar vendas. */
    private final GerenciarVenda gerenciarVenda;

    /** Gerenciador responsável por acessar os clientes cadastrados. */
    private final GerenciarCliente gerenciarCliente;


    /**
     * Construtor padrão que inicializa os gerenciadores internamente.
     *
     * Ideal para uso direto no sistema principal.
     *
     */
    public ServicoVenda() {
        this.gerenciadorProduto = new GerenciadorProduto();
        this.gerenciarVenda = new GerenciarVenda();
        this.gerenciarCliente = new GerenciarCliente();
    }

    /**
     * Construtor alternativo que recebe instâncias externas dos gerenciadores.
     *
     * Útil em cenários de testes unitários ou injeção de dependência.
     *
     *
     * @param gerenciadorProduto gerenciador de produtos
     * @param gerenciarVenda gerenciador de vendas
     */
    public ServicoVenda(GerenciadorProduto gerenciadorProduto, GerenciarVenda gerenciarVenda) {
        this(gerenciadorProduto, gerenciarVenda, new GerenciarCliente());
    }

    /**
     * Construtor alternativo permitindo informar também um gerenciador de clientes.
     *
     * @param gerenciadorProduto gerenciador de produtos
     * @param gerenciarVenda gerenciador de vendas
     * @param gerenciarCliente gerenciador de clientes
     */
    public ServicoVenda(GerenciadorProduto gerenciadorProduto,
                        GerenciarVenda gerenciarVenda,
                        GerenciarCliente gerenciarCliente) {
        this.gerenciadorProduto = gerenciadorProduto;
        this.gerenciarVenda = gerenciarVenda;
        this.gerenciarCliente = gerenciarCliente;
    }

    /**
     * Efetua uma venda de produto, atualizando o estoque e registrando a venda.
     *
     * A operação consiste nos seguintes passos:
     *
     *     Carregar lista de clientes e buscar o cliente pelo ID
     *     Carregar lista de produtos
     *     Buscar o produto pelo ID
     *     Verificar se existe estoque suficiente
     *     Atualizar o estoque
     *     Registrar a venda no gerenciador de vendas e persistir em JSON
     *
     *
     * @param clienteId ID do cliente que está comprando
     * @param produtoId ID do produto a ser vendido
     * @param quantidade quantidade vendida
     * @param dataVenda data da venda no formato "dd/MM/yyyy"
     * @return {@code true} se a venda for concluída, {@code false} caso o cliente ou
     *         o produto não existam, ou não haja estoque suficiente
     */
    public boolean efetuarVenda(int clienteId, int produtoId, int quantidade, String dataVenda) {
        // Carrega e busca o cliente
        gerenciarCliente.carregar();
        Cliente cliente = gerenciarCliente.buscarCliente(clienteId);
        if (cliente == null) {
            return false;
        }

        // Carrega e busca o produto
        gerenciadorProduto.carregar();
        Produto produto = gerenciadorProduto.buscarPorId(produtoId);
        if (produto == null) {
            return false;
        }

        // Atualiza estoque
        if (!gerenciadorProduto.atualizarEstoque(produtoId, quantidade)) {
            return false;
        }

        // Carrega vendas atuais
        gerenciarVenda.carregar();

        // Cria e adiciona a venda
        Venda venda = new Venda(produto, cliente, quantidade, dataVenda);
        gerenciarVenda.adicionar(venda);

        // Agora a venda é persistida em vendas.json antes de qualquer novo carregar()
        gerenciarVenda.salvarTodasVendas();

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
