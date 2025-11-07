package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.negocio.Agendamento;

import java.util.ArrayList;
import java.util.List;

public class TestAgendamento {

    public static void main(String[] args) {
        GerenciarAgendamento ga =  new GerenciarAgendamento();
        GerenciadorFuncionario gf =  new GerenciadorFuncionario();
        GerenciarCliente gc =  new GerenciarCliente();
//        gc.carregar();
//        gf.carregar();
//
//        List<Servico> sv = new ArrayList<>();
//        Servico corteCabelo = new Servico("Corte de Cabelo", 35.00, "Corte masculino completo com finalização");
//
//        sv.add(corteCabelo);
//        Agendamento ag =  new Agendamento("teste", gc.buscarCliente(2), gf.buscarFuncionario(1), sv, StatusAtendimentoCliente.EM_ESPERA);
//
//        ga.criarAgendamento(ag);
//        ga.carregar();
//        ga.listarAgendamentos();

        ga.limparAgendamentos();

}
}
