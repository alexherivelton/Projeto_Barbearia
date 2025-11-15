package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.models.Venda;
import xela.chris.barbearia.negocio.Agendamento;

import java.util.List;

/**
 * Classe responsável por calcular e exibir o balanço financeiro.
 *
 * O balanço é calculado com base nos dados de:
 * 1. Serviços realizados (via GerenciarAgendamento)
 * 2. Produtos vendidos (via GerenciarVenda)
 *
 * Permite filtrar os totais por um período (dia ou mês), usando um filtro de string.
 */
public class GerenciadorBalanco {

    private final GerenciarAgendamento gerenciarAgendamento;
    private final GerenciarVenda gerenciarVenda;

    /**
     * Construtor padrão. Instancia os gerenciadores necessários.
     */
    public GerenciadorBalanco() {
        this.gerenciarAgendamento = new GerenciarAgendamento();
        this.gerenciarVenda = new GerenciarVenda();
    }

    /**
     * Construtor para injeção de dependência (útil para testes).
     */
    public GerenciadorBalanco(GerenciarAgendamento ga, GerenciarVenda gv) {
        this.gerenciarAgendamento = ga;
        this.gerenciarVenda = gv;
    }

    /**
     * Calcula o valor total de SERVIÇOS prestados em um período.
     * O filtro de data é uma string simples (ex: "dd/MM/yyyy" ou "MM/yyyy").
     *
     * @param filtroData O filtro de data (ex: "15/11/2025" ou "11/2025").
     * @return O valor total dos serviços.
     */
    public double calcularTotalServicos(String filtroData) {
        // Garante que os dados mais recentes sejam lidos dos arquivos JSON
        gerenciarAgendamento.carregar();
        List<Agendamento> agendamentos = gerenciarAgendamento.listarAgendamentosOrdenadosPorData();

        double totalServicos = 0.0;

        for (Agendamento ag : agendamentos) {
            // Verifica se a data do agendamento (que é uma string) contém o filtro
            if (ag.getDataHora() != null && ag.getDataHora().contains(filtroData)) {

                // Soma o preço de todos os serviços dentro deste agendamento
                if (ag.getServicos() != null) {
                    for (Servico s : ag.getServicos()) {
                        totalServicos += s.getPreco();
                    }
                }
            }
        }
        return totalServicos;
    }

    /**
     * Calcula o valor total de PRODUTOS vendidos em um período.
     *
     * @param filtroData O filtro de data (ex: "15/11/2025" ou "11/2025").
     * @return O valor total dos produtos.
     */
    public double calcularTotalProdutos(String filtroData) {
        // Garante que os dados mais recentes sejam lidos dos arquivos JSON
        gerenciarVenda.carregar();
        List<Venda> vendas = gerenciarVenda.listar();

        double totalProdutos = 0.0;

        for (Venda v : vendas) {
            // Verifica se a data da venda (string) contém o filtro
            if (v.getDataVenda() != null && v.getDataVenda().contains(filtroData)) {
                totalProdutos += v.getValorTotal();
            }
        }
        return totalProdutos;
    }

    /**
     * Gera e imprime no console o balanço detalhado (Serviços, Produtos e Total).
     *
     * @param filtroData O filtro de data (ex: "15/11/2025" para dia ou "11/2025").
     */
    public void gerarBalanco(String filtroData) {
        // Calcula os totais separadamente
        double totalServicos = calcularTotalServicos(filtroData);
        double totalProdutos = calcularTotalProdutos(filtroData);
        double totalGeral = totalServicos + totalProdutos;

        // Imprime o relatório formatado
        System.out.println("=========================================");
        System.out.println("      BALANÇO FINANCEIRO - " + filtroData);
        System.out.println("=========================================");
        System.out.printf("Total em Serviços:   R$ %.2f%n", totalServicos);
        System.out.printf("Total em Produtos:   R$ %.2f%n", totalProdutos);
        System.out.println("-----------------------------------------");
        System.out.printf("TOTAL GERAL:         R$ %.2f%n", totalGeral);
        System.out.println("=========================================");
    }
}