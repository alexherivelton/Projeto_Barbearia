package xela.chris.barbearia.negocio;

import xela.chris.barbearia.FacadeMediator.AgendamentoFacade;
import xela.chris.barbearia.Gerenciadores.*;
import xela.chris.barbearia.Security.Proxy;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.NotaFiscal;
import xela.chris.barbearia.models.OrdemDeServico;
import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.models.Venda;
import xela.chris.barbearia.negocio.Agendamento;
import xela.chris.barbearia.servicos.ServicoOrdemServico;
import xela.chris.barbearia.servicos.ServicoVenda;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal para demonstração e teste do sistema Barbearia,
 * simulando um menu interativo com controle de acesso por permissões.
 *
 * Baseada nos gerenciadores e classes do projeto.
 */
public class Barbearia {

    private static final Scanner scanner = new Scanner(System.in);
    private static Proxy acesso;
    private static Funcionario usuarioLogado;

    // Inicialização dos Gerenciadores e Serviços
    private static final GerenciadorFuncionario gerenciadorFuncionario = new GerenciadorFuncionario();
    private static final GerenciarCliente gerenciarCliente = new GerenciarCliente();
    private static final GerenciarServico gerenciarServico = new GerenciarServico();
    private static final GerenciadorProduto gerenciadorProduto = new GerenciadorProduto();
    private static final GerenciarAgendamento gerenciarAgendamento = new GerenciarAgendamento();
    private static final GerenciarNotaFiscal gerenciarNotaFiscal = new GerenciarNotaFiscal();
    private static final GerenciarVenda gerenciarVenda = new GerenciarVenda();
    private static final GerenciadorPonto gerenciadorPonto = new GerenciadorPonto();
    private static final GerenciadorBalanco gerenciadorBalanco = new GerenciadorBalanco(gerenciarAgendamento, gerenciarVenda);
    private static final AgendamentoFacade agendamentoFacade = new AgendamentoFacade();
    private static final ServicoVenda servicoVenda = new ServicoVenda(gerenciadorProduto, gerenciarVenda, gerenciarCliente);
    private static final ServicoOrdemServico servicoOrdemServico = new ServicoOrdemServico(gerenciarAgendamento, gerenciarVenda);

    /**
     * Método principal do sistema de barbearia.
     *
     * <p>Inicializa o sistema, realiza o login do usuário e exibe
     * o menu principal com as opções disponíveis de acordo com as
     * permissões do funcionário logado.</p>
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        // Inicializa o Proxy com o Gerenciador de Funcionários
        acesso = new Proxy(gerenciadorFuncionario);
        System.out.println("=== SISTEMA BARBEARIA - LOGIN ===");

        if (realizarLogin()) {
            System.out.println("\nBem-vindo(a), " + usuarioLogado.getNome() + " (" + usuarioLogado.getCargo() + ")!");
            exibirMenuPrincipal();
        } else {
            System.out.println("\nO sistema será encerrado.");
        }
        scanner.close();
    }

    private static boolean realizarLogin() {
        System.out.print("Usuário: ");
        String user = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (acesso.login(user, senha)) {
            usuarioLogado = acesso.getUsuarioLogado();
            return true;
        }
        return false;
    }

    private static void exibirMenuPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=================================");
            System.out.println("            MENU PRINCIPAL");
            System.out.println("=================================");
            System.out.println("1. Agendamentos");
            System.out.println("2. Clientes");
            System.out.println("3. Serviços e Produtos");
            System.out.println("4. Funcionários (Administrativo)");
            System.out.println("5. Ponto Eletrônico");
            System.out.println("6. Vendas e Nota Fiscal");
            System.out.println("7. Relatórios (Ordem de Serviço/Balanço)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        menuAgendamentos();
                        break;
                    case 2:
                        menuClientes();
                        break;
                    case 3:
                        menuServicosEProdutos();
                        break;
                    case 4:
                        menuFuncionarios();
                        break;
                    case 5:
                        baterPonto();
                        break;
                    case 6:
                        menuVendasENotaFiscal();
                        break;
                    case 7:
                        menuRelatorios();
                        break;
                    case 0:
                        System.out.println("Até logo, " + usuarioLogado.getNome() + "!");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    // ====================================================================
    // 1. AGENDAMENTOS
    // ====================================================================

    private static void menuAgendamentos() {
        // Verifica se tem permissão para CRIAR ou VERIFICAR
        if (!acesso.temPermissao(PermissoesEnum.CRIAR_AGENDAMENTO) && !acesso.temPermissao(PermissoesEnum.VERIFICAR_AGENDA)) {
            System.out.println("Você não tem permissão para acessar o menu de Agendamentos.");
            return;
        }

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU AGENDAMENTOS ---");
            if (acesso.temPermissao(PermissoesEnum.CRIAR_AGENDAMENTO)) {
                System.out.println("1. Criar Novo Agendamento");
            }
            if (acesso.temPermissao(PermissoesEnum.VERIFICAR_AGENDA)) {
                System.out.println("2. Listar Agendamentos Ordenados por Data");
                System.out.println("3. Buscar Agendamento por ID");
            }
            if (acesso.temPermissao(PermissoesEnum.CRIAR_AGENDAMENTO)) {
                System.out.println("4. Excluir Agendamento por ID");
                System.out.println("5. Finalizar Agendamento (gera Nota Fiscal automaticamente)");
            }
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        if (acesso.temPermissao(PermissoesEnum.CRIAR_AGENDAMENTO)) criarAgendamento();
                        break;
                    case 2:
                        if (acesso.temPermissao(PermissoesEnum.VERIFICAR_AGENDA)) {
                            gerenciarAgendamento.carregar(); // Recarrega antes de listar
                            agendamentoFacade.listarAgendamentosOrdenadosPorData();
                        }
                        break;
                    case 3:
                        if (acesso.temPermissao(PermissoesEnum.VERIFICAR_AGENDA)) buscarAgendamento();
                        break;
                    case 4:
                        if (acesso.temPermissao(PermissoesEnum.CRIAR_AGENDAMENTO)) excluirAgendamento();
                        break;
                    case 5:
                        if (acesso.temPermissao(PermissoesEnum.CRIAR_AGENDAMENTO)) finalizarAgendamento();
                        break;
                    case 0:
                        agendamentoFacade.salvarAgendamentos();
                        System.out.println("Retornando ao Menu Principal.");
                        break;
                    default:
                        if (opcao != 0) System.out.println("Opção inválida ou sem permissão.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    private static void criarAgendamento() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarCliente.carregar();
            gerenciadorFuncionario.carregar();
            gerenciarServico.carregar();

            System.out.print("ID do Cliente: ");
            int idCliente = Integer.parseInt(scanner.nextLine());

            Cliente cliente = gerenciarCliente.buscarCliente(idCliente);
            if (cliente == null) {
                System.out.println("Cliente com ID " + idCliente + " não encontrado.");
                return;
            }
            System.out.println("Cliente Selecionado: " + cliente.getNome());

            System.out.print("ID do Funcionário: ");
            int idFuncionario = Integer.parseInt(scanner.nextLine());

            System.out.print("ID do Serviço: ");
            int idServico = Integer.parseInt(scanner.nextLine());

            Servico servico = gerenciarServico.buscarPorId(idServico);
            if (servico == null) {
                System.out.println("Serviço com ID " + idServico + " não encontrado.");
                return;
            }
            System.out.printf("Servico Selecionado: %s - R$ %.2f\n", servico.getNome(), servico.getPreco());

            System.out.print("Data e Hora (Ex: dd/MM/yyyy HH:mm): ");
            String dataHora = scanner.nextLine();

            boolean sucesso = agendamentoFacade.criarAgendamento(idCliente, idFuncionario, idServico, dataHora);
            if (sucesso) {
                agendamentoFacade.salvarAgendamentos(); // Salva o agendamento imediatamente
                System.out.println("Agendamento realizado com sucesso e salvo no JSON!");
            } else {
                System.out.println("Falha ao criar agendamento. Verifique as IDs, disponibilidade do funcionário ou da cadeira.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Certifique-se de digitar números para os IDs.");
        }
    }

    private static void buscarAgendamento() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarAgendamento.carregar();

            System.out.print("ID do Agendamento a buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Agendamento ag = agendamentoFacade.buscarAgendamento(id);
            if (ag != null) {
                System.out.println(ag);
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void excluirAgendamento() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarAgendamento.carregar();

            System.out.print("ID do Agendamento a excluir: ");
            int id = Integer.parseInt(scanner.nextLine());
            if (agendamentoFacade.excluirAgendamento(id)) {
                System.out.println("✅ Agendamento excluído e lista salva.");
                agendamentoFacade.salvarAgendamentos();
            } else {
                System.out.println("Falha ao excluir agendamento.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void finalizarAgendamento() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarAgendamento.carregar();
            gerenciarNotaFiscal.carregar();
            gerenciarVenda.carregar();

            System.out.print("ID do Agendamento a finalizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            boolean sucesso = gerenciarAgendamento.finalizarAgendamento(id, gerenciarNotaFiscal, gerenciarVenda);
            if (sucesso) {
                agendamentoFacade.salvarAgendamentos();
            } else {
                System.out.println("Falha ao finalizar agendamento.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    // ====================================================================
    // 2. CLIENTES
    // ====================================================================

    private static void menuClientes() {
        // Verifica se tem permissão para CADASTRAR ou VERIFICAR
        if (!acesso.temPermissao(PermissoesEnum.CADASTRAR_CLIENTE) && !acesso.temPermissao(PermissoesEnum.VERIFICAR_CLIENTE)) {
            System.out.println("Você não tem permissão para acessar o menu de Clientes.");
            return;
        }

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU CLIENTES ---");
            if (acesso.temPermissao(PermissoesEnum.CADASTRAR_CLIENTE)) {
                System.out.println("1. Cadastrar Novo Cliente");
                System.out.println("2. Atualizar Cliente por ID");
                System.out.println("3. Remover Cliente por ID");
            }
            if (acesso.temPermissao(PermissoesEnum.VERIFICAR_CLIENTE)) {
                System.out.println("4. Listar Todos os Clientes");
                System.out.println("5. Buscar Cliente por ID");
            }
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        if (acesso.temPermissao(PermissoesEnum.CADASTRAR_CLIENTE)) cadastrarCliente();
                        break;
                    case 2:
                        if (acesso.temPermissao(PermissoesEnum.CADASTRAR_CLIENTE)) atualizarCliente();
                        break;
                    case 3:
                        if (acesso.temPermissao(PermissoesEnum.CADASTRAR_CLIENTE)) removerCliente();
                        break;
                    case 4:
                        if (acesso.temPermissao(PermissoesEnum.VERIFICAR_CLIENTE)) {
                            gerenciarCliente.carregar(); // Recarrega antes de listar
                            gerenciarCliente.listar();
                        }
                        break;
                    case 5:
                        if (acesso.temPermissao(PermissoesEnum.VERIFICAR_CLIENTE)) buscarCliente();
                        break;
                    case 0:
                        gerenciarCliente.salvarTodosClientes();
                        System.out.println("Retornando ao Menu Principal.");
                        break;
                    default:
                        if (opcao != 0) System.out.println("Opção inválida ou sem permissão.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF (somente números): ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone (somente números): ");
        String telefone = scanner.nextLine();

        // O status inicial será AGENDADO, conforme o construtor Cliente.java
        Cliente novoCliente = new Cliente(nome, cpf, telefone, StatusAtendimentoCliente.AGENDADO);
        gerenciarCliente.adicionar(novoCliente);
        gerenciarCliente.salvarTodosClientes();
        System.out.println("Cliente cadastrado com sucesso! ID: " + novoCliente.getId());
    }

    private static void buscarCliente() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarCliente.carregar();

            System.out.print("ID do Cliente a buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Cliente cliente = gerenciarCliente.buscarCliente(id);
            if (cliente != null) {
                System.out.println(cliente);
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void atualizarCliente() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarCliente.carregar();

            System.out.print("ID do Cliente a atualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println("Deixe em branco para não alterar.");
            System.out.print("Novo Nome: ");
            String novoNome = scanner.nextLine();
            System.out.print("Novo CPF: ");
            String novoCpf = scanner.nextLine();
            System.out.print("Novo Telefone: ");
            String novoTelefone = scanner.nextLine();

            // O método atualizarCliente aceita null para manter o valor atual
            boolean sucesso = gerenciarCliente.atualizarCliente(id,
                    novoNome.isEmpty() ? null : novoNome,
                    novoCpf.isEmpty() ? null : novoCpf,
                    novoTelefone.isEmpty() ? null : novoTelefone,
                    null); // Mantém o status

            if (sucesso) {
                System.out.println("Cliente atualizado.");
                gerenciarCliente.salvarTodosClientes();
            } else {
                System.out.println("Falha ao atualizar cliente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void removerCliente() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarCliente.carregar();

            System.out.print("ID do Cliente a remover: ");
            int id = Integer.parseInt(scanner.nextLine());
            if (gerenciarCliente.removerPorId(id)) {
                System.out.println("Cliente removido e lista salva.");
                gerenciarCliente.salvarTodosClientes();
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    // ====================================================================
    // 3. SERVIÇOS E PRODUTOS
    // ====================================================================

    private static void menuServicosEProdutos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU SERVIÇOS E PRODUTOS ---");
            System.out.println("1. Gerenciar Serviços (CRUD)");
            System.out.println("2. Gerenciar Produtos (CRUD)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        menuServicos();
                        break;
                    case 2:
                        menuProdutos();
                        break;
                    case 0:
                        System.out.println("Retornando ao Menu Principal.");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    private static void menuServicos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gerenciar Serviços ---");
            System.out.println("1. Listar Todos os Serviços");
            if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) { // Permissão administrativa/gerencial
                System.out.println("2. Adicionar Novo Serviço");
                System.out.println("3. Atualizar Serviço por ID");
                System.out.println("4. Remover Serviço por ID");
                System.out.println("5. Buscar Serviço por ID");
            }
            System.out.println("0. Voltar ao Menu Anterior");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        gerenciarServico.carregar(); // Recarrega antes de listar
                        gerenciarServico.listar();
                        break;
                    case 2:
                        if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) adicionarServico();
                        break;
                    case 3:
                        if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) atualizarServico();
                        break;
                    case 4:
                        if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) removerServico();
                        break;
                    case 5:
                        if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) buscarServico();
                        break;
                    case 0:
                        gerenciarServico.salvarTodosServicos();
                        break;
                    default:
                        if (opcao != 0) System.out.println("Opção inválida ou sem permissão.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    private static void adicionarServico() {
        try {
            System.out.print("Nome do Serviço: ");
            String nome = scanner.nextLine();
            System.out.print("Preço: ");
            double preco = Double.parseDouble(scanner.nextLine());
            System.out.print("Utiliza Lavagem/Secagem (true/false): ");
            boolean utilizaLavagemSecagem = Boolean.parseBoolean(scanner.nextLine());
            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();

            gerenciarServico.adicionar(new Servico(nome, preco, utilizaLavagemSecagem, descricao));
            gerenciarServico.salvarTodosServicos();
            System.out.println("Serviço adicionado e salvo.");
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Certifique-se de digitar um valor numérico para o preço.");
        }
    }

    private static void atualizarServico() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarServico.carregar();

            System.out.print("ID do Serviço a atualizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            // Buscar serviço para obter valores atuais
            Servico s = gerenciarServico.buscarPorId(id);
            if (s == null) {
                System.out.println("Serviço não encontrado.");
                return;
            }

            System.out.println("Deixe em branco para não alterar.");
            System.out.print("Novo Nome: ");
            String novoNome = scanner.nextLine();
            System.out.print("Novo Preço (digite um número): ");
            double novoPreco = Double.parseDouble(scanner.nextLine());
            System.out.print("Nova Descrição: ");
            String novaDescricao = scanner.nextLine();

            // O método atualizar exige que todos os campos sejam passados, usando o valor original caso o novo esteja vazio/seja 0.0
            boolean sucesso = gerenciarServico.atualizar(id,
                    novoNome.isEmpty() ? s.getNome() : novoNome,
                    novoPreco,
                    novaDescricao.isEmpty() ? s.getDescricao() : novaDescricao);

            if (sucesso) {
                System.out.println("Serviço atualizado.");
                gerenciarServico.salvarTodosServicos();
            } else {
                System.out.println("Falha ao atualizar serviço.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID/Preço.");
        }
    }

    private static void removerServico() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarServico.carregar();

            System.out.print("ID do Serviço a remover: ");
            int id = Integer.parseInt(scanner.nextLine());
            if (gerenciarServico.removerPorId(id)) {
                System.out.println("Serviço removido e lista salva.");
                gerenciarServico.salvarTodosServicos();
            } else {
                System.out.println("Serviço não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void buscarServico() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarServico.carregar();

            System.out.print("ID do Serviço a buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Servico s = gerenciarServico.buscarPorId(id);
            if (s != null) {
                System.out.println(s);
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void menuProdutos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gerenciar Produtos ---");
            System.out.println("1. Listar Todos os Produtos");
            if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) { // Permissão administrativa/gerencial
                System.out.println("2. Adicionar Novo Produto");
                System.out.println("3. Atualizar Estoque (Define Nova Quantidade Total)");
                System.out.println("4. Buscar Produto por ID");
                System.out.println("5. Remover Produto (Nota: O método 'removerPorCpf' usa ID, use o ID)");
            }
            System.out.println("0. Voltar ao Menu Anterior");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        gerenciadorProduto.carregar(); // Recarrega antes de listar
                        gerenciadorProduto.listar().forEach(System.out::println);
                        break;
                    case 2:
                        if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) adicionarProduto();
                        break;
                    case 3:
                        if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) atualizarEstoque();
                        break;
                    case 4:
                        if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) buscarProduto();
                        break;
                    case 5:
                        if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) removerProduto();
                        break;
                    case 0:
                        gerenciadorProduto.salvarTodosProdutos();
                        break;
                    default:
                        if (opcao != 0) System.out.println("Opção inválida ou sem permissão.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    private static void adicionarProduto() {
        try {
            System.out.print("Nome do Produto: ");
            String nome = scanner.nextLine();
            System.out.print("Valor: ");
            double valor = Double.parseDouble(scanner.nextLine());
            System.out.print("Quantidade Inicial: ");
            int quantidade = Integer.parseInt(scanner.nextLine());

            gerenciadorProduto.adicionar(new xela.chris.barbearia.models.Produto(nome, valor, quantidade));
            gerenciadorProduto.salvarTodosProdutos();
            System.out.println("Produto adicionado e salvo.");
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Certifique-se de digitar um valor numérico para Valor/Quantidade.");
        }
    }

    private static void atualizarEstoque() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciadorProduto.carregar();

            System.out.print("ID do Produto: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Nova Quantidade TOTAL no estoque: ");
            int novaQuantidade = Integer.parseInt(scanner.nextLine());

            xela.chris.barbearia.models.Produto p = gerenciadorProduto.buscarPorId(id);
            if (p != null) {
                p.setQuantidade(novaQuantidade); // Usa o setter para definir a nova quantidade
                gerenciadorProduto.salvarTodosProdutos();
                System.out.println("Estoque do produto " + p.getNome() + " atualizado para: " + novaQuantidade);
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID/Quantidade.");
        }
    }

    private static void buscarProduto() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciadorProduto.carregar();

            System.out.print("ID do Produto a buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            xela.chris.barbearia.models.Produto p = gerenciadorProduto.buscarPorId(id);
            if (p != null) {
                System.out.println(p);
            } else {
                System.out.println("Produto não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void removerProduto() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciadorProduto.carregar();

            System.out.print("ID do Produto a remover: ");
            String idStr = scanner.nextLine();
            // O método removerPorCpf na classe GerenciadorProduto está incorreto e usa uma String como entrada para o ID.
            if (gerenciadorProduto.removerPorCpf(idStr)) {
                System.out.println("Produto removido (via método 'removerPorCpf' que usa ID).");
                gerenciadorProduto.salvarTodosProdutos();
            } else {
                System.out.println("Falha ao remover produto.");
            }
        } catch (Exception e) {
            System.out.println("Erro de entrada.");
        }
    }


    // ====================================================================
    // 4. FUNCIONÁRIOS
    // ====================================================================

    private static void menuFuncionarios() {
        // CAD_FUNC: Cadastrar Funcionário (geralmente Admin)
        if (!acesso.temPermissao(PermissoesEnum.CAD_FUNC)) {
            System.out.println("Você não tem permissão para acessar o Menu Administrativo de Funcionários.");
            return;
        }

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU FUNCIONÁRIOS (ADMIN) ---");
            System.out.println("1. Cadastrar Novo Funcionário");
            System.out.println("2. Atualizar Funcionário por ID");
            System.out.println("3. Remover Funcionário por ID");
            System.out.println("4. Listar Todos os Funcionários");
            System.out.println("5. Buscar Funcionário por ID");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        cadastrarFuncionario();
                        break;
                    case 2:
                        atualizarFuncionario();
                        break;
                    case 3:
                        removerFuncionario();
                        break;
                    case 4:
                        gerenciadorFuncionario.carregar(); // Recarrega antes de listar
                        gerenciadorFuncionario.listarFuncionarios().forEach(System.out::println);
                        break;
                    case 5:
                        buscarFuncionario();
                        break;
                    case 0:
                        gerenciadorFuncionario.salvarTodosFuncionarios();
                        System.out.println("Retornando ao Menu Principal.");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    private static void cadastrarFuncionario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Cargo (atendente, barbeiro, administrador): ");
        String cargo = scanner.nextLine();
        System.out.print("Usuário para Login: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Funcionario novoFunc = new Funcionario(nome, cpf, telefone, cargo, usuario, senha);
        gerenciadorFuncionario.adicionarFuncionario(novoFunc);
        gerenciadorFuncionario.salvarTodosFuncionarios();
        System.out.println("✅ Funcionário cadastrado! ID: " + novoFunc.getId());
    }

    private static void atualizarFuncionario() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciadorFuncionario.carregar();

            System.out.print("ID do Funcionário a atualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println("Deixe em branco para não alterar.");

            System.out.print("Novo Nome: ");
            String novoNome = scanner.nextLine();
            System.out.print("Novo CPF: ");
            String novoCpf = scanner.nextLine();
            System.out.print("Novo Telefone: ");
            String novoTelefone = scanner.nextLine();
            System.out.print("Novo Cargo: ");
            String novoCargo = scanner.nextLine();
            System.out.print("Novo Usuário: ");
            String novoUsuario = scanner.nextLine();
            System.out.print("Nova Senha: ");
            String novaSenha = scanner.nextLine();

            boolean sucesso = gerenciadorFuncionario.atualizarFuncionario(id,
                    novoNome.isEmpty() ? null : novoNome,
                    novoCpf.isEmpty() ? null : novoCpf,
                    novoTelefone.isEmpty() ? null : novoTelefone,
                    novoCargo.isEmpty() ? null : novoCargo,
                    novoUsuario.isEmpty() ? null : novoUsuario,
                    novaSenha.isEmpty() ? null : novaSenha);

            if (sucesso) {
                gerenciadorFuncionario.salvarTodosFuncionarios();
                System.out.println("Funcionário atualizado e lista salva.");
            } else {
                System.out.println("Falha ao atualizar funcionário.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void removerFuncionario() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciadorFuncionario.carregar();

            System.out.print("ID do Funcionário a remover: ");
            int id = Integer.parseInt(scanner.nextLine());
            gerenciadorFuncionario.removerFuncionario(id);
            gerenciadorFuncionario.salvarTodosFuncionarios();
            System.out.println("Funcionário removido e lista salva (Verifique se o ID existia).");
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void buscarFuncionario() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciadorFuncionario.carregar();

            System.out.print("ID do Funcionário a buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Funcionario f = gerenciadorFuncionario.buscarFuncionario(id);
            if (f != null) {
                System.out.println(f);
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }


    private static void baterPonto() {
        // Assume-se que qualquer funcionário logado pode bater ponto
        if (usuarioLogado == null) {
            System.out.println("É necessário estar logado para bater o ponto.");
            return;
        }

        System.out.println("\n--- PONTO ELETRÔNICO ---");
        gerenciadorPonto.baterPonto(usuarioLogado);
        gerenciadorPonto.salvarPonto();
        System.out.println("Ponto registrado/salvo. (Verifique console para entrada/saída)");

        System.out.println("Deseja buscar um ponto anterior?");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        System.out.print("Opção: ");
        try {
            int op = Integer.parseInt(scanner.nextLine());
            if (op == 1) {
                System.out.print("Data a buscar (Ex: 2025-11-15): ");
                String data = scanner.nextLine();
                gerenciadorPonto.buscarPorData(usuarioLogado, data);
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }


    private static void menuVendasENotaFiscal() {
        if (!acesso.temPermissao(PermissoesEnum.GERAR_NOTA)) { //
            System.out.println("Você não tem permissão para acessar o menu de Vendas e Nota Fiscal.");
            return;
        }

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU VENDAS E NOTA FISCAL ---");
            System.out.println("1. Realizar Venda de Produto");
            System.out.println("2. Gerar Nota Fiscal de Agendamento");
            System.out.println("3. Listar Todas as Vendas");
            System.out.println("4. Listar Todas as Notas Fiscais");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        realizarVenda();
                        break;
                    case 2:
                        gerarNotaFiscal();
                        break;
                    case 3:
                        gerenciarVenda.carregar(); // Recarrega antes de listar
                        gerenciarVenda.listar().forEach(System.out::println);
                        break;
                    case 4:
                        gerenciarNotaFiscal.carregar(); // Recarrega antes de listar
                        gerenciarNotaFiscal.listar().forEach(System.out::println);
                        break;
                    case 0:
                        gerenciarVenda.salvarTodasVendas();
                        System.out.println("Retornando ao Menu Principal.");
                        break;
                    default:
                        if (opcao != 0) System.out.println("Opção inválida ou sem permissão.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    private static void realizarVenda() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarCliente.carregar();
            gerenciadorProduto.carregar();
            gerenciarVenda.carregar();
            gerenciarAgendamento.carregar();
            gerenciarNotaFiscal.carregar();

            System.out.print("ID do Cliente: ");
            int idCliente = Integer.parseInt(scanner.nextLine());
            System.out.print("ID do Produto: ");
            int idProduto = Integer.parseInt(scanner.nextLine());
            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());

            LocalDate data = LocalDate.now();
            DateTimeFormatter formatarData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = data.format(formatarData);

            if (servicoVenda.efetuarVenda(idCliente, idProduto, quantidade, dataFormatada)) {
                System.out.println("Venda realizada com sucesso!");
                gerenciarVenda.salvarTodasVendas(); // Salva a venda imediatamente
                gerenciadorProduto.salvarTodosProdutos(); // Salva a atualização de estoque

                // Busca a venda recém-criada para gerar nota fiscal
                gerenciarVenda.carregar();
                List<Venda> todasVendas = gerenciarVenda.listar();
                Venda vendaRecente = todasVendas.stream()
                        .filter(v -> v.getCliente() != null && v.getCliente().getId() == idCliente)
                        .filter(v -> v.getProduto() != null && v.getProduto().getId() == idProduto)
                        .filter(v -> v.getQuantidade() == quantidade)
                        .filter(v -> v.getDataVenda().equals(dataFormatada))
                        .max((v1, v2) -> Integer.compare(v1.getId(), v2.getId())) // Pega a mais recente (maior ID)
                        .orElse(null);

                // Verifica se há um agendamento do mesmo cliente que ainda não foi finalizado
                if (vendaRecente != null) {
                    List<Agendamento> agendamentosCliente = gerenciarAgendamento.listarAgendamentosOrdenadosPorData()
                            .stream()
                            .filter(ag -> ag.getCliente() != null && ag.getCliente().getId() == idCliente)
                            .filter(ag -> ag.getStatusCliente() != StatusAtendimentoCliente.ATENDIDO)
                            .toList();

                    // Se houver agendamento não finalizado, gera nota fiscal automaticamente
                    if (!agendamentosCliente.isEmpty()) {
                        // Pega o agendamento mais recente
                        Agendamento agendamentoRecente = agendamentosCliente.get(agendamentosCliente.size() - 1);

                        // Busca todas as vendas do cliente que ainda não foram vinculadas a notas fiscais
                        List<NotaFiscal> todasNotas = gerenciarNotaFiscal.listar();
                        List<Venda> vendasCliente = gerenciarVenda.listar().stream()
                                .filter(v -> v.getCliente() != null && v.getCliente().getId() == idCliente)
                                .filter(v -> {
                                    // Verifica se a venda já está em alguma nota fiscal
                                    return todasNotas.stream()
                                            .noneMatch(nota -> nota.getVendasProdutos() != null &&
                                                    nota.getVendasProdutos().contains(v));
                                })
                                .toList();

                        // Gera nota fiscal automaticamente
                        NotaFiscal nota = gerenciarNotaFiscal.gerarNotaFiscal(agendamentoRecente, vendasCliente);
                        if (nota != null) {
                            System.out.println("Nota Fiscal gerada automaticamente após a venda!");
                            System.out.println("ID da Nota Fiscal: " + nota.getId());
                            System.out.println("Valor Total: R$ " + String.format("%.2f", nota.getValorTotal()));

                            // Finaliza o agendamento automaticamente
                            agendamentoRecente.setStatusCliente(StatusAtendimentoCliente.ATENDIDO);
                            gerenciarAgendamento.salvarTodos();
                        }
                    }
                }
            } else {
                System.out.println("Falha na venda. Verifique ID do cliente/produto ou estoque.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Certifique-se de digitar números para os IDs/Quantidade.");
        }
    }

    private static void gerarNotaFiscal() {
        try {
            // Recarrega dados para garantir que estejam atualizados
            gerenciarAgendamento.carregar();
            gerenciarVenda.carregar();
            gerenciarNotaFiscal.carregar();

            System.out.print("ID do Agendamento para gerar Nota Fiscal: ");
            int idAgendamento = Integer.parseInt(scanner.nextLine());

            Agendamento agendamento = agendamentoFacade.buscarAgendamento(idAgendamento);
            if (agendamento == null) {
                System.out.println("Agendamento não encontrado.");
                return;
            }

            // Simplesmente obtém todas as vendas do cliente (idealmente, as não vinculadas a notas)
            Cliente clienteAgendamento = agendamento.getCliente();
            List<Venda> vendasCliente = gerenciarVenda.listar().stream()
                    .filter(v -> v.getCliente() != null && clienteAgendamento != null && v.getCliente().getId() == clienteAgendamento.getId())
                    .toList();

            // O método gerarNotaFiscal já salva automaticamente no JSON através do método adicionar()
            NotaFiscal nota = gerenciarNotaFiscal.gerarNotaFiscal(agendamento, vendasCliente);
            if (nota != null) {
                System.out.println("Nota Fiscal gerada e salva no JSON!");
                System.out.println("ID da Nota Fiscal: " + nota.getId());
            } else {
                System.out.println("Erro ao gerar nota fiscal.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void menuRelatorios() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU RELATÓRIOS ---");
            if (acesso.temPermissao(PermissoesEnum.VERIFICAR_AGENDA)) { // Permissão para visualizar
                System.out.println("1. Ordem de Serviço por Cliente");
                System.out.println("2. Ordem de Serviço por Data");
            }
            if (acesso.temPermissao(PermissoesEnum.GERAR_BALANCO_MENSAL)) {
                System.out.println("3. Calcular Total de Vendas");
                System.out.println("4. Gerar Balanço Financeiro (por Data)");
                System.out.println("5. Gerar Balanço Financeiro (por Mês)");
                System.out.println("6. Gerar Balanço Financeiro Completo (Todas as Vendas)");
            }
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        if (acesso.temPermissao(PermissoesEnum.VERIFICAR_AGENDA)) relatorioOrdemServicoCliente();
                        break;
                    case 2:
                        if (acesso.temPermissao(PermissoesEnum.VERIFICAR_AGENDA)) relatorioOrdemServicoData();
                        break;
                    case 3:
                        if (acesso.temPermissao(PermissoesEnum.GERAR_BALANCO_MENSAL)) relatorioTotalVendas();
                        break;
                    case 4:
                        if (acesso.temPermissao(PermissoesEnum.GERAR_BALANCO_MENSAL)) gerarBalancoPorData();
                        break;
                    case 5:
                        if (acesso.temPermissao(PermissoesEnum.GERAR_BALANCO_MENSAL)) gerarBalancoPorMes();
                        break;
                    case 6:
                        if (acesso.temPermissao(PermissoesEnum.GERAR_BALANCO_MENSAL)) gerarBalancoCompleto();
                        break;
                    case 0:
                        System.out.println("Retornando ao Menu Principal.");
                        break;
                    default:
                        if (opcao != 0) System.out.println("Opção inválida ou sem permissão.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    private static void relatorioOrdemServicoCliente() {
        try {
            System.out.print("ID do Cliente para Ordem de Serviço: ");
            int idCliente = Integer.parseInt(scanner.nextLine());
            servicoOrdemServico.imprimirPorCliente(idCliente);
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada. Digite um número para o ID.");
        }
    }

    private static void relatorioOrdemServicoData() {
        System.out.print("Data (Ex: dd/MM/yyyy) para Ordem de Serviço: ");
        String data = scanner.nextLine();
        servicoOrdemServico.imprimirPorData(data);
    }

    private static void relatorioTotalVendas() {
        // Garante que os dados estão atualizados
        gerenciarVenda.carregar();
        double total = servicoVenda.calcularTotalVendas();
        System.out.println("\n=========================================");
        System.out.println("      TOTAL DE VENDAS DE PRODUTOS");
        System.out.println("=========================================");
        System.out.printf("Total arrecadado: R$ %.2f\n", total);
        System.out.println("=========================================");
    }

    private static void gerarBalancoPorData() {
        try {
            System.out.print("Digite a data para o balanço (Ex: 15/11/2025): ");
            String data = scanner.nextLine();

            // Garante que os dados estão atualizados antes de calcular
            gerenciarAgendamento.carregar();
            gerenciarVenda.carregar();

            gerenciadorBalanco.gerarBalanco(data);

            System.out.println("\nBalanço gerado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao gerar balanço: " + e.getMessage());
        }
    }

    private static void gerarBalancoPorMes() {
        try {
            System.out.print("Digite o mês/ano para o balanço (Ex: 11/2025): ");
            String mesAno = scanner.nextLine();

            // Garante que os dados estão atualizados antes de calcular
            gerenciarAgendamento.carregar();
            gerenciarVenda.carregar();

            gerenciadorBalanco.gerarBalanco(mesAno);

            System.out.println("\nBalanço mensal gerado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao gerar balanço: " + e.getMessage());
        }
    }

    private static void gerarBalancoCompleto() {
        try {
            // Garante que os dados estão atualizados antes de calcular
            gerenciarAgendamento.carregar();
            gerenciarVenda.carregar();

            // Calcula totais sem filtro (todos os registros)
            double totalServicos = gerenciadorBalanco.calcularTotalServicos("");
            double totalProdutos = gerenciadorBalanco.calcularTotalProdutos("");
            double totalGeral = totalServicos + totalProdutos;

            System.out.println("\n=========================================");
            System.out.println("      BALANÇO FINANCEIRO COMPLETO");
            System.out.println("      (Todas as Vendas e Serviços)");
            System.out.println("=========================================");
            System.out.printf("Total em Serviços:   R$ %.2f%n", totalServicos);
            System.out.printf("Total em Produtos:   R$ %.2f%n", totalProdutos);
            System.out.println("-----------------------------------------");
            System.out.printf("TOTAL GERAL:         R$ %.2f%n", totalGeral);
            System.out.println("=========================================");

            System.out.println("\nBalanço completo gerado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao gerar balanço: " + e.getMessage());
        }
    }

    /**
     * Questão 12: Método de classe que retorna quantas instâncias foram criadas do tipo OrdemDeServico.
     * @return número total de ordens de serviço criadas
     */
    public static int getTotalOrdensDeServico() {
        return OrdemDeServico.getTotalOS();
    }
}
