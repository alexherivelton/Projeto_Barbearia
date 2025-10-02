package xela.chris.barbearia;

import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.Repositorios.ClienteJson;
import xela.chris.barbearia.Repositorios.FuncionarioJson;
import xela.chris.barbearia.Repositorios.ProdutoJson;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.Produto;

import java.io.File;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        ClienteJson clienteJson = new ClienteJson();
        FuncionarioJson funcionarioJson = new FuncionarioJson();
        ProdutoJson produtoJson = new ProdutoJson();

        System.out.println("Pasta dos JSONs: " + new File("jsons").getAbsolutePath());

        clienteJson.limpar();
        funcionarioJson.limpar();
        produtoJson.limpar();

        Cliente cliente1 = new Cliente("Christian", "17600724600", "33998642761", StatusAtendimentoCliente.EM_ESPERA);
        Cliente cliente2 = new Cliente("xela", "14027245601", "122234589090", StatusAtendimentoCliente.EM_ESPERA);
        Funcionario funcionario1 = new Funcionario("Maria", "12345678900", "45986745", "Atendente",
                List.of(PermissoesEnum.CADASTRAR_CLIENTE, PermissoesEnum.GERAR_NOTA));
        Funcionario f2 = new Funcionario("Alberto", "87654571232", "45986745", "Atendente",
                List.of(PermissoesEnum.CADASTRAR_CLIENTE, PermissoesEnum.GERAR_NOTA));

        Produto p1 = new Produto("Shampoo", 14.5, 10);

        clienteJson.adicionar(cliente1);
        clienteJson.adicionar(cliente2);
        funcionarioJson.adicionar(funcionario1);
        funcionarioJson.adicionar(f2);
        produtoJson.adicionar(p1);

        System.out.println("\nClientes:");
        for (Cliente c : clienteJson.listar()) {
            System.out.println(c);
        }

        System.out.println("=================================================================");

//        System.out.println("Funcionarios: ");
//        for (Funcionario f : funcionarioJson.listar()) {
//            System.out.println(f);
//        }

        System.out.println("Funcionarios: " + funcionarioJson.listar());

        System.out.println("Produtos: " + produtoJson.listar());


        System.out.println("=================================================================");

        clienteJson.removerPorCpf("14027245601");
        System.out.println(clienteJson.listar());
    }
}