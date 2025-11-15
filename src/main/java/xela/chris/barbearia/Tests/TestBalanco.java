package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciadorBalanco;

/**
 * Classe de teste para o GerenciadorBalanco.
 * Carrega os dados de agendamentos e vendas e calcula os totais
 * com base em filtros de dia e mês.
 */
public class TestBalanco {

    public static void main(String[] args) {
        GerenciadorBalanco gb = new GerenciadorBalanco();

        // 1. Teste de Balanço por Dia (Ex: "05/11/2025")
        // Este filtro pegará vendas feitas nesse dia
        String filtroDia = "05/11/2025";
        System.out.println("=== TESTE BALANÇO POR DIA (" + filtroDia + ") ===");
        gb.gerarBalanco(filtroDia);

        // 2. Teste de Balanço por Mês (Ex: "11/2025")
        // Este filtro pegará todas as vendas do mês 11
        String filtroMes = "11/2025";
        System.out.println("\n=== TESTE BALANÇO POR MÊS (" + filtroMes + ") ===");
        gb.gerarBalanco(filtroMes);

        // 3. Teste de Balanço por Dia (Serviços e Produtos)
        // Os testes de agendamento (TestFacadeMediator) usam "02/10/2032"
        String filtroDiaServico = "02/10/2032";
        System.out.println("\n=== TESTE BALANÇO POR DIA (Serviços) (" + filtroDiaServico + ") ===");
        gb.gerarBalanco(filtroDiaServico);

        // 4. Teste de Balanço por Mês (Serviços e Produtos)
        String filtroMesServico = "10/2032";
        System.out.println("\n=== TESTE BALANÇO POR MÊS (Serviços) (" + filtroMesServico + ") ===");
        gb.gerarBalanco(filtroMesServico);

        System.out.println("\n=== FIM DO TESTE DE BALANÇO ===");
    }
}