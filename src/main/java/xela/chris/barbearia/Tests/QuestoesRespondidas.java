package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;

public class QuestoesRespondidas {
    public static void main(String[] args) {
        GerenciarCliente gerenciarCliente = new GerenciarCliente();
        GerenciadorFuncionario gerenciadorFuncionario = new GerenciadorFuncionario();

        gerenciarCliente.carregar();
        gerenciadorFuncionario.carregar();

        //Questão 01: {
        System.out.printf("====Questão 01====");
        System.out.println("Diagrama criado!");
        // }
        //Questão 02: {
        System.out.println("====Questão 02====");
        System.out.println("Classe Funcionario, especifica Cargo e Permissoes.");
        //}
        //Questão 03:{
        System.out.println("====Questão 03====");
        System.out.println("Acessar qualquer classe da pasta models");
        // }
        //Questão 04:{
        System.out.println("====Questão 04====");
        System.out.println("Acessar a classe Funcionario.");
        // }
        //Questão 05:{
        System.out.println("====Questão 05====");
        System.out.println("Acessar a classe GerenciarCadeira.");
        // }
        //Questão 06:{
        System.out.println("====Questão 06====");
        System.out.println("Acessar a classe GerenciadorFuncionario");
        // ex para adicionar: Funcionario f1 = new Funcionario("Maria", "12345678900", "33998642761", "Atendente","chris" , "1234");
        // ex para alterar: gerenciadorFuncionario.atualizarFuncionario(3, "Jose do Grau", null, null, null, "jose123", "jose1234545");
        // ex para excluir: gerenciadorFuncionario.removerFuncionario(3);

        //Questão 07:{
        System.out.println("====Questão 07====");
        // ex para adicionar: Cliente cliente1 = new Cliente("Christian", "17600724600", "33998642761", StatusAtendimentoCliente.EM_ESPERA);
        //ex para alterar: gerenciarCliente.atualizarCliente(2, "Alex", null, null, StatusAtendimentoCliente.EM_ATENDIMENTO);
        // ex para excluir: gerenciarCliente.removerPorId(2);


        //Questão 08:{
        System.out.println("====Questão 08====");
        System.out.println("Precisa fazer!");


        //Questão 09:{
        System.out.println("====Questão 09====");
        System.out.println("Precisa fazer!");
        // }
        //Questão 10:{
        System.out.println("====Questão 10====");
        System.out.println("Precisa fazer!");

        //Questão 11:{
        System.out.println("====Questão 11====");
        System.out.println("Precisar fazer!");
        // }
        //Questão 12:{
        System.out.println("====Questão 12====");
        System.out.println("Precisa fazer!");
        // }
        //Questão 13:{
        System.out.println("====Questão 13====");
        System.out.println("Acessar a classe ClienteCpfComparators"); // -> retorna em ordem alfabetica baseada na comparacao de caracteres
        System.out.println("Acessar a classe ClienteNomeComparators"); // -> retorna em ordem alfabetica baseada na comparacao de caracteres
        System.out.println("Acessar a classe AgendamentoServicoComparators"); // -> retorna em ordem alfabetica baseada na comparacao de caracteres
        System.out.println("Acessar a classe AgendamentoClienteComparator"); // -> retorna em ordem alfabetica baseada na comparacao de caracteres
        // }
        //Questão 14:{
        System.out.println("====Questão 14====");
        System.out.println("Mostrar as JSON prontas!");
        // }
        //Questão 15:{
        System.out.println("====Questão 15====");
        System.out.println("Tem que fazer");
        // }
        //Questão 16:{
        System.out.println("====Questão 16====");
        System.out.println("Precisa fazer");
        // }
        //Questão 17:{
        System.out.println("====Questão 17====");
        System.out.println("Precisa fazer");
        // }
        //Questão 18:{
        System.out.println("====Questão 18====");
        System.out.println("Precisa fazer");
        // }



    }
}

