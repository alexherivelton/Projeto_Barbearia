package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;

import java.io.File;
import java.util.List;

public class PessoaTest {
    public static void main(String[] args) {
        GerenciarCliente gerenciarCliente = new GerenciarCliente();
        GerenciadorFuncionario gerenciadorFuncionario = new GerenciadorFuncionario();


        System.out.println("Pasta dos JSONs: " + new File("jsons").getAbsolutePath());
        gerenciarCliente.limpar();
        gerenciadorFuncionario.limpar();

        Cliente cliente1 = new Cliente("Christian", "17600724600", "33998642761", StatusAtendimentoCliente.EM_ESPERA);
        Cliente cliente2 = new Cliente("xela", "14027245601", "99999999999", StatusAtendimentoCliente.EM_ESPERA);
        Funcionario f1 = new Funcionario("Maria", "12345678900", "33998642761", "Atendente","chris" , "1234");
        Funcionario f2 = new Funcionario("Alberto", "87654571232", "45986745", "Atendente","xela" , "mamabola");
        Funcionario f3 = new Funcionario("Caralho", "87654571232", "11111111111", "Administrador","carambolas" , "carambolas123");
        Funcionario f4 = new Funcionario("Jose du Corte", "12312312322", "33998642761", "Barbeiro", "mamaBolas", "tomanocuminhamaevai");

        gerenciarCliente.adicionar(cliente1);
        gerenciarCliente.adicionar(cliente2);

        gerenciadorFuncionario.adicionarFuncionario(f1);
        gerenciadorFuncionario.adicionarFuncionario(f2);
        gerenciadorFuncionario.adicionarFuncionario(f3);
        gerenciadorFuncionario.adicionarFuncionario(f4);


        System.out.println("\nClientes: "  + gerenciarCliente.listar());

        System.out.println("=================================================================");
        System.out.println("=================================================================");

        System.out.println("Funcionarios: " + gerenciadorFuncionario.listar());

        //gerenciarCliente.removerPorCpf("17600724600");

        System.out.println("\nClientes: "  + gerenciarCliente.listar());

        System.out.println("=================================================================");
        System.out.println("=================================================================");

        Funcionario f5 = new Funcionario("Jose du mamaBola", "12312312322", "33998642761", "Barbeiro", "mamaBolas", "tomanocuminhamaevai");
        gerenciadorFuncionario.adicionarFuncionario(f5);
        System.out.println("\nFuncionarios: "  + gerenciadorFuncionario.listar());


    }
}
