package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.ServicosEnum;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa um serviço oferecido pela barbearia.
 */
public class Servico {
    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;
    private ServicosEnum servico;

    public Servico(ServicosEnum servico) {
        this.id = contador.incrementAndGet();
        this.servico = servico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServicosEnum getServico() {
        return servico;
    }

    public void setServico(ServicosEnum servico) {
        this.servico = servico;
    }

    public String getNome() {
        return servico.getNome();
    }

    public double getPreco() {
        return servico.getPreco();
    }

    public String getDescricao() {
        return servico.getDescricao();
    }

    public static void atualizarContador(int ultimoId){
        contador.set(ultimoId);
    }

    @Override
    public String toString() {
        return "\n===============" +
                "\n ID: " + id +
                "\n Nome: " + getNome() +
                "\n Preço: R$ " + getPreco() +
                "\n Descrição: " + getDescricao() +
                "\n===============";
    }
}
