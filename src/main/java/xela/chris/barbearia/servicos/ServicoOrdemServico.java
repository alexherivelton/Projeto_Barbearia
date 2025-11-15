package xela.chris.barbearia.servicos;

import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarVenda;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Venda;
import xela.chris.barbearia.negocio.Agendamento;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço simples para reunir agendamentos e vendas e gerar uma ordem de serviço.
 * Permite filtrar os dados por data ou por cliente e imprimir um resumo direto
 * no console sem formatações complexas.
 */
public class ServicoOrdemServico {

    private final GerenciarAgendamento gerenciarAgendamento;
    private final GerenciarVenda gerenciarVenda;

    public ServicoOrdemServico() {
        this(new GerenciarAgendamento(), new GerenciarVenda());
    }

    public ServicoOrdemServico(GerenciarAgendamento gerenciarAgendamento, GerenciarVenda gerenciarVenda) {
        this.gerenciarAgendamento = gerenciarAgendamento;
        this.gerenciarVenda = gerenciarVenda;
    }

    public List<Agendamento> buscarAgendamentosPorData(String data) {
        List<Agendamento> resultado = new ArrayList<>();
        if (data == null || data.isBlank()) {
            return resultado;
        }

        gerenciarAgendamento.carregar();
        List<Agendamento> todos = gerenciarAgendamento.listarAgendamentosOrdenadosPorData();
        if (todos == null) {
            return resultado;
        }

        for (Agendamento agendamento : todos) {
            String dataHora = agendamento.getDataHora();
            if (dataHora != null && dataHora.contains(data)) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    public List<Venda> buscarVendasPorData(String data) {
        List<Venda> resultado = new ArrayList<>();
        if (data == null || data.isBlank()) {
            return resultado;
        }

        gerenciarVenda.carregar();
        List<Venda> todas = gerenciarVenda.listar();
        if (todas == null) {
            return resultado;
        }

        for (Venda venda : todas) {
            String dataVenda = venda.getDataVenda();
            if (dataVenda != null && dataVenda.contains(data)) {
                resultado.add(venda);
            }
        }
        return resultado;
    }

    public List<Agendamento> buscarAgendamentosPorCliente(int clienteId) {
        List<Agendamento> resultado = new ArrayList<>();

        gerenciarAgendamento.carregar();
        List<Agendamento> todos = gerenciarAgendamento.listarAgendamentosOrdenadosPorData();
        if (todos == null) {
            return resultado;
        }

        for (Agendamento agendamento : todos) {
            Cliente cliente = agendamento.getCliente();
            if (cliente != null && cliente.getId() == clienteId) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    public List<Venda> buscarVendasPorCliente(int clienteId) {
        List<Venda> resultado = new ArrayList<>();

        gerenciarVenda.carregar();
        List<Venda> todas = gerenciarVenda.listar();
        if (todas == null) {
            return resultado;
        }

        for (Venda venda : todas) {
            Cliente cliente = venda.getCliente();
            if (cliente != null && cliente.getId() == clienteId) {
                resultado.add(venda);
            }
        }
        return resultado;
    }

    public void imprimirPorData(String data) {
        List<Agendamento> agendamentos = buscarAgendamentosPorData(data);
        List<Venda> vendas = buscarVendasPorData(data);

        System.out.println("=== Ordem de serviço na data: " + data + " ===");
        imprimirAgendamentos(agendamentos);
        imprimirVendas(vendas);
    }

    public void imprimirPorCliente(int clienteId) {
        List<Agendamento> agendamentos = buscarAgendamentosPorCliente(clienteId);
        List<Venda> vendas = buscarVendasPorCliente(clienteId);

        System.out.println("=== Ordem de serviço para o cliente ID " + clienteId + " ===");
        imprimirAgendamentos(agendamentos);
        imprimirVendas(vendas);
    }

    private void imprimirAgendamentos(List<Agendamento> agendamentos) {
        System.out.println("-- Agendamentos --");
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }
        for (Agendamento agendamento : agendamentos) {
            System.out.println(agendamento);
        }
    }

    private void imprimirVendas(List<Venda> vendas) {
        System.out.println("-- Vendas --");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada.");
            return;
        }
        for (Venda venda : vendas) {
            System.out.println(venda);
        }
    }
}