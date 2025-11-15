package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciarVenda;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Venda;
import xela.chris.barbearia.negocio.Agendamento;
import xela.chris.barbearia.servicos.ServicoOrdemServico;

import java.util.List;

/**
 * Teste simples para o {@link ServicoOrdemServico} utilizando os dados persistidos
 * nos gerenciadores existentes. O objetivo é reproduzir o estilo dos demais testes
 * do projeto: carregar os dados, invocar o serviço e imprimir os resultados.
 */
public class TestServicoOrdemServico {

    public static void main(String[] args) {
        ServicoOrdemServico servico = new ServicoOrdemServico();

        GerenciarAgendamento gerenciarAgendamento = new GerenciarAgendamento();
        gerenciarAgendamento.carregar();

        GerenciarVenda gerenciarVenda = new GerenciarVenda();
        gerenciarVenda.carregar();

        GerenciarCliente gerenciarCliente = new GerenciarCliente();

        String dataParaTeste = "02/10/2032";
        Cliente clienteParaTeste = gerenciarCliente.buscarCliente(1);

        System.out.println("=== TESTE DE ORDEM DE SERVIÇO ===");

//        servico.imprimirPorData(dataParaTeste);
        servico.imprimirPorCliente(clienteParaTeste.getId());


        System.out.println("\n=== FIM DO TESTE ===");
    }
}