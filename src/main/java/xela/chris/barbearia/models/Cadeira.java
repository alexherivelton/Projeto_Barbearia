package xela.chris.barbearia.models;

import java.util.concurrent.atomic.AtomicInteger;

import xela.chris.barbearia.enums.TipoCadeira;

public class Cadeira {
    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;
    private String nome;
    private TipoCadeira tipo;

    public Cadeira() {
    }

    public Cadeira(String nome, TipoCadeira tipo) {
        this.id = contador.incrementAndGet();
        this.nome = nome;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoCadeira getTipo() {
        return tipo;
    }

    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

    @Override
    public String toString() {
        return "Cadeira{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
