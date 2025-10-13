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
import xela.chris.barbearia.servicos.ServicoVenda;

import java.io.File;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        GerenciarCliente gerenciarCliente = new GerenciarCliente();
        GerenciadorFuncionario gerenciadorFuncionario = new GerenciadorFuncionario();
        GerenciadorProduto gerenciadorProduto = new GerenciadorProduto();
        GerenciarVenda gv = new GerenciarVenda();
        ServicoVenda sv = new ServicoVenda(gerenciadorProduto, gv);




        System.out.println("Pasta dos JSONs: " + new File("jsons").getAbsolutePath());

        gerenciarCliente.limpar();
        gerenciadorFuncionario.limpar();
        gerenciadorProduto.limpar();
        gv.limpar();

        Cliente cliente1 = new Cliente("Christian", "17600724600", "33998642761", StatusAtendimentoCliente.EM_ESPERA);
        Cliente cliente2 = new Cliente("xela", "14027245601", "122234589090", StatusAtendimentoCliente.EM_ESPERA);
        Funcionario funcionario1 = new Funcionario("Maria", "12345678900", "45986745", "Atendente","chris" , "1234",
                List.of(PermissoesEnum.CADASTRAR_CLIENTE, PermissoesEnum.GERAR_NOTA));
        Funcionario f2 = new Funcionario("Alberto", "87654571232", "45986745", "Atendente","xela" , "mamabola",
                List.of(PermissoesEnum.CADASTRAR_CLIENTE, PermissoesEnum.GERAR_NOTA));

        Produto p1 = new Produto("Shampoo", 14.5, 10);

        gerenciarCliente.adicionar(cliente1);
        gerenciarCliente.adicionar(cliente2);
//        gerenciadorFuncionario.adicionarFuncionario(funcionario1, token);
//        gerenciadorFuncionario.adicionarFuncionario(f2, token);
        gerenciadorProduto.adicionar(p1);

        System.out.println("\nClientes: "  + gerenciarCliente.listar());

        System.out.println("=================================================================");

//        System.out.println("Funcionarios: " + gerenciadorFuncionario.listar());

        System.out.println("Produtos: " + gerenciadorProduto.listar());

        System.out.println("=================================================================");

        sv.efetuarVenda(1, 2, "25102025");

        System.out.println("Produtos: " + gerenciadorProduto.listar());

        System.out.println(sv.calcularTotalVendas());

//        clienteJson.removerPorCpf("14027245601");
//        System.out.println(clienteJson.listar());


    }
}