package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.models.Venda;
import xela.chris.barbearia.negocio.Agendamento;

import java.util.List;

/**
 * Classe responsável por calcular e exibir o balanço financeiro da barbearia.
 *
 * O balanço é calculado consolidando os dados de duas fontes principais:
 * 1. Serviços realizados (obtidos do {@link GerenciarAgendamento}).
 * 2. Produtos vendidos (obtidos do {@link GerenciarVenda}).
 *
 * Permite filtrar os totais por um período específico (dia ou mês),
 * utilizando um filtro de string simples (ex: "11/2025").
 */
public class GerenciadorBalanco {

    private final GerenciarAgendamento gerenciarAgendamento;
    private final GerenciarVenda gerenciarVenda;

    /**
     * Construtor padrão.
     * Instancia internamente novas versões dos gerenciadores
     * {@link GerenciarAgendamento} e {@link GerenciarVenda}.
     */
    public GerenciadorBalanco() {
        this.gerenciarAgendamento = new GerenciarAgendamento();
        this.gerenciarVenda = new GerenciarVenda();
    }

    /**
     * Construtor para injeção de dependência.
     * Permite fornecer instâncias já existentes dos gerenciadores,
     * o que é útil para testes unitários e desacoplamento.
     *
     * @param ga A instância de {@link GerenciarAgendamento} a ser usada.
     * @param gv A instância de {@link GerenciarVenda} a ser usada.
     */
    public GerenciadorBalanco(GerenciarAgendamento ga, GerenciarVenda gv) {
        this.gerenciarAgendamento = ga;
        this.gerenciarVenda = gv;
    }

    /**
     * Calcula o valor total de SERVIÇOS prestados em um período.
     *
     * Este método recarrega os dados de agendamentos (chamando {@code carregar()})
     * e, em seguida, itera sobre eles. A filtragem é feita verificando se a
     * string {@code dataHora} do agendamento {@code contains} (contém) a
     * string de filtro fornecida.
     *
     * @param filtroData O filtro de data (ex: "15/11/2025" ou "11/2025").
     * @return O valor total (double) dos serviços que correspondem ao filtro.
     */
    public double calcularTotalServicos(String filtroData) {
        // Garante que os dados mais recentes sejam lidos dos arquivos JSON
        gerenciarAgendamento.carregar();
        List<Agendamento> agendamentos = gerenciarAgendamento.listarAgendamentosOrdenadosPorData();

        double totalServicos = 0.0;

        for (Agendamento ag : agendamentos) {

            if (ag.getDataHora() != null && ag.getDataHora().contains(filtroData)) {

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
     * Este método recarrega os dados de vendas (chamando {@code carregar()})
     * e, em seguida, itera sobre elas. A filtragem é feita verificando se a
     * string {@code dataVenda} da venda {@code contains} (contém) a
     * string de filtro fornecida.
     *
     * @param filtroData O filtro de data (ex: "15/11/2025" ou "11/2025").
     * @return O valor total (double) dos produtos vendidos que correspondem ao filtro.
     */
    public double calcularTotalProdutos(String filtroData) {
        gerenciarVenda.carregar();
        List<Venda> vendas = gerenciarVenda.listar();

        double totalProdutos = 0.0;

        for (Venda v : vendas) {

            if (v.getDataVenda() != null && v.getDataVenda().contains(filtroData)) {
                totalProdutos += v.getValorTotal();
            }
        }
        return totalProdutos;
    }

    /**
     * Gera e imprime no console o balanço financeiro detalhado,
     * combinando serviços e produtos para o período filtrado.
     *
     * Utiliza {@link #calcularTotalServicos(String)} e
     * {@link #calcularTotalProdutos(String)} para obter os valores
     * e, em seguida, exibe um relatório formatado.
     *
     * @param filtroData O filtro de data a ser aplicado (ex: "15/11/2025" para dia
     * ou "11/2025" para o mês).
     */
    public void gerarBalanco(String filtroData) {
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