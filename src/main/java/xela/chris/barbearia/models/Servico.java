package xela.chris.barbearia.models;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa um serviço oferecido pela barbearia.
 */
public class Servico {
    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;
    private String nome;
    private double preco;
    private String descricao;

    // 🔹 Construtor padrão obrigatório para o Jackson (desserialização)
    public Servico() {
    }

    // 🔹 Construtor usado para criar novos serviços manualmente
    public Servico(String nome, double preco, String descricao) {
        this.id = contador.incrementAndGet();
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

    @Override
    public String toString() {
        return "\n===============" +
                "\n ID: " + id +
                "\n Nome: " + nome +
                "\n Preço: R$ " + preco +
                "\n Descrição: " + descricao +
                "\n===============";
    }
}
