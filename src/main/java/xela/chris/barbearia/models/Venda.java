package xela.chris.barbearia.models;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa uma venda realizada pela barbearia, vinculada a um produto existente.
 *
 * O construtor recebe um objeto Produto e a quantidade vendida, calculando
 * automaticamente o valor unitário e o total com base no produto fornecido.
 * <p>
 * Esta classe também possui um contador estático de IDs, que é atualizado
 * automaticamente conforme novas vendas são criadas. O contador pode ser
 * sincronizado com o maior ID existente ao carregar vendas do armazenamento
 * JSON, garantindo que os IDs não sejam reiniciados após reiniciar o sistema.
 */
public class Venda {

    private static final AtomicInteger contador = new AtomicInteger(0);

    private int id;
    private Produto produto;
    private int quantidade;
    private double valorUnitario;
    private double valorTotal;
    private String dataVenda;

    /**
     * Construtor padrão necessário para serialização JSON.
     */
    public Venda() {
    }

    /**
     * Construtor que recebe um objeto Produto e a quantidade vendida.
     * O valor unitário e total são calculados a partir do produto.
     *
     * @param produto    objeto Produto vendido (já validado no GerenciadorProduto)
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        this.valorUnitario = produto.getValor();
        this.valorTotal = this.quantidade * this.valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.valorTotal = this.quantidade * this.valorUnitario;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    /**
     * Atualiza o contador de IDs para garantir que os próximos registros
     * continuem a sequência a partir do último ID existente.
     *
     * @param ultimoId maior ID encontrado ao carregar os dados
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

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
