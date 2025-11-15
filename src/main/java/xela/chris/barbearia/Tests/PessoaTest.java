package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;

/**
 * Classe de teste manual (harness) para as entidades `Cliente` e `Funcionario`.
 *
 * Esta classe contém um método `main` que é usado para instanciar os
 * gerenciadores (`GerenciarCliente`, `GerenciadorFuncionario`) e executar
 * operações de forma ad-hoc.
 *
 * Seu principal objetivo é popular os arquivos JSON com dados iniciais (seeding)
 * e verificar as operações de CRUD (como adicionar, remover, salvar e carregar).
 *
 * O conteúdo do método `main` é frequentemente modificado (comentando e
 * descomentando linhas) para realizar testes específicos.
 */
public class PessoaTest {
    /**
     * Ponto de entrada principal para a execução dos testes manuais.
     *
     * Este método instancia os gerenciadores, carrega os dados existentes
     * e, em seu estado atual, adiciona um novo funcionário administrador,
     * tenta remover o cliente com ID 2 e lista os clientes restantes.
     *
     * As linhas comentadas são exemplos de operações de seeding (população)
     * de dados que foram usadas anteriormente.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
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

        gerenciadorFuncionario.adicionarFuncionario(new Funcionario("Christian", "17600724600", "33998642761", "administrador", "chris_ntt", "chris1234"));

        gerenciadorFuncionario.salvarTodosFuncionarios();



        gerenciarCliente.salvarTodosClientes();

        gerenciarCliente.removerPorId(2);

        gerenciarCliente.salvarTodosClientes();

        gerenciarCliente.listar();
    }
}