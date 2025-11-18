package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Comparators.ClienteCpfComparators;
import xela.chris.barbearia.Comparators.ClienteNomeComparators;
import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciadorProduto;
import xela.chris.barbearia.Gerenciadores.GerenciarServico;
import xela.chris.barbearia.Gerenciadores.GerenciarVenda;
import xela.chris.barbearia.Gerenciadores.GerenciarNotaFiscal;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.*;
import xela.chris.barbearia.negocio.Agendamento;
import xela.chris.barbearia.negocio.Barbearia;
import xela.chris.barbearia.servicos.ServicoOrdemServico;
import xela.chris.barbearia.servicos.ServicoVenda;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class QuestoesRespondidas {
    public static void main(String[] args) {
        GerenciarCliente gerenciarCliente = new GerenciarCliente();
        GerenciadorFuncionario gerenciadorFuncionario = new GerenciadorFuncionario();
        GerenciarServico gerenciarServico = new GerenciarServico();
        GerenciadorProduto gerenciadorProduto = new GerenciadorProduto();
        GerenciarAgendamento gerenciarAgendamento = new GerenciarAgendamento();
        GerenciarVenda gerenciarVenda = new GerenciarVenda();
        GerenciarNotaFiscal gerenciarNotaFiscal = new GerenciarNotaFiscal();
        ServicoVenda servicoVenda = new ServicoVenda(gerenciadorProduto, gerenciarVenda, gerenciarCliente);
        ServicoOrdemServico servicoOrdemServico = new ServicoOrdemServico();

        gerenciarCliente.carregar();
        gerenciadorFuncionario.carregar();
        gerenciarServico.carregar();
        gerenciadorProduto.carregar();
        gerenciarAgendamento.carregar();
        gerenciarVenda.carregar();


        if (gerenciadorFuncionario.listarFuncionarios().isEmpty()) {
            Funcionario func1 = new Funcionario("Barbeiro Teste", "11111111111", "31911111111", "barbeiro", "barbeiro1", "1234");
            gerenciadorFuncionario.adicionarFuncionario(func1);
            gerenciadorFuncionario.salvarTodosFuncionarios();
            gerenciadorFuncionario.carregar();
        }

        if (gerenciarServico.listar().isEmpty()) {
            gerenciarServico.adicionar(new Servico("Corte Teste", 25.0, false, "Corte de cabelo para teste"));
            gerenciarServico.salvarTodosServicos();
            gerenciarServico.carregar();
        }

        if (gerenciadorProduto.listar().isEmpty()) {
            gerenciadorProduto.adicionar(new Produto("Pomada Teste", 20.0, 50));
            gerenciadorProduto.salvarTodosProdutos();
            gerenciadorProduto.carregar();
        }

        List<Funcionario> funcionarios = gerenciadorFuncionario.listarFuncionarios();
        List<Servico> servicos = gerenciarServico.listar();
        List<Produto> produtos = gerenciadorProduto.listar();

        Funcionario funcionarioPadrao = funcionarios.get(0);
        Servico servicoPadrao = servicos.get(0);
        Produto produtoPadrao = produtos.get(0);

        produtoPadrao.setQuantidade(50);
        gerenciadorProduto.salvarTodosProdutos();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataAtual = LocalDate.now().format(formatter);


        List<NotaFiscal> notasFiscaisGeradas = new ArrayList<>();
        // ----------------------------------------------------


        // Questão 01
        System.out.println("====Questão 01====");
        System.out.println("Diagrama criado!");

        // Questão 02
        System.out.println("\n====Questão 02====");
        System.out.println("Classe Funcionario, especifica Cargo e Permissoes.");

        // Questão 03
        System.out.println("\n====Questão 03====");
        System.out.println("Acessar qualquer classe da pasta models");

        // Questão 04
        System.out.println("\n====Questão 04====");
        System.out.println("Acessar a classe Funcionario.");

        // Questão 05
        System.out.println("\n====Questão 05====");
        System.out.println("Acessar a classe GerenciarCadeira.");

        // Questão 06
        System.out.println("\n====Questão 06====");
        System.out.println("Acessar a classe GerenciadorFuncionario");

        // Questão 07
        System.out.println("\n====Questão 07====");
        System.out.println("Acessar a classe GerenciadorClientes.");

        // Questão 08
        System.out.println("\n====Questão 08====");
        System.out.println("Acessar a classe ServicoOrdemServico.");

        // Questão 09
        System.out.println("\n====Questão 09====");
        System.out.println("Feito - Sistema salva e recupera dados em JSON");

        // Questão 10
        System.out.println("\n====Questão 10====");
        System.out.println("Resolvido com a Nota Fiscal");

        // Questão 11
        System.out.println("\n====Questão 11====");
        System.out.println("Testando contador de instâncias de Servico com ENCAPSULAMENTO (private static com get/set):");
        System.out.println("Funciona em conjuto com a classe MAIN");


        // Questão 12
        System.out.println("\n====Questão 12====");
        System.out.println("Testando contador de instâncias de Servico com PROTECTED STATIC:");

        System.out.println("Funciona em conjuto com a classe MAIN");

        // Questão 13
        System.out.println("\n====Questão 13====");
        System.out.println("Acessar a classe ClienteCpfComparators");
        System.out.println("Acessar a classe ClienteNomeComparators");
        System.out.println("Acessar a classe AgendamentoServicoComparators");
        System.out.println("Acessar a classe AgendamentoDataComparator");

        // Questão 14
        System.out.println("\n====Questão 14====");
        System.out.println("Mostrar as JSON prontas!");
        System.out.println("Sistema salva e recupera dados em arquivos JSON:");

        //Questão 15
        System.out.println("\n====Questão 15====");
        System.out.println("Classe TestFilaDeEspera");

        // Questão 15
        System.out.println("\n====Questão 15====");
        System.out.println("Testando ITERATOR e FOREACH:");

        // Criar alguns clientes de exemplo
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(new Cliente("Chris", "11111111111", "31999999999", StatusAtendimentoCliente.AGENDADO));
        listaClientes.add(new Cliente("Xela", "22222222222", "31888888888", StatusAtendimentoCliente.EM_ESPERA));
        listaClientes.add(new Cliente("Paulo", "33333333333", "31777777777", StatusAtendimentoCliente.EM_ATENDIMENTO));

        // 1. Pecorrendo ArrayList com ITERATOR:
        System.out.println("\n1. Percorrendo ArrayList com ITERATOR:");
        Iterator<Cliente> iterator = listaClientes.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            System.out.println(cliente);
        }

        // 2. Pecorrendo ArrayList com FOREACH:
        System.out.println("\n2. Percorrendo ArrayList com FOREACH:");
        for (Cliente cliente : listaClientes) {
            System.out.println(cliente);
        }

        // Questão 16
        System.out.println("\n====Questão 16====");
        System.out.println("Testando COMPARATOR e Collections.sort():");

        // Criar lista de clientes para ordenar
        List<Cliente> clientesParaOrdenar = new ArrayList<>();
        clientesParaOrdenar.add(new Cliente("Zeca", "99999999999", "31911111111", StatusAtendimentoCliente.AGENDADO));
        clientesParaOrdenar.add(new Cliente("Ana", "11111111111", "31922222222", StatusAtendimentoCliente.EM_ESPERA));
        clientesParaOrdenar.add(new Cliente("Carlos", "33333333333", "31933333333", StatusAtendimentoCliente.EM_ATENDIMENTO));
        clientesParaOrdenar.add(new Cliente("Bruno", "22222222222", "31944444444", StatusAtendimentoCliente.AGENDADO));

        System.out.println("\nLista original (desordenada):");
        clientesParaOrdenar.forEach(System.out::println);

        // 1. Ordenando por NOME
        System.out.println("\n1. Ordenando por NOME usando ClienteNomeComparators:");
        Collections.sort(clientesParaOrdenar, new ClienteNomeComparators());
        clientesParaOrdenar.forEach(System.out::println);

        // 2. Ordenando por CPF
        System.out.println("\n2. Ordenando por CPF usando ClienteCpfComparators:");
        Collections.sort(clientesParaOrdenar, new ClienteCpfComparators());
        clientesParaOrdenar.forEach(System.out::println);

        // Questão 17
        System.out.println("\n====Questão 17====");
        System.out.println("Testando método FIND com Iterator e Comparator:");
        System.out.println("O método FIND foi implementado na classe GERENCIARCLIENTE, apresentamos aqui as chamadas:");

        // Cria um cliente com os dados de busca
        Cliente clienteProcuradoNome = new Cliente("Maria", "98765432109", "31987654321", StatusAtendimentoCliente.EM_ESPERA);
        // Adiciona este cliente ao gerenciador para que ele possa ser encontrado
        gerenciarCliente.adicionar(clienteProcuradoNome);
        gerenciarCliente.salvarTodosClientes();


        // 1. Busca usando FIND (Implementação com Iterator)
        System.out.println("\n1. Buscando cliente usando método FIND implementado (por nome):");
        Cliente encontradoFind = gerenciarCliente.findCliente(clienteProcuradoNome, new ClienteNomeComparators());
        if (encontradoFind != null) {
            System.out.println("✓ Cliente encontrado com FIND: " + encontradoFind.getNome());
        } else {
            System.out.println("✗ Cliente não encontrado com FIND!");
        }

        // 2. Busca usando binarySearch()
        System.out.println("\n2. Buscando cliente usando Collections.binarySearch() (por nome):");
        Cliente encontradoBinaryNome = gerenciarCliente.buscarClientePorNomeComBinarySearch(clienteProcuradoNome);
        if (encontradoBinaryNome != null) {
            System.out.println("✓ Cliente encontrado com binarySearch: " + encontradoBinaryNome.getNome());
        } else {
            System.out.println("✗ Cliente não encontrado com binarySearch!");
        }


        // Questão 18
        System.out.println("\n====Questão 18====");
        System.out.println("Simulação de atendimento de 10 clientes:");
        System.out.println("==========================================");


        for (int i = 1; i <= 10; i++) {
            System.out.println("\n--- Cliente " + i + " ---");

            String cpf = String.format("%011d", 100 + i);
            Cliente cliente = new Cliente("Cliente " + i, cpf, "319" + String.format("%08d", i), StatusAtendimentoCliente.AGENDADO);
            gerenciarCliente.adicionar(cliente);
            gerenciarCliente.salvarTodosClientes();

            // REMOVIDO: 2. Criação da Ordem de Serviço (OS) manual

            // 3. Denotação correta dos serviços realizados (e Agendamento)
            List<Servico> servicosAgendamento = new ArrayList<>();
            servicosAgendamento.add(servicoPadrao); // Ex: Corte Teste (R$ 25.0)

            // Simula um segundo serviço para clientes ímpares (ex: Barba)
            if (i % 2 != 0) {
                // Adiciona o mesmo serviço para simplificar, mas representa um serviço extra
                servicosAgendamento.add(servicoPadrao);
            }

            String dataHora = dataAtual + " " + String.format("%02d:00", 9 + i);
            Agendamento agendamento = new Agendamento(
                    dataHora,
                    cliente,
                    funcionarioPadrao,
                    servicosAgendamento,
                    StatusAtendimentoCliente.EM_ATENDIMENTO,
                    1 // Cadeira ID 1
            );
            gerenciarAgendamento.criarAgendamento(agendamento);
            System.out.printf("Agendamento #%d criado para %s. Serviços: %d%n", agendamento.getId(), cliente.getNome(), servicosAgendamento.size());

            // 4. Baixa no estoque (Simulação de Venda de Produto)
            List<Venda> vendasCliente = new ArrayList<>(); // Mantida para simular a venda
            if (i % 2 == 0) { // Clientes pares compram um produto (baixa de estoque)
                if (servicoVenda.efetuarVenda(cliente.getId(), produtoPadrao.getId(), 1, dataAtual)) {
                    // Recupera a venda recém-criada (necessário para a NF/OS)
                    gerenciarVenda.carregar();
                    Venda ultimaVenda = gerenciarVenda.listar().stream()
                            .filter(v -> v.getCliente() != null && v.getCliente().getId() == cliente.getId())
                            .max((v1, v2) -> Integer.compare(v1.getId(), v2.getId())) // Pega a última venda
                            .orElse(null);

                    if (ultimaVenda != null) {
                        vendasCliente.add(ultimaVenda);
                        System.out.println("Venda de produto realizada (baixa em estoque). Produto: " + ultimaVenda.getProduto().getNome());
                    }
                } else {
                    System.out.println("Falha na venda de produto (estoque insuficiente ou erro de busca).");
                }
            }

            boolean finalizado = gerenciarAgendamento.finalizarAgendamento(
                    agendamento.getId(),
                    gerenciarNotaFiscal,
                    gerenciarVenda,
                    servicoOrdemServico
            );

            if (finalizado) {
                // Busca a NF gerada para adicionar ao relatório de NFs
                gerenciarNotaFiscal.carregar();
                NotaFiscal notaEmitida = gerenciarNotaFiscal.listar().stream()
                        .filter(nota -> nota.getAgendamento() != null && nota.getAgendamento().getId() == agendamento.getId())
                        .max((n1, n2) -> Integer.compare(n1.getId(), n2.getId()))
                        .orElse(null);

                if(notaEmitida != null) {
                    notasFiscaisGeradas.add(notaEmitida);
                    System.out.println("Nota Fiscal emitida (ID: " + notaEmitida.getId() + ").");
                }

                System.out.println("Status finalizado para ATENDIDO.");
            } else {
                System.out.println("Falha ao finalizar o agendamento/gerar documentos.");
            }
        }

        gerenciarCliente.salvarTodosClientes();
        gerenciarAgendamento.salvarTodos();
        gerenciarVenda.salvarTodasVendas();
        gerenciadorProduto.salvarTodosProdutos();
        gerenciadorFuncionario.salvarTodosFuncionarios();
        servicoOrdemServico.salvarTodos();


        System.out.println("\n### RESUMO DAS ORDENS DE SERVIÇO PERSISTIDAS ###");
        servicoOrdemServico.carregar(); // Recarrega do JSON o histórico de OS
        List<OrdemDeServico> ordensDeServicoPersistidas = servicoOrdemServico.listar();

        if (ordensDeServicoPersistidas.isEmpty()) {
            System.out.println("Nenhuma Ordem de Serviço foi persistida.");
        } else {
            for (OrdemDeServico os : ordensDeServicoPersistidas) {
                System.out.println(os);
            }
        }


        System.out.println("\n### NOTAS FISCAIS EMITIDAS POR CLIENTE ###");
        for (NotaFiscal nota : notasFiscaisGeradas) {
            System.out.println(nota); // NotaFiscal.toString() exibe os detalhes formatados
        }
        System.out.println("\n==========================================");
        System.out.println("Simulação concluída!");
        System.out.println("Total de clientes atendidos: 10");
        System.out.println("Total de Ordens de Serviço: " + OrdemDeServico.getTotalOS());
        System.out.println("==========================================");
    }
}
