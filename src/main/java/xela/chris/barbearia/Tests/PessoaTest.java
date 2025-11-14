package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;

public class PessoaTest {
    public static void main(String[] args) {
        GerenciarCliente gerenciarCliente = new GerenciarCliente();
        GerenciadorFuncionario gerenciadorFuncionario = new GerenciadorFuncionario();

        gerenciarCliente.carregar();
        gerenciadorFuncionario.carregar();

//        gerenciadorFuncionario.adicionarFuncionario(new Funcionario("Maria", "12345678900", "33998642761", "Atendente","chris" , "1234"));
//        gerenciadorFuncionario.adicionarFuncionario(new Funcionario("Alberto", "87654571232", "45986745", "Atendente","xela" , "mamabola"));
//        gerenciadorFuncionario.adicionarFuncionario(new Funcionario("Jose du Corte", "12312312322", "33998642761", "Barbeiro", "mamaBolas", "tomanocuminhamaevai"));
//
//        gerenciadorFuncionario.salvarTodosFuncionarios();


//        gerenciarCliente.adicionar(new Cliente("Christian", "17600724600", "33998642761", StatusAtendimentoCliente.EM_ESPERA));
//        gerenciarCliente.adicionar(new Cliente("xela", "14027245601", "99999999999", StatusAtendimentoCliente.EM_ESPERA));


        gerenciarCliente.salvarTodosClientes();


        gerenciarCliente.removerPorId(2);

        gerenciarCliente.salvarTodosClientes();

        gerenciarCliente.listar();
    }
}
