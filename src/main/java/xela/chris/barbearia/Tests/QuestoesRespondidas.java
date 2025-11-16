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
        ServicoOrdemServico servicoOrdemServico = new ServicoOrdemServico(gerenciarAgendamento, gerenciarVenda);

        // Carrega todos os dados persistentes (chamado no construtor, mas reforçado)
        gerenciarCliente.carregar();
        gerenciadorFuncionario.carregar();
        gerenciarServico.carregar();
        gerenciadorProduto.carregar();
        gerenciarAgendamento.carregar();
        gerenciarVenda.carregar();


        // 1. Funcionario Check: Se a lista estiver vazia, cria e salva
        if (gerenciadorFuncionario.listarFuncionarios().isEmpty()) {
            Funcionario func1 = new Funcionario("Barbeiro Teste", "11111111111", "31911111111", "barbeiro", "barbeiro1", "1234");
            gerenciadorFuncionario.adicionarFuncionario(func1);
            gerenciadorFuncionario.salvarTodosFuncionarios();
            gerenciadorFuncionario.carregar();
        }

        // 2. Servico Check: Se a lista estiver vazia, cria e salva
        if (gerenciarServico.listar().isEmpty()) {
            gerenciarServico.adicionar(new Servico("Corte Teste", 25.0, false, "Corte de cabelo para teste"));
            gerenciarServico.salvarTodosServicos();
            gerenciarServico.carregar();
        }

        // 3. Produto Check: Se a lista estiver vazia, cria e salva
        if (gerenciadorProduto.listar().isEmpty()) {
            gerenciadorProduto.adicionar(new Produto("Pomada Teste", 20.0, 50));
            gerenciadorProduto.salvarTodosProdutos();
            gerenciadorProduto.carregar();
        }

        // Reatribuição e Checagem final
        List<Funcionario> funcionarios = gerenciadorFuncionario.listarFuncionarios();
        List<Servico> servicos = gerenciarServico.listar();
        List<Produto> produtos = gerenciadorProduto.listar();

        if (funcionarios.isEmpty() || servicos.isEmpty() || produtos.isEmpty()) {
            System.err.println("ERRO CRÍTICO: O sistema não conseguiu inicializar Funcionario, Servico ou Produto.");
            return;
        }

        // Variáveis de escopo global para o loop
        Funcionario funcionarioPadrao = funcionarios.get(0);
        Servico servicoPadrao = servicos.get(0);
        Produto produtoPadrao = produtos.get(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataAtual = LocalDate.now().format(formatter);

        // LISTA PARA ARMAZENAR AS NOTAS FISCAIS GERADAS
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

        // Questão 08
        System.out.println("\n====Questão 08====");

        // Questão 09
        System.out.println("\n====Questão 09====");
        System.out.println("Feito - Sistema salva e recupera dados em JSON");

        // Questão 10
        System.out.println("\n====Questão 10====");
        System.out.println("Resolvido com a Nota Fiscal");

        // Questão 11
        System.out.println("\n====Questão 11====");
        System.out.println("Testando contador de instâncias de Servico com ENCAPSULAMENTO (private static com get/set):");

        // ... (código da Questão 11) ...

        // Questão 12
        System.out.println("\n====Questão 12====");
        System.out.println("Testando contador de instâncias de Servico com PROTECTED STATIC:");

        // ... (código da Questão 12) ...

        System.out.println("\nTestando método de classe para retornar total de OrdemDeServico:");
        int totalOS = Barbearia.getTotalOrdensDeServico();
        System.out.println("Total de Ordens de Serviço criadas: " + totalOS);

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

        // Questão 15
        System.out.println("\n====Questão 15====");
        System.out.println("Testando ITERATOR e FOREACH:");

//        // Criar alguns clientes de exemplo
//        List<Cliente> listaClientes = new ArrayList<>();
//        listaClientes.add(new Cliente("Ana", "11111111111", "31999999999", StatusAtendimentoCliente.AGENDADO));
//        listaClientes.add(new Cliente("Bruno", "22222222222", "31888888888", StatusAtendimentoCliente.EM_ESPERA));
//        listaClientes.add(new Cliente("Carlos", "33333333333", "31777777777", StatusAtendimentoCliente.EM_ATENDIMENTO));
//
//    // 1. Pecorrendo ArrayList com ITERATOR:
//        System.out.println("\n1. Percorrendo ArrayList com ITERATOR:");
//        Iterator<Cliente> iterator = listaClientes.iterator();
//        while (iterator.hasNext()) {
//            Cliente cliente = iterator.next();
//            System.out.println(cliente);
//        }
//
//    // 2. Pecorrendo ArrayList com FOREACH:
//        System.out.println("\n2. Percorrendo ArrayList com FOREACH:");
//        for (Cliente cliente : listaClientes) {
//            System.out.println(cliente);
//        }
//        System.out.println("\nExplicação da Relação:");
//        System.out.println("O FOREACH em Java é um 'açúcar sintático' para o Iterator. A sintaxe 'for (Tipo item : colecao)' é convertida pelo compilador para usar 'iterator.hasNext()' e 'iterator.next()'.");
//        System.out.println("Relação: O foreach é a forma mais simples e legível de usar o padrão Iterator.");

        // Questão 16
        System.out.println("\n====Questão 16====");
        System.out.println("Testando COMPARATOR e Collections.sort():");

//        // Criar lista de clientes para ordenar
//        List<Cliente> clientesParaOrdenar = new ArrayList<>();
//        clientesParaOrdenar.add(new Cliente("Zeca", "99999999999", "31911111111", StatusAtendimentoCliente.AGENDADO));
//        clientesParaOrdenar.add(new Cliente("Ana", "11111111111", "31922222222", StatusAtendimentoCliente.EM_ESPERA));
//        clientesParaOrdenar.add(new Cliente("Carlos", "33333333333", "31933333333", StatusAtendimentoCliente.EM_ATENDIMENTO));
//        clientesParaOrdenar.add(new Cliente("Bruno", "22222222222", "31944444444", StatusAtendimentoCliente.AGENDADO));
//
//        System.out.println("\nLista original (desordenada):");
//        clientesParaOrdenar.forEach(System.out::println);
//
//// 1. Ordenando por NOME
//        System.out.println("\n1. Ordenando por NOME usando ClienteNomeComparators:");
//        Collections.sort(clientesParaOrdenar, new ClienteNomeComparators());
//        clientesParaOrdenar.forEach(System.out::println);
//
//// 2. Ordenando por CPF
//        System.out.println("\n2. Ordenando por CPF usando ClienteCpfComparators:");
//        Collections.sort(clientesParaOrdenar, new ClienteCpfComparators());
//        clientesParaOrdenar.forEach(System.out::println);

        // Questão 17
        System.out.println("\n====Questão 17====");
        System.out.println("Testando método FIND com Iterator e Comparator:");
        System.out.println("O método FIND foi implementado na classe GERENCIARCLIENTE, apresentamos aqui as chamadas:");

        // Cria um cliente com os dados de busca
//        Cliente clienteProcuradoNome = new Cliente("Maria", "98765432109", "31987654321", StatusAtendimentoCliente.EM_ESPERA);
//
    //// 1. Busca usando FIND (Implementação com Iterator)
//        System.out.println("\n1. Buscando cliente usando método FIND implementado (por nome):");
//        Cliente encontradoFind = gerenciarCliente.findCliente(clienteProcuradoNome, new ClienteNomeComparators());
//        if (encontradoFind != null) {
//            System.out.println("✓ Cliente encontrado com FIND: " + encontradoFind.getNome());
//        } else {
//            System.out.println("✗ Cliente não encontrado com FIND!");
//        }
//
//    // 2. Busca usando binarySearch()
//        System.out.println("\n2. Buscando cliente usando Collections.binarySearch() (por nome):");
//    // O método buscarClientePorNomeComBinarySearch garante que a lista esteja ordenada antes de chamar o binarySearch.
//        Cliente encontradoBinaryNome = gerenciarCliente.buscarClientePorNomeComBinarySearch(clienteProcuradoNome);
//        if (encontradoBinaryNome != null) {
//            System.out.println("✓ Cliente encontrado com binarySearch: " + encontradoBinaryNome.getNome());
//        } else {
//            System.out.println("✗ Cliente não encontrado com binarySearch!");
//        }
//
//        System.out.println("\n=== COMPARAÇÃO ENTRE FIND E BINARYSEARCH ===");
//        System.out.println("\nMétodo FIND implementado (Busca Linear via Iterator):");
//        System.out.println("- Utiliza iteração sequencial (Iterator) sobre a lista.");
//        System.out.println("- Complexidade: O(n) (tempo linear), percorre a lista no pior caso.");
//        System.out.println("- Vantagem: Simples e não requer que a lista esteja ordenada.");
//
//        System.out.println("\nCollections.binarySearch() (Busca Binária):");
//        System.out.println("- Requer que a lista esteja \\textbf{ordenada} pelo mesmo Comparator antes de ser chamada.");
//        System.out.println("- Complexidade: O(log n) (tempo logarítmico), muito mais eficiente para listas grandes.");
//        System.out.println("- Desvantagem: Requer uma etapa de ordenação prévia (sort é O(n log n)).");

        // Questão 18
        System.out.println("\n====Questão 18====");
        System.out.println("Simulação de atendimento de 10 clientes:");
        System.out.println("==========================================");

        // Simular atendimento de 10 clientes
        for (int i = 1; i <= 10; i++) {
            System.out.println("\n--- Cliente " + i + " ---");

            // 1. Cadastro do cliente e Agendamento (Serviço)
            Cliente cliente = new Cliente("Cliente " + i, String.format("%011d", 100 + i), "319" + String.format("%08d", i), StatusAtendimentoCliente.AGENDADO);
            gerenciarCliente.adicionar(cliente);

            // Agendamento (com status EM_ATENDIMENTO)
            List<Servico> servicosAgendamento = List.of(servicoPadrao);
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
            System.out.println("Agendamento criado.");

            // 2. Criação da Ordem de Serviço (OS)
            OrdemDeServico ordemServico = new OrdemDeServico(cliente, dataAtual);
            ordemServico.adicionarAgendamento(agendamento);
            System.out.println("Ordem de Serviço criada.");

            // 3. Simulação de Venda de Produto (com baixa no estoque)
            if (i % 2 == 0) { // Simula venda para clientes pares
                if (servicoVenda.efetuarVenda(cliente.getId(), produtoPadrao.getId(), 1, dataAtual)) {
                    System.out.println("Venda de produto realizada (baixa em estoque).");
                }
            }

            // 4. Emissão de Nota Fiscal
            gerenciarVenda.carregar();
            // Filtra as vendas recém-criadas para o cliente (necessário para a NF)
            List<Venda> vendasCliente = gerenciarVenda.listar().stream()
                    .filter(v -> v.getCliente() != null && v.getCliente().getId() == cliente.getId())
                    .collect(Collectors.toList());

            // CAPTURA A NOTA FISCAL
            NotaFiscal notaEmitida = gerenciarNotaFiscal.gerarNotaFiscal(agendamento, vendasCliente);

            if (notaEmitida != null) {
                notasFiscaisGeradas.add(notaEmitida); // ADICIONA À LISTA
                System.out.println("Nota Fiscal emitida (ID: " + notaEmitida.getId() + ").");
            } else {
                System.out.println("Falha ao emitir Nota Fiscal.");
            }

            // 5. Finalização
            cliente.setStatusAtendimentoCliente(StatusAtendimentoCliente.ATENDIDO);
            System.out.println("Status finalizado para ATENDIDO.");
        }

        // Salvar tudo
        gerenciarCliente.salvarTodosClientes();
        gerenciarAgendamento.salvarTodos();
        gerenciarVenda.salvarTodasVendas();
        gerenciadorProduto.salvarTodosProdutos();
        gerenciadorFuncionario.salvarTodosFuncionarios();


        // ----------------------------------------------------
        // REQUISITO FINAL: MOSTRAR AS NOTAS FISCAIS
        // ----------------------------------------------------
        System.out.println("### NOTAS FISCAIS EMITIDAS POR CLIENTE ###");

        for (NotaFiscal nota : notasFiscaisGeradas) {
            System.out.println(nota); // NotaFiscal.toString() exibe os detalhes formatados
        }


        System.out.println("\n==========================================");
        System.out.println("Simulação concluída!");
        System.out.println("Total de clientes atendidos: 10");
        System.out.println("Total de Ordens de Serviço: " + Barbearia.getTotalOrdensDeServico());
        System.out.println("==========================================");
    }
}