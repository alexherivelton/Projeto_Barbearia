package xela.chris.barbearia.models;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa uma venda realizada pela barbearia, vinculada diretamente a um produto.
 * Cada venda armazena o produto vendido, a quantidade, o valor unitário no momento
 * da venda, o valor total e a data do registro.
 *
 * <p>A classe utiliza um contador estático para geração automática de IDs,
 * garantindo unicidade mesmo com múltiplas vendas sendo criadas. Esse contador
 * pode ser sincronizado com o maior ID existente ao carregar dados de arquivos,
 * evitando duplicações quando o sistema é reiniciado.</p>
 */
public class Venda {

    /** Contador usado para geração automática e segura de IDs. */
    private static final AtomicInteger contador = new AtomicInteger(0);

    /** Identificador único da venda. */
    private int id;

    /** Produto associado à venda. */
    private Produto produto;

    /** Quantidade de unidades vendidas. */
    private int quantidade;

    /** Valor unitário do produto no momento da venda. */
    private double valorUnitario;

    /** Valor total calculado (quantidade × valor unitário). */
    private double valorTotal;

    /** Data da venda no formato dd/MM/yyyy. */
    private String dataVenda;

    /**
     * Construtor padrão utilizado em operações de serialização e desserialização JSON.
     * Não executa cálculos automáticos.
     */
    public Venda() {
    }

    /**
     * Construtor principal que cria uma venda a partir de um produto e quantidade.
     * O ID é gerado automaticamente e os valores financeiros são calculados.
     *
     * @param produto    produto vendido (já validado no GerenciadorProduto)
     * @param quantidade quantidade vendida
     * @param dataVenda  data da venda no formato "dd/MM/yyyy"
     */
    public Venda(Produto produto, int quantidade, String dataVenda) {
        this.id = contador.incrementAndGet();
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = produto.getValor();
        this.valorTotal = quantidade * this.valorUnitario;
        this.dataVenda = dataVenda;
    }

    /**
     * Retorna o ID da venda.
     *
     * @return identificador único
     */
    public int getId() {
        return id;
    }

    /**
     * Define manualmente o ID da venda, usado principalmente ao carregar dados salvos.
     *
     * @param id valor a ser atribuído
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o produto vendido.
     *
     * @return objeto Produto
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Atualiza o produto da venda e recalcula valores financeiros.
     *
     * @param produto novo produto associado
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
        this.valorUnitario = produto.getValor();
        this.valorTotal = this.quantidade * this.valorUnitario;
    }

    /**
     * Retorna a quantidade vendida.
     *
     * @return quantidade de unidades
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade vendida e recalcula o valor total.
     *
     * @param quantidade nova quantidade
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.valorTotal = this.quantidade * this.valorUnitario;
    }

    /**
     * Retorna o valor unitário do produto no momento da venda.
     *
     * @return valor unitário
     */
    public double getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Retorna o valor total da venda.
     *
     * @return quantidade × valor unitário
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * Retorna a data da venda.
     *
     * @return data no formato dd/MM/yyyy
     */
    public String getDataVenda() {
        return dataVenda;
    }

    /**
     * Define a data da venda.
     *
     * @param dataVenda nova data da venda
     */
    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    /**
     * Atualiza o contador de IDs usado para gerar novos registros.
     * Deve ser chamado ao carregar vendas salvas, evitando repetição de IDs.
     *
     * @param ultimoId maior ID já registrado
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

    /**
     * Retorna uma representação textual formatada da venda.
     *
     * @return string com informações da venda
     */
    @Override
    public String toString() {
        return "\n===============" +
                "\n ID: " + id +
                "\n Produto: " + produto.getNome() +
                "\n Quantidade: " + quantidade +
                "\n Valor Unitário: " + valorUnitario +
                "\n Valor Total: " + valorTotal +
                "\n Data: " + dataVenda +
                "\n";
    }
}
