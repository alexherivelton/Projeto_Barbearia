package xela.chris.barbearia.models;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa um produto disponível na barbearia.
 *
 * <p>
 * A classe {@code Produto} armazena informações básicas como nome, valor,
 * quantidade em estoque e um identificador único gerado automaticamente.
 * </p>
 *
 * <p>
 * O ID de cada produto é gerado sequencialmente através de um contador estático
 * ({@link AtomicInteger}), garantindo unicidade entre os objetos criados.
 * </p>
 */
public class Produto {

    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;
    private String nome;
    private double valor;
    private int quantidade;

    /**
     * Construtor padrão da classe {@code Produto}.
     * <p>
     * Utilizado principalmente em casos de desserialização ou inicialização vazia.
     * </p>
     */
    public Produto() {
    }

    /**
     * Construtor completo da classe {@code Produto}.
     *
     * <p>
     * O identificador é gerado automaticamente com base no contador estático.
     * </p>
     *
     * @param nome       o nome do produto
     * @param valor      o valor unitário do produto
     * @param quantidade a quantidade em estoque
     */
    public Produto(String nome, double valor, int quantidade) {
        this.id = contador.incrementAndGet();
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    /**
     * Retorna o identificador único do produto.
     *
     * @return o ID do produto
     */
    public int getId() {
        return id;
    }

    /**
     * Define manualmente o identificador do produto.
     * <p>
     * Este método deve ser usado apenas em casos específicos, pois o ID é
     * normalmente gerado automaticamente.
     * </p>
     *
     * @param id o novo ID do produto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return o nome do produto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     *
     * @param nome o novo nome do produto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a quantidade disponível em estoque.
     *
     * @return a quantidade atual
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade disponível em estoque.
     *
     * @param quantidade a nova quantidade
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Retorna o valor unitário do produto.
     *
     * @return o valor do produto
     */
    public double getValor() {
        return valor;
    }

    /**
     * Define o valor unitário do produto.
     *
     * @param valor o novo valor do produto
     */
    public void setValor(double valor) {
        this.valor = valor;
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

    /**
     * Retorna uma representação textual do produto.
     *
     * <p>
     * Inclui ID, nome, valor e quantidade.
     * </p>
     *
     * @return uma string contendo os principais dados do produto
     */
    @Override
    public String toString() {
        return "\n===============" +
                "\n ID: " + id +
                "\n Nome: " + nome +
                "\n Valor: " + valor +
                "\n Quantidade: " + quantidade +
                "\n";
    }
}
