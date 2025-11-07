package xela.chris.barbearia.Tests;

import xela.chris.barbearia.FacadeMediator.AgendamentoFacade;

public class TestFacadeMediator {
    public static void main(String[] args) {
        AgendamentoFacade ag = new AgendamentoFacade();
//        ag.criarAgendamento(1,2,3,"02/10/2032 13:30");
//        ag.criarAgendamento(2,3,2,"03/12/2022 11:30");
//        ag.listarAgendamentos();

       // ag.excluirAgendamento(1);

        System.out.println(ag.buscarAgendamento(1));

    }
}


