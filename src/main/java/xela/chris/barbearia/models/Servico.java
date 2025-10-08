package xela.chris.barbearia.models;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa um serviço oferecido pela barbearia.
 *
 * <p>
 * Cada serviço possui um identificador único gerado automaticamente,
 * além de informações como nome, preço e descrição.
 * </p>
 *
 * <p>
 * A classe utiliza um contador estático ({@link AtomicInteger})
 * para garantir que cada serviço receba um ID único de forma sequencial.
 * </p>
 */
public class Servico {
    /**
     * Contador estático responsável por gerar IDs únicos.
     */
    private static final AtomicInteger contador = new AtomicInteger(0);

    /**
     * Identificador único do serviço.
     */
    private int id;

    /**
     * Nome do serviço.
     */
    private String nome;

    /**
     * Preço do serviço.
     */
    private double preco;

    /**
     * Descrição detalhada do serviço (opcional).
     */
    private String descricao;

    /**
     * Construtor da classe {@code Servico}.
     *
     * <p>
     * O ID é gerado automaticamente de forma incremental.
     * </p>
     *
     * @param nome  o nome do serviço
     * @param preco o preço do serviço
     */
    public Servico(String nome, double preco) {
        this.id = contador.incrementAndGet();
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * Retorna o identificador único do serviço.
     *
     * @return o ID do serviço
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador único do serviço.
     *
     * <p>
     * Este método deve ser usado com cuidado, pois o ID normalmente
     * é gerado automaticamente.
     * </p>
     *
     * @param id o novo ID do serviço
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome do serviço.
     *
     * @return o nome do serviço
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do serviço.
     *
     * @param nome o novo nome do serviço
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o preço do serviço.
     *
     * @return o preço do serviço
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Define o preço do serviço.
     *
     * @param preco o novo preço do serviço
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Retorna a descrição detalhada do serviço.
     *
     * @return a descrição do serviço
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do serviço.
     *
     * @param descricao a nova descrição do serviço
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
