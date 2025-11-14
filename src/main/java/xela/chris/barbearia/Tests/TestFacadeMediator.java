package xela.chris.barbearia.Tests;

import xela.chris.barbearia.FacadeMediator.AgendamentoFacade;

public class TestFacadeMediator {
    public static void main(String[] args) {
        AgendamentoFacade ag = new AgendamentoFacade();
        ag.limparAgendamentos();
        // 1. Teste de agendamento com cadeira de serviço corriqueiro (Corte)
        // Assumindo que o Servico 1 (Corte) é corriqueiro.
        System.out.println("\n--- Teste 1: Agendamento de Serviço Corriqueiro (Corte) ---");
        ag.criarAgendamento(1, 2, 1, "02/10/2032 13:30"); // Cliente 1, Func 2, Servico 1 (Corte) - Cadeira 2

        // 2. Teste de agendamento com cadeira de lavar/secar (Cadeira 1)
        // Assumindo que o Servico 4 (Lavagem/Secagem) é o que precisa da cadeira especial.
        System.out.println("\n--- Teste 2: Agendamento de Serviço Lavar/Secar ---");
        ag.criarAgendamento(2, 3, 4, "02/10/2032 13:30"); // Cliente 2, Func 3, Servico 3 (Lavagem/Secagem) - Cadeira 1

        // 3. Teste de ocupação de todas as cadeiras corriqueiras (Cadeira 2 e 3)
        System.out.println("\n--- Teste 3: Ocupação da Segunda Cadeira Corriqueira ---");
        ag.criarAgendamento(1, 4, 1, "02/10/2032 13:30"); // Cliente 1, Func 4, Servico 1 (Corte) - Cadeira 3

        // 4. Teste de falha ao tentar agendar mais um serviço corriqueiro no mesmo horário
        System.out.println("\n--- Teste 4: Falha ao Agendar (Cadeiras Corriqueiras Ocupadas) ---");
        ag.criarAgendamento(2, 5, 1, "02/10/2032 13:30"); // Cliente 2, Func 5, Servico 1 (Corte) - Falha esperada (Cadeiras corriqueiras ocupadas)

        // 5. Teste de falha ao tentar agendar mais um serviço lavar/secar no mesmo horário
        System.out.println("\n--- Teste 5: Falha ao Agendar (Cadeira Lavar/Secar Ocupada) ---");
        ag.criarAgendamento(1, 1, 4, "02/10/2032 13:30"); // Cliente 1, Func 3, Servico 3 (Lavagem/Secagem) - Falha esperada (Cadeira lavar/secar ocupada)

        // 6. Teste de agendamento em horário diferente
        System.out.println("\n--- Teste 6: Agendamento em Horário Diferente ---");
        ag.criarAgendamento(2, 2, 1, "02/10/2032 14:30"); // Cliente 2, Func 2, Servico 1 (Corte) - Cadeira 2 (Sucesso esperado)

        ag.listarAgendamentos();



       

        

    }
}


