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
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.OrdemDeServico;
import xela.chris.barbearia.models.Produto;
import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.models.Venda;
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

        gerenciarCliente.carregar();
        gerenciadorFuncionario.carregar();
        gerenciarServico.carregar();
        gerenciadorProduto.carregar();
        gerenciarAgendamento.carregar();
        gerenciarVenda.carregar();

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
        // ex para criar: Funcionario f1 = new Funcionario("Maria", "12345678900", "33998642761", "Atendente","chris" , "1234");
        // ex para adicionar: gerenciadorFuncionario.adicionarFuncionario(f1);
        // ex para alterar: gerenciadorFuncionario.atualizarFuncionario(3, "Jose do Grau", null, null, null, "jose123", "jose1234545");
        // ex para excluir: gerenciadorFuncionario.removerFuncionario(3);

        // Questão 07
        System.out.println("\n====Questão 07====");
        // ex para criar: Cliente cliente1 = new Cliente("Christian", "17600724600", "33998642761", StatusAtendimentoCliente.EM_ESPERA);
        // ex para adicionar: gerenciarCliente.adicionar(cliente1);
        // ex para alterar: gerenciarCliente.atualizarCliente(2, "Alex", null, null, StatusAtendimentoCliente.EM_ATENDIMENTO);
        // ex para excluir: gerenciarCliente.removerPorId(2);

        // Questão 08
        System.out.println("\n====Questão 08====");
        // Imprimi os atendimentos e serviços realizados em determinado dia
        // Imprimi também os serviços realizados por clientes
        // TesteServicoOrdemServico

        // Questão 09
        System.out.println("\n====Questão 09====");
        System.out.println("Feito - Sistema salva e recupera dados em JSON");

        // Questão 10
        System.out.println("\n====Questão 10====");
        System.out.println("Resolvido com a Nota Fiscal");
        //TestNota

        // Questão 11
        System.out.println("\n====Questão 11====");
        System.out.println("Testando contador de instâncias de Servico com ENCAPSULAMENTO (private static com get/set):");
        
        // Resetar contador para teste
        Servico.setContadorInstanciasEncapsulado(0);
        
        Servico servico1 = new Servico("Corte", 25.0, false, "Corte de cabelo");
        Servico servico2 = new Servico("Barba", 15.0, false, "Aparar barba");
        Servico servico3 = new Servico("Corte + Barba", 35.0, true, "Corte completo");
        
        int totalEncapsulado = Servico.getContadorInstanciasEncapsulado();
        System.out.println("Total de instâncias criadas (encapsulado): " + totalEncapsulado);
        System.out.println("Vantagens do encapsulamento:");
        System.out.println("- Controle total sobre acesso");
        System.out.println("- Possibilidade de validação");
        System.out.println("- Melhor para manutenção");
        System.out.println("Desvantagens:");
        System.out.println("- Mais verboso (requer get/set)");
        System.out.println("- Mais código para escrever");

        // Questão 12
        System.out.println("\n====Questão 12====");
        System.out.println("Testando contador de instâncias de Servico com PROTECTED STATIC:");
        
        // Resetar contador para teste
        Servico.setContadorInstanciasProtected(0);
        
        Servico servico4 = new Servico("Sobrancelha", 10.0, false, "Design de sobrancelha");
        Servico servico5 = new Servico("Lavagem", 5.0, true, "Lavagem de cabelo");
        
        int totalProtected = Servico.getContadorInstanciasProtected();
        System.out.println("Total de instâncias criadas (protected): " + totalProtected);
        System.out.println("Vantagens do protected:");
        System.out.println("- Acesso direto para subclasses");
        System.out.println("- Mais simples de usar");
        System.out.println("Desvantagens:");
        System.out.println("- Menos controle sobre modificações");
        System.out.println("- Pode ser modificado diretamente por subclasses");
        
        System.out.println("\nTestando método de classe para retornar total de OrdemDeServico:");
        int totalOS = Barbearia.getTotalOrdensDeServico();
        System.out.println("Total de Ordens de Serviço criadas: " + totalOS);

        // Questão 13
        System.out.println("\n====Questão 13====");
        System.out.println("Acessar a classe ClienteCpfComparators"); // -> retorna em ordem alfabetica baseada na comparacao de caracteres
        System.out.println("Acessar a classe ClienteNomeComparators"); // -> retorna em ordem alfabetica baseada na comparacao de caracteres
        System.out.println("Acessar a classe AgendamentoServicoComparators"); // -> retorna em ordem alfabetica baseada na comparacao de caracteres
        System.out.println("Acessar a classe AgendamentoDataComparator"); // -> retorna em ordem alfabetica baseada na comparacao de caracteres

        // Questão 14
        System.out.println("\n====Questão 14====");
        System.out.println("Mostrar as JSON prontas!");
        System.out.println("Sistema salva e recupera dados em arquivos JSON:");

        // Questão 15
        System.out.println("\n====Questão 15====");
        System.out.println("Testando ITERATOR e FOREACH:");
        
        // Criar alguns clientes de exemplo
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(new Cliente("Ana", "11111111111", "31999999999", StatusAtendimentoCliente.AGENDADO));
        listaClientes.add(new Cliente("Bruno", "22222222222", "31888888888", StatusAtendimentoCliente.EM_ESPERA));
        listaClientes.add(new Cliente("Carlos", "33333333333", "31777777777", StatusAtendimentoCliente.EM_ATENDIMENTO));
        
        System.out.println("\n1. Percorrendo ArrayList com ITERATOR:");
        Iterator<Cliente> iterator = listaClientes.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            System.out.println(cliente);
        }
        
        System.out.println("\n2. Percorrendo ArrayList com FOREACH:");
        for (Cliente cliente : listaClientes) {
            System.out.println(cliente);
        }
        
        System.out.println("\nExplicação:");
        System.out.println("O ITERATOR é um padrão de design que permite percorrer uma coleção");
        System.out.println("de forma sequencial sem expor sua estrutura interna.");
        System.out.println("O FOREACH em Java é um 'açúcar sintático' que internamente usa");
        System.out.println("o Iterator. A sintaxe 'for (Tipo item : colecao)' é convertida");
        System.out.println("pelo compilador para usar iterator.hasNext() e iterator.next().");
        System.out.println("Relação: O foreach é uma forma mais simples e legível de usar o Iterator.");

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
        
        System.out.println("\n1. Ordenando por NOME usando ClienteNomeComparators:");
        Collections.sort(clientesParaOrdenar, new ClienteNomeComparators());
        clientesParaOrdenar.forEach(System.out::println);
        
        System.out.println("\n2. Ordenando por CPF usando ClienteCpfComparators:");
        Collections.sort(clientesParaOrdenar, new ClienteCpfComparators());
        clientesParaOrdenar.forEach(System.out::println);

        // Questão 17
        System.out.println("\n====Questão 17====");
        System.out.println("Testando método FIND com Iterator e Comparator:");
        System.out.println("E comparando com Collections.binarySearch():");
        System.out.println("(Todos os métodos de busca estão implementados em GerenciarCliente)");
        
        // Adicionar clientes ao gerenciador
        Cliente cliente1 = new Cliente("João", "12345678901", "31912345678", StatusAtendimentoCliente.AGENDADO);
        Cliente cliente2 = new Cliente("Maria", "98765432109", "31987654321", StatusAtendimentoCliente.EM_ESPERA);
        Cliente cliente3 = new Cliente("Pedro", "55555555555", "31955555555", StatusAtendimentoCliente.EM_ATENDIMENTO);
        Cliente cliente4 = new Cliente("Ana", "11111111111", "31911111111", StatusAtendimentoCliente.AGENDADO);
        Cliente cliente5 = new Cliente("Carlos", "99999999999", "31999999999", StatusAtendimentoCliente.EM_ATENDIMENTO);
        
        gerenciarCliente.adicionar(cliente1);
        gerenciarCliente.adicionar(cliente2);
        gerenciarCliente.adicionar(cliente3);
        gerenciarCliente.adicionar(cliente4);
        gerenciarCliente.adicionar(cliente5);
        
        System.out.println("\n=== TESTE 1: Busca por NOME ===");
        System.out.println("\n1. Buscando cliente usando método FIND implementado (por nome):");
        Cliente clienteProcuradoNome = new Cliente("Maria", "98765432109", "31987654321", StatusAtendimentoCliente.EM_ESPERA);
        Cliente encontradoFind = gerenciarCliente.findCliente(clienteProcuradoNome, new ClienteNomeComparators());
        if (encontradoFind != null) {
            System.out.println("✓ Cliente encontrado com FIND: " + encontradoFind.getNome());
        } else {
            System.out.println("✗ Cliente não encontrado com FIND!");
        }
        
        System.out.println("\n2. Buscando cliente usando Collections.binarySearch() via GerenciarCliente (por nome):");
        List<Cliente> listaOrdenadaNome = gerenciarCliente.getClientesOrdenados(new ClienteNomeComparators());
        
        System.out.println("Lista ordenada por nome:");
        for (int i = 0; i < listaOrdenadaNome.size(); i++) {
            System.out.println("  [" + i + "] " + listaOrdenadaNome.get(i).getNome());
        }
        
        Cliente encontradoBinaryNome = gerenciarCliente.buscarClientePorNomeComBinarySearch(clienteProcuradoNome);
        if (encontradoBinaryNome != null) {
            System.out.println("✓ Cliente encontrado com binarySearch: " + encontradoBinaryNome.getNome());
        } else {
            System.out.println("✗ Cliente não encontrado com binarySearch!");
        }
        
        System.out.println("\n=== TESTE 2: Busca por CPF ===");
        System.out.println("\n1. Buscando cliente usando método FIND implementado (por CPF):");
        Cliente clienteProcuradoCpf = new Cliente("Pedro", "55555555555", "31955555555", StatusAtendimentoCliente.EM_ATENDIMENTO);
        Cliente encontradoFindCpf = gerenciarCliente.findCliente(clienteProcuradoCpf, new ClienteCpfComparators());
        if (encontradoFindCpf != null) {
            System.out.println("✓ Cliente encontrado com FIND: " + encontradoFindCpf.getNome() + " (CPF: " + encontradoFindCpf.getCpf() + ")");
        } else {
            System.out.println("✗ Cliente não encontrado com FIND!");
        }
        
        System.out.println("\n2. Buscando cliente usando Collections.binarySearch() via GerenciarCliente (por CPF):");
        List<Cliente> listaOrdenadaCpf = gerenciarCliente.getClientesOrdenados(new ClienteCpfComparators());
        
        System.out.println("Lista ordenada por CPF:");
        for (int i = 0; i < listaOrdenadaCpf.size(); i++) {
            System.out.println("  [" + i + "] " + listaOrdenadaCpf.get(i).getNome() + " - CPF: " + listaOrdenadaCpf.get(i).getCpf());
        }
        
        System.out.println("\n=== TESTE 3: Busca genérica com binarySearch ===");
        System.out.println("\nBuscando cliente usando método genérico buscarClienteComBinarySearch():");
        Cliente clienteProcuradoGenerico = new Cliente("Ana", "11111111111", "31911111111", StatusAtendimentoCliente.AGENDADO);
        Cliente encontradoGenerico = gerenciarCliente.buscarClienteComBinarySearch(clienteProcuradoGenerico, new ClienteNomeComparators());
        if (encontradoGenerico != null) {
            System.out.println("✓ Cliente encontrado: " + encontradoGenerico.getNome());
        } else {
            System.out.println("✗ Cliente não encontrado!");
        }
        
        System.out.println("\n=== COMPARAÇÃO ENTRE FIND E BINARYSEARCH ===");
        System.out.println("\nMétodo FIND implementado (em GerenciarCliente):");
        System.out.println("- Usa Iterator para percorrer a lista");
        System.out.println("- Usa Comparator para comparar elementos");
        System.out.println("- Complexidade: O(n) - percorre toda a lista no pior caso");
        System.out.println("- Vantagem: Funciona mesmo se a lista não estiver ordenada");
        System.out.println("- Desvantagem: Mais lento para listas grandes");
        
        System.out.println("\nCollections.binarySearch() (métodos em GerenciarCliente):");
        System.out.println("- Usa algoritmo de busca binária");
        System.out.println("- Requer que a lista esteja ORDENADA antes");
        System.out.println("- Usa Comparator para comparar elementos");
        System.out.println("- Complexidade: O(log n) - muito mais eficiente");
        System.out.println("- Retorna: Cliente encontrado ou null");
        System.out.println("- Vantagem: Muito mais rápido para listas grandes");
        System.out.println("- Desvantagem: Requer ordenação prévia (O(n log n))");
        
        System.out.println("\nMétodos disponíveis em GerenciarCliente:");
        System.out.println("- findCliente(cliente, comparator): Busca usando Iterator");
        System.out.println("- buscarClientePorNomeComBinarySearch(cliente): Busca por nome com binarySearch");
        System.out.println("- buscarClientePorCpfComBinarySearch(cliente): Busca por CPF com binarySearch");
        System.out.println("- buscarClienteComBinarySearch(cliente, comparator): Busca genérica com binarySearch");
        System.out.println("- getClientesOrdenados(comparator): Retorna lista ordenada para visualização");
        
        System.out.println("\nConclusão:");
        System.out.println("- Para listas pequenas ou não ordenadas: use findCliente()");
        System.out.println("- Para listas grandes e já ordenadas: use buscarClienteComBinarySearch()");
        System.out.println("- binarySearch é ideal quando você precisa fazer múltiplas buscas na mesma lista ordenada");

        // Questão 18
        System.out.println("\n====Questão 18====");
        System.out.println("Simulação de atendimento de 10 clientes:");
        System.out.println("==========================================");
        
        // Garantir que temos funcionários e serviços
        List<Funcionario> funcionarios = gerenciadorFuncionario.listarFuncionarios();
        if (funcionarios.isEmpty()) {
            Funcionario func1 = new Funcionario("Barbeiro 1", "11111111111", "31911111111", "barbeiro", "barbeiro1", "1234");
            gerenciadorFuncionario.adicionarFuncionario(func1);
            funcionarios = gerenciadorFuncionario.listarFuncionarios();
        }
        
        List<Servico> servicos = gerenciarServico.listar();
        if (servicos.isEmpty()) {
            gerenciarServico.adicionar(new Servico("Corte", 25.0, false, "Corte de cabelo"));
            gerenciarServico.adicionar(new Servico("Barba", 15.0, false, "Aparar barba"));
            servicos = gerenciarServico.listar();
        }
        
        List<Produto> produtos = gerenciadorProduto.listar();
        if (produtos.isEmpty()) {
            gerenciadorProduto.adicionar(new Produto("Pomada", 20.0, 50));
            gerenciadorProduto.adicionar(new Produto("Shampoo", 15.0, 30));
            produtos = gerenciadorProduto.listar();
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataAtual = LocalDate.now().format(formatter);
        
        // Simular atendimento de 10 clientes
        for (int i = 1; i <= 10; i++) {
            System.out.println("\n--- Cliente " + i + " ---");
            
            // 1. Cadastrar cliente
            Cliente cliente = new Cliente("Cliente " + i, 
                                         String.format("%011d", i), 
                                         "319" + String.format("%08d", i), 
                                         StatusAtendimentoCliente.AGENDADO);
            gerenciarCliente.adicionar(cliente);
            System.out.println("✓ Cliente cadastrado: " + cliente.getNome());
            
            // 2. Criar agendamento
            if (!funcionarios.isEmpty() && !servicos.isEmpty()) {
                Funcionario funcionario = funcionarios.get(0);
                Servico servico = servicos.get(0);
                List<Servico> servicosAgendamento = new ArrayList<>();
                servicosAgendamento.add(servico);
                
                String dataHora = dataAtual + " " + String.format("%02d:00", 9 + i);
                Agendamento agendamento = new Agendamento(
                    dataHora,
                    cliente,
                    funcionario,
                    servicosAgendamento,
                    StatusAtendimentoCliente.EM_ATENDIMENTO,
                    1
                );
                gerenciarAgendamento.criarAgendamento(agendamento);
                System.out.println("✓ Agendamento criado para " + dataHora);
                
                // 3. Criar ordem de serviço
                OrdemDeServico ordemServico = new OrdemDeServico(cliente, dataAtual);
                ordemServico.adicionarAgendamento(agendamento);
                System.out.println("✓ Ordem de Serviço criada");
                
                // 4. Realizar venda de produto (se houver produtos)
                if (!produtos.isEmpty() && i % 2 == 0) { // Venda a cada 2 clientes
                    Produto produto = produtos.get(0);
                    if (servicoVenda.efetuarVenda(cliente.getId(), produto.getId(), 1, dataAtual)) {
                        System.out.println("✓ Venda de produto realizada: " + produto.getNome());
                    }
                }
                
                // 5. Gerar nota fiscal
                List<Venda> vendasCliente = gerenciarVenda.listar().stream()
                    .filter(v -> v.getCliente() != null && v.getCliente().getId() == cliente.getId())
                    .toList();
                
                gerenciarNotaFiscal.gerarNotaFiscal(agendamento, vendasCliente);
                System.out.println("✓ Nota Fiscal gerada");
                
                // 6. Atualizar status do cliente
                cliente.setStatusAtendimentoCliente(StatusAtendimentoCliente.ATENDIDO);
                System.out.println("✓ Status do cliente atualizado para ATENDIDO");
            }
        }
        
        // Salvar tudo
        gerenciarCliente.salvarTodosClientes();
        gerenciarAgendamento.salvarTodos();
        gerenciarVenda.salvarTodasVendas();
        gerenciadorProduto.salvarTodosProdutos();
        gerenciadorFuncionario.salvarTodosFuncionarios();
        
        System.out.println("\n==========================================");
        System.out.println("Simulação concluída!");
        System.out.println("Total de clientes atendidos: 10");
        System.out.println("Total de Ordens de Serviço: " + Barbearia.getTotalOrdensDeServico());
        System.out.println("==========================================");
    }
}
