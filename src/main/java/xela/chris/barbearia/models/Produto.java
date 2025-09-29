package xela.chris.barbearia.models;

import xela.chris.barbearia.negocio.GeradoraDeIds;

public class Produto {
    private int id;
    private String nome;
    private double valor;
    private int quantidade;

    public Produto() {
    }

    public Produto(int id, String nome, double valor, int quantidade) {
        this.id = GeradoraDeIds.nextId();
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "\n ===============" +
                "\n id=" + id +
                "\n  nome='" + nome + '\'' +
                "\n  valor=" + valor +
                "\n  quantidade=" + quantidade +
                "\n ";
    }
}
