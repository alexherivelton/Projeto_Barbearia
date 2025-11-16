package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciarNotaFiscal;
import xela.chris.barbearia.Gerenciadores.GerenciarVenda;

import xela.chris.barbearia.models.NotaFiscal;
import xela.chris.barbearia.models.Venda;
import xela.chris.barbearia.negocio.Agendamento;

import xela.chris.barbearia.servicos.ServicoVenda;

import java.util.ArrayList;
import java.util.List;


public class TestNota {

    public static void main(String[] args) {
        ServicoVenda sv = new ServicoVenda();
        
        GerenciarNotaFiscal gnf = new GerenciarNotaFiscal();
        GerenciarAgendamento ga =  new GerenciarAgendamento();
        GerenciadorFuncionario gf =  new GerenciadorFuncionario();
        GerenciarCliente gc =  new GerenciarCliente();
        GerenciarVenda gv = new GerenciarVenda();
        gc.carregar();
        gf.carregar();
        ga.carregar();
        gv.carregar();


        System.out.println("=================================================================");
//        System.out.println("Agendamento criado:");
        Agendamento agendamento = ga.buscarPorId(56);
        System.out.println(agendamento);



        List<Venda> vendasCliente = new ArrayList<>();
        vendasCliente.add(gv.buscarVenda(6));

        // Gerando a nota fiscal a partir do agendamento e das vendas do cliente
        NotaFiscal nota = gnf.gerarNotaFiscal(agendamento, vendasCliente);

        System.out.println("=================================================================");
        System.out.println("Nota fiscal gerada:");
        System.out.println(nota);

        // Listando todas as notas fiscais salvas
//        System.out.println("=================================================================");
//        System.out.println("Notas fiscais cadastradas:");
//        for (NotaFiscal nf : gnf.listar()) {
//            System.out.println(nf);
//        }
    }
}
