package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciadorProduto;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciarVenda;
import xela.chris.barbearia.models.Produto;
import xela.chris.barbearia.servicos.ServicoVenda;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestProdutoVenda {
    public static void main(String[] args) {
        GerenciadorProduto gp = new GerenciadorProduto();
        GerenciarVenda gv = new GerenciarVenda();
        GerenciarCliente gc = new GerenciarCliente();
        ServicoVenda sv = new ServicoVenda(gp, gv, gc);

        Cliente cliente = new Cliente("Cliente Teste", "12345678900", "11999999999", StatusAtendimentoCliente.ATENDIDO);
        gc.adicionar(cliente);
        gc.salvarTodosClientes();

        //Formatar Data
        LocalDate data = LocalDate.now();
        DateTimeFormatter formatarData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = data.format(formatarData);

        Produto p1 = new Produto("Shampoo", 14.5, 35);
        Produto p2 = new Produto("Gel de cabelo", 12.5, 35);

//        gp.adicionar(p1);
//        gp.adicionar(p2);

        gp.carregar();

        System.out.println("Produtos: " + gp.listar());

        System.out.println("=================================================================");

        sv.efetuarVenda(cliente.getId(), 1, 2, dataFormatada);
        sv.efetuarVenda(cliente.getId(), 2, 10, dataFormatada);


        System.out.println("=================================================================");
        System.out.println(gv.buscarVendaPorId(1));
        System.out.println(gv.buscarVendaPorId(6));

    }
}
