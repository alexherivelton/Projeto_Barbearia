package xela.chris.barbearia.servicos;

import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarVenda;
import xela.chris.barbearia.Gerenciadores.RepositorioJson;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Venda;
import xela.chris.barbearia.negocio.Agendamento;
import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.OrdemDeServico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerar relatórios baseados em um novo conceito
 * simplificado de Ordem de Serviço (OS), utilizando apenas dados brutos
 * (IDs, CPFs, Valores) extraídos de Agendamentos e Vendas.
 *
 * Esta classe também atua como Repositório/Gerenciador para persistir as OS.
 */
public class ServicoOrdemServico {

    private final GerenciarAgendamento gerenciarAgendamento;
    private final GerenciarVenda gerenciarVenda;

    // Lista e Repositório para gerenciar a persistência das Ordens de Serviço
    private List<OrdemDeServico> ordensDeServico = new ArrayList<>();
    private final RepositorioJson<OrdemDeServico> repoOS =
            new RepositorioJson<>(OrdemDeServico.class, "ordensDeServico.json");

    /**
     * Construtor com injeção de dependência.
     */
    public ServicoOrdemServico(GerenciarAgendamento gerenciarAgendamento, GerenciarVenda gerenciarVenda) {
        this.gerenciarAgendamento = gerenciarAgendamento;
        this.gerenciarVenda = gerenciarVenda;
        this.carregar(); // Carrega as OS na inicialização
    }

    /**
     * Construtor padrão.
     */
    public ServicoOrdemServico() {
        this(new GerenciarAgendamento(), new GerenciarVenda());
    }

    /**
     * Carrega (ou recarrega) todas as ordens de serviço do arquivo JSON.
     */
    public void carregar() {
        ordensDeServico = repoOS.buscarTodos();
        if (ordensDeServico == null) {
            ordensDeServico = new ArrayList<>();
            return;
        }
        if (!ordensDeServico.isEmpty()) {
            int maiorId = ordensDeServico.stream()
                    .mapToInt(OrdemDeServico::getId)
                    .max()
                    .orElse(0);
            OrdemDeServico.atualizarContador(maiorId);
        }
    }

    /**
     * Salva a lista de ordens de serviço atualmente em memória no arquivo JSON.
     */
    public void salvarTodos(){
        repoOS.salvarTodos(ordensDeServico);
    }

    /**
     * Adiciona uma lista de novas ordens de serviço à lista em memória e persiste
     * imediatamente a lista atualizada no arquivo JSON.
     *
     * @param oss A lista de {@link OrdemDeServico} a ser adicionada e salva.
     */
    public void adicionarTodos(List<OrdemDeServico> oss) {
        if (oss != null && !oss.isEmpty()) {
            ordensDeServico.addAll(oss);
        }
    }

    /**
     * Retorna a lista de Ordens de Serviço do gerenciador.
     */
    public List<OrdemDeServico> listar() {
        return this.ordensDeServico;
    }

    /**
     * Extrai os dados relevantes de um agendamento e suas vendas
     * para criar uma representação do novo modelo OrdemDeServico.
     * @param ag O agendamento base.
     * @param vendasDoAgendamento Lista de vendas associadas à data/cliente.
     * @return Uma lista de objetos OrdemDeServico (um por serviço no agendamento).
     */
    private List<OrdemDeServico> extrairNovasOS(Agendamento ag, List<Venda> vendasDoAgendamento) {
        List<OrdemDeServico> novasOS = new ArrayList<>();

        Cliente cliente = ag.getCliente();
        Funcionario funcionario = ag.getFuncionario();
        String data = ag.getDataHora().split(" ")[0];

        String clienteCpf = (cliente != null) ? cliente.getCpf() : "N/A";
        String funcionarioCpf = (funcionario != null) ? funcionario.getCpf() : "N/A";

        double totalVendas = vendasDoAgendamento.stream().mapToDouble(Venda::getValorTotal).sum();

        for (Servico s : ag.getServicos()) {
            double valorTotalOS = s.getPreco() + totalVendas;

            OrdemDeServico novaOs = new OrdemDeServico(
                    s.getId(),
                    clienteCpf,
                    funcionarioCpf,
                    valorTotalOS,
                    s.getNome(),
                    data,
                    totalVendas > 0 ? vendasDoAgendamento : new ArrayList<>()
            );
            novasOS.add(novaOs);

            totalVendas = 0.0;
        }

        return novasOS;
    }

    /**
     * **MÉTODO DE PERSISTÊNCIA**: Cria e salva as Ordens de Serviço geradas a partir de um Agendamento.
     * @param ag Agendamento que foi finalizado.
     * @param vendasDoAgendamento Vendas associadas à data/cliente.
     */
    public void criarEsalvarOS(Agendamento ag, List<Venda> vendasDoAgendamento) {
        List<OrdemDeServico> novasOS = extrairNovasOS(ag, vendasDoAgendamento);

        if (!novasOS.isEmpty()) {
            this.adicionarTodos(novasOS);
            System.out.println(novasOS.size() + " Ordem(s) de Serviço gerada(s) e salva(s) em 'ordensDeServico.json'.");
        } else {
            System.out.println("Nenhuma Ordem de Serviço gerada a partir do Agendamento.");
        }
    }
    // ... (restante dos métodos de busca e impressão) ...

    public List<Agendamento> buscarAgendamentosPorData(String data) {
        gerenciarAgendamento.carregar();
        return gerenciarAgendamento.listarAgendamentosOrdenadosPorData().stream()
                .filter(ag -> ag.getDataHora() != null && ag.getDataHora().contains(data))
                .collect(Collectors.toList());
    }

    public List<Agendamento> buscarAgendamentosPorCliente(int clienteId) {
        gerenciarAgendamento.carregar();
        return gerenciarAgendamento.listarAgendamentosOrdenadosPorData().stream()
                .filter(ag -> ag.getCliente() != null && ag.getCliente().getId() == clienteId)
                .collect(Collectors.toList());
    }

    public List<Venda> buscarVendasPorCliente(int clienteId) {
        gerenciarVenda.carregar();
        return gerenciarVenda.listar().stream()
                .filter(venda -> venda.getCliente() != null && venda.getCliente().getId() == clienteId)
                .collect(Collectors.toList());
    }

    /**
     * Imprime no console as Ordens de Serviço (na nova estrutura)
     * para uma data específica, carregando do JSON.
     */
    public void imprimirPorData(String data) {
        carregar();
        List<OrdemDeServico> todasOS = listar();

        System.out.println("=== RELATÓRIO DE ORDENS DE SERVIÇO POR DATA (" + data + ") ===");

        List<OrdemDeServico> ossFiltradas = todasOS.stream()
                .filter(os -> os.getDataDoServico() != null && os.getDataDoServico().contains(data))
                .collect(Collectors.toList());

        if (ossFiltradas.isEmpty()) {
            System.out.println("Nenhuma Ordem de Serviço encontrada para o filtro: " + data);
        } else {
            ossFiltradas.forEach(System.out::println);
        }
    }

    /**
     * Imprime no console as Ordens de Serviço (na nova estrutura)
     * para um ID de cliente específico, gerando a partir dos agendamentos/vendas.
     */
    public void imprimirPorCliente(int clienteId) {
        List<Agendamento> agendamentos = buscarAgendamentosPorCliente(clienteId);
        List<Venda> vendasCliente = buscarVendasPorCliente(clienteId);

        System.out.println("=== RELATÓRIO DE ORDENS DE SERVIÇO POR CLIENTE (ID " + clienteId + ") ===");

        if (agendamentos.isEmpty() && vendasCliente.isEmpty()) {
            System.out.println("Nenhuma Ordem de Serviço encontrada para o cliente ID: " + clienteId + " (Agendamentos e Vendas vazias).");
            return;
        }

        for (Agendamento ag : agendamentos) {
            String agendamentoDate = ag.getDataHora().split(" ")[0];
            List<Venda> vendasDoAgendamento = vendasCliente.stream()
                    .filter(v -> v.getDataVenda() != null && v.getDataVenda().equals(agendamentoDate))
                    .collect(Collectors.toList());

            List<OrdemDeServico> novasOS = extrairNovasOS(ag, vendasDoAgendamento);
            novasOS.forEach(System.out::println);
        }
    }
}