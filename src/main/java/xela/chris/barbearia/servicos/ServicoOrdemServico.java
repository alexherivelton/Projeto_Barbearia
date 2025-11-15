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
 *
 * Esta classe atua como um serviço de consulta (ou relatório) que
 * consolida dados de {@link GerenciarAgendamento} e {@link GerenciarVenda}.
 *
 * Permite filtrar os dados por data ou por cliente e imprimir um resumo direto
 * no console sem formatações complexas.
 */
public class ServicoOrdemServico {

    private final GerenciarAgendamento gerenciarAgendamento;
    private final GerenciarVenda gerenciarVenda;

    /**
     * Construtor padrão.
     * Instancia internamente novos gerenciadores de Agendamento e Venda.
     */
    public ServicoOrdemServico() {
        this(new GerenciarAgendamento(), new GerenciarVenda());
    }

    /**
     * Construtor com injeção de dependência.
     * Permite que instâncias externas dos gerenciadores sejam fornecidas.
     *
     * @param gerenciarAgendamento O gerenciador de agendamentos a ser usado.
     * @param gerenciarVenda O gerenciador de vendas a ser usado.
     */
    public ServicoOrdemServico(GerenciarAgendamento gerenciarAgendamento, GerenciarVenda gerenciarVenda) {
        this.gerenciarAgendamento = gerenciarAgendamento;
        this.gerenciarVenda = gerenciarVenda;
    }

    /**
     * Busca todos os agendamentos cuja data ({@code dataHora}) contenha
     * a string de filtro fornecida.
     *
     * O método recarrega os dados ({@code carregar()}) antes da busca.
     *
     * @param data A string de filtro de data (ex: "15/11/2025" ou "11/2025").
     * @return Uma lista de {@link Agendamento} correspondentes.
     */
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

    /**
     * Busca todas as vendas cuja data ({@code dataVenda}) contenha
     * a string de filtro fornecida.
     *
     * O método recarrega os dados ({@code carregar()}) antes da busca.
     *
     * @param data A string de filtro de data (ex: "15/11/2025" ou "11/2025").
     * @return Uma lista de {@link Venda} correspondentes.
     */
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

    /**
     * Busca todos os agendamentos associados a um ID de cliente específico.
     *
     * O método recarrega os dados ({@code carregar()}) antes da busca.
     *
     * @param clienteId O ID do cliente a ser filtrado.
     * @return Uma lista de {@link Agendamento} do cliente.
     */
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

    /**
     * Busca todas as vendas associadas a um ID de cliente específico.
     *
     * O método recarrega os dados ({@code carregar()}) antes da busca.
     *
     * @param clienteId O ID do cliente a ser filtrado.
     * @return Uma lista de {@link Venda} do cliente.
     */
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

    /**
     * Consolida e imprime no console todos os agendamentos e vendas
     * filtrados por uma data específica.
     *
     * @param data A string de filtro de data (ex: "11/2025").
     */
    public void imprimirPorData(String data) {
        List<Agendamento> agendamentos = buscarAgendamentosPorData(data);
        List<Venda> vendas = buscarVendasPorData(data);

        System.out.println("=== Ordem de serviço na data: " + data + " ===");
        imprimirAgendamentos(agendamentos);
        imprimirVendas(vendas);
    }

    /**
     * Consolida e imprime no console todos os agendamentos e vendas
     * filtrados por um ID de cliente específico.
     *
     * @param clienteId O ID do cliente.
     */
    public void imprimirPorCliente(int clienteId) {
        List<Agendamento> agendamentos = buscarAgendamentosPorCliente(clienteId);
        List<Venda> vendas = buscarVendasPorCliente(clienteId);

        System.out.println("=== Ordem de serviço para o cliente ID " + clienteId + " ===");
        imprimirAgendamentos(agendamentos);
        imprimirVendas(vendas);
    }

    /**
     * Método auxiliar para formatar e imprimir a lista de agendamentos.
     *
     * @param agendamentos A lista a ser impressa.
     */
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

    /**
     * Método auxiliar para formatar e imprimir a lista de vendas.
     *
     * @param vendas A lista a ser impressa.
     */
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