package xela.chris.barbearia;

import xela.chris.barbearia.Gerenciadores.GerenciarVenda;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciadorProduto;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.Produto;
import xela.chris.barbearia.models.Venda;

import java.io.File;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        GerenciarCliente gerenciarCliente = new GerenciarCliente();
        GerenciadorFuncionario gerenciadorFuncionario = new GerenciadorFuncionario();
        GerenciadorProduto gerenciadorProduto = new GerenciadorProduto();
        GerenciarVenda gerVenda = new GerenciarVenda();
        gerVenda.carregar();

        System.out.println("Pasta dos JSONs: " + new File("jsons").getAbsolutePath());

        gerenciarCliente.limpar();
        gerenciadorFuncionario.limpar();
        gerenciadorProduto.limpar();
        gerVenda.limpar();

        Cliente cliente1 = new Cliente("Christian", "17600724600", "33998642761", StatusAtendimentoCliente.EM_ESPERA);
        Cliente cliente2 = new Cliente("xela", "14027245601", "122234589090", StatusAtendimentoCliente.EM_ESPERA);
        Funcionario funcionario1 = new Funcionario("Maria", "12345678900", "45986745", "Atendente",
                List.of(PermissoesEnum.CADASTRAR_CLIENTE, PermissoesEnum.GERAR_NOTA));
        Funcionario f2 = new Funcionario("Alberto", "87654571232", "45986745", "Atendente",
                List.of(PermissoesEnum.CADASTRAR_CLIENTE, PermissoesEnum.GERAR_NOTA));

        Produto p1 = new Produto("Shampoo", 14.5, 5);

        gerenciarCliente.adicionar(cliente1);
        gerenciarCliente.adicionar(cliente2);
        gerenciadorFuncionario.adicionar(funcionario1);
        gerenciadorFuncionario.adicionar(f2);
        gerenciadorProduto.adicionar(p1);

        gerVenda.adicionar(new Venda(p1, 2, "10102025"));
        System.out.println("Total arrecadado: " + gerVenda.listar());
        System.out.println(gerenciadorProduto.listar());

//        System.out.println("\nClientes: "  + gerenciarCliente.listar());
//
//        System.out.println("=================================================================");
//
//        System.out.println("Funcionarios: " + gerenciadorFuncionario.listar());
//
//        System.out.println("Produtos: " + gerenciadorProduto.listar());
//
//        System.out.println("=================================================================");




    }
}