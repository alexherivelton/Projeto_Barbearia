package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciarNotaFiscal;

import xela.chris.barbearia.models.NotaFiscal;


public class TestNota {

    public static void main(String[] args) {

        GerenciarNotaFiscal gnf = new GerenciarNotaFiscal();
        GerenciarAgendamento ga =  new GerenciarAgendamento();
        GerenciadorFuncionario gf =  new GerenciadorFuncionario();
        GerenciarCliente gc =  new GerenciarCliente();
        gc.carregar();
        gf.carregar();
        ga.carregar();


        System.out.println("=================================================================");
        System.out.println("Agendamento criado:");
        System.out.println(ga.buscarPorId(56));

        // Gerando a nota fiscal a partir do agendamento
        NotaFiscal nota = gnf.gerarNotaFiscal(ga.buscarPorId(56));

        System.out.println("=================================================================");
        System.out.println("Nota fiscal gerada:");
        System.out.println(nota);

        // Listando todas as notas fiscais salvas
        System.out.println("=================================================================");
        System.out.println("Notas fiscais cadastradas:");
        for (NotaFiscal nf : gnf.listar()) {
            System.out.println(nf);
        }
    }
}
