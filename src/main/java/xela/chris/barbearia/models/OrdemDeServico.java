package xela.chris.barbearia.models;

import xela.chris.barbearia.negocio.Agendamento;

import java.util.ArrayList;
import java.util.List;

public class OrdemDeServico {
    private static int contador = 0; // conta quantas OS foram criadas
    private final int id;

    private Cliente cliente;
    private String data;
    private List<Agendamento> agendamentos;
    private List<Venda> vendas;

    public OrdemDeServico(Cliente cliente, String data) {
        this.cliente = cliente;
        this.data = data;
        this.agendamentos = new ArrayList<>();
        this.vendas = new ArrayList<>();

        contador++;
        this.id = contador;
    }

    public void adicionarAgendamento(Agendamento a) {
        agendamentos.add(a);
    }

    public void adicionarVenda(Venda v) {
        vendas.add(v);
    }

    public double calcularTotal() {
        double total = 0;

        for (Agendamento ag : agendamentos) {
            for (Servico servico : ag.getServicos()) {
                total += servico.getPreco();
            }
        }

        for (Venda v : vendas) {
            total += v.getValorTotal();
        }

        return total;
    }

    public static int getTotalOS() {
        return contador;
    }

    @Override
    public String toString() {
        return "Ordem de Serviço #" + id +
                "\nCliente: " + cliente.getNome() +
                "\nData: " + data +
                "\nAgendamentos: " + agendamentos.size() +
                "\nVendas: " + vendas.size() +
                "\nTotal: R$ " + calcularTotal();
    }
}
