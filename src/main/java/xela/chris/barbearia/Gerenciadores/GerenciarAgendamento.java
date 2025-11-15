package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.Comparators.AgendamentoDataComparator;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.negocio.Agendamento;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.NotaFiscal;
import xela.chris.barbearia.models.Venda;
import xela.chris.barbearia.Gerenciadores.GerenciarNotaFiscal;
import xela.chris.barbearia.Gerenciadores.GerenciarVenda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Responsável por gerenciar todos os agendamentos da barbearia.
 *
 * Esta classe permite:
 * - Criar novos agendamentos;
 * - Remover agendamentos existentes;
 * - Buscar agendamentos pelo ID;
 * - Validar horário e cadeira para evitar conflitos;
 * - Listar e limpar todos os agendamentos;
 * - Persistir os dados em arquivo JSON.
 *
 * Ela mantém os agendamentos em memória e utiliza o {@code RepositorioJson}
 * para salvar e carregar automaticamente os dados no arquivo {@code agendamentos.json}.
 */
public class GerenciarAgendamento {

    private List<Agendamento> agendamentos = new ArrayList<>();
    private RepositorioJson<Agendamento> repo = new RepositorioJson<>(Agendamento.class, "agendamentos.json");

    /**
     * Construtor que automaticamente carrega os agendamentos gravados no JSON.
     */
    public GerenciarAgendamento() {
        this.carregar();
    }

    /**
     * Carrega todos os agendamentos a partir do arquivo JSON.
     * Se a lista não estiver vazia, atualiza o contador de IDs da classe {@link Agendamento}
     * para evitar duplicação de identificadores ao reiniciar o sistema.
     */
    public void carregar() {
        agendamentos = repo.buscarTodos();
        if (!agendamentos.isEmpty()) {
            int maiorId = agendamentos.stream()
                    .mapToInt(Agendamento::getId)
                    .max()
                    .orElse(0);
            Agendamento.atualizarContador(maiorId);
        }
    }

    /**
     * Cria um novo agendamento, adicionando-o à lista interna e persistindo
     * a atualização no arquivo JSON.
     *
     * @param agendamento Agendamento a ser registrado.
     */
    public void criarAgendamento(Agendamento agendamento) {
        this.agendamentos.add(agendamento);
    }

    /**
     * Remove um agendamento existente com base no seu ID.
     * Caso a remoção seja feita com sucesso, salva a lista atualizada no JSON.
     *
     * @param id Identificador do agendamento a ser removido.
     * @return {@code true} se foi removido; {@code false} caso não exista.
     */
    public boolean removerPorId(int id) {
        boolean removido = this.agendamentos.removeIf(agendamento -> agendamento.getId() == id);
        if (removido) {
            System.out.println("Removido com sucesso!");
        }
        return removido;
    }

    /**
     * Busca um agendamento pelo seu ID.
     *
     * @param id Identificador do agendamento desejado.
     * @return O agendamento encontrado ou {@code null} se não existir.
     */
    public Agendamento buscarPorId(int id) {
        Iterator<Agendamento> iterator = agendamentos.iterator();
        while (iterator.hasNext()) {
            Agendamento agendamento = iterator.next();
            if (agendamento.getId() == id) {
                return agendamento;
            }
        }
        System.out.println("Agendamento nao encontrado!");
        return null;
    }

    /**
     * Verifica se uma cadeira está disponível em um determinado horário.
     * Essa validação impede que duas pessoas agendem o mesmo horário na mesma cadeira.
     *
     * @param horario   Horário desejado.
     * @param idCadeira Número da cadeira.
     * @return {@code true} se estiver disponível; {@code false} caso já esteja ocupada.
     */
    public boolean verificarDisponibilidadeCadeira(String horario, int idCadeira) {
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getDataHora().equals(horario) && agendamento.getIdCadeira() == idCadeira) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se o funcionário já possui um agendamento registrado no mesmo horário,
     * evitando que ele atenda mais de um cliente ao mesmo tempo.
     *
     * @param horario     Horário desejado.
     * @param funcionario Funcionário responsável pelo atendimento.
     * @return {@code true} se o horário estiver livre; {@code false} se já houver outro agendamento.
     */
    public boolean verificarHorarioAgendamento(String horario, Funcionario funcionario) {
        Iterator<Agendamento> iterator = agendamentos.iterator();
        while (iterator.hasNext()) {
            Agendamento agendamento = iterator.next();
            if (agendamento.getFuncionario().equals(funcionario) && agendamento.getDataHora().equals(horario)) {
                System.out.println("Já existe um horario para este funcionario e também neste horario!");
                return false;
            }
        }
        System.out.println("Horario e funcionario Disponivel! Pode realizar o agendamento.");
        return true;
    }

    /**
     * Ordena a lista de agendamentos em memória por data e hora,
     * utilizando o comparador {@link AgendamentoDataComparator}.
     *
     * <p>Esta operação modifica diretamente a lista em memória.
     * Para persistir a ordenação, chame {@link #salvarTodos()} após
     * a ordenação.</p>
     */
    public void ordenarPorData(){
        Collections.sort(agendamentos, new AgendamentoDataComparator());
    }

    /**
     * Exibe todos os agendamentos no console.
     * Atualmente retorna uma lista vazia (pode ser ajustado no futuro).
     *
     * @return Uma lista vazia.
     */
    public List<Agendamento> listarAgendamentos() {
        System.out.println("Agedamentos carregados: " + agendamentos.size());
        for (Agendamento a : agendamentos) {
            System.out.println(a);
        }
        return List.of();
    }

    public List<Agendamento> listarAgendamentosOrdenadosPorData(){
        ordenarPorData();
        return new ArrayList<>(agendamentos); // Só retorna a lista ordenada
    }


    public void salvarTodos(){
        repo.salvarTodos(agendamentos);
    }

    /**
     * Remove todos os agendamentos tanto da memória quanto do arquivo JSON,
     * deixando a lista completamente zerada.
     */
    public void limparAgendamentos() {
        agendamentos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }

    /**
     * Finaliza um agendamento ao marcar o status como atendido e gerar uma nota fiscal.
     *
     * <p>
     * Este método atualiza o status do agendamento identificado por {@code idAgendamento}
     * para {@link StatusAtendimentoCliente#ATENDIDO}, persiste a alteração no arquivo JSON
     * e gera automaticamente uma instância de {@link NotaFiscal} com base no agendamento
     * e nas vendas do cliente que ainda não foram vinculadas a notas fiscais.
     * </p>
     *
     * @param idAgendamento identificador do agendamento a ser finalizado
     * @param gerenciarNotaFiscal gerenciador de notas fiscais para gerar a nota
     * @param gerenciarVenda gerenciador de vendas para buscar vendas do cliente
     * @return {@code true} se o agendamento foi finalizado e a nota fiscal gerada com sucesso, {@code false} caso contrário
     */
    public boolean finalizarAgendamento(int idAgendamento, GerenciarNotaFiscal gerenciarNotaFiscal, GerenciarVenda gerenciarVenda) {
        Agendamento ag = buscarPorId(idAgendamento);
        if (ag == null) {
            System.out.println("Agendamento não encontrado para finalizar!");
            return false;
        }

        // Verifica se o agendamento já foi finalizado
        if (ag.getStatusCliente() == StatusAtendimentoCliente.ATENDIDO) {
            System.out.println("Este agendamento já foi finalizado!");
            return false;
        }

        ag.setStatusCliente(StatusAtendimentoCliente.ATENDIDO);
        repo.salvarTodos(agendamentos);

        // Gera nota fiscal automaticamente
        if (gerenciarNotaFiscal != null && gerenciarVenda != null) {
            gerenciarVenda.carregar();
            gerenciarNotaFiscal.carregar();

            // Busca vendas do cliente que ainda não foram vinculadas a notas fiscais
            List<Venda> vendasCliente = new ArrayList<>();
            if (ag.getCliente() != null) {
                List<Venda> todasVendas = gerenciarVenda.listar();
                List<NotaFiscal> todasNotas = gerenciarNotaFiscal.listar();

                // Filtra vendas do cliente que não estão em nenhuma nota fiscal
                for (Venda venda : todasVendas) {
                    if (venda.getCliente() != null &&
                            venda.getCliente().getId() == ag.getCliente().getId()) {
                        // Verifica se a venda já está em alguma nota fiscal
                        boolean jaVinculada = todasNotas.stream()
                                .anyMatch(nota -> nota.getVendasProdutos() != null &&
                                        nota.getVendasProdutos().contains(venda));
                        if (!jaVinculada) {
                            vendasCliente.add(venda);
                        }
                    }
                }
            }

            // Gera a nota fiscal
            NotaFiscal nota = gerenciarNotaFiscal.gerarNotaFiscal(ag, vendasCliente);
            if (nota != null) {
                System.out.println("Agendamento finalizado e Nota Fiscal gerada automaticamente!");
                System.out.println("ID da Nota Fiscal: " + nota.getId());
                System.out.println("Valor Total: R$ " + String.format("%.2f", nota.getValorTotal()));
                return true;
            } else {
                System.out.println("Agendamento finalizado, mas houve erro ao gerar a Nota Fiscal.");
                return true; // Retorna true pois o agendamento foi finalizado
            }
        }

        return true;
    }

    /**
     * Finaliza um agendamento ao marcar o status como atendido (versão sem geração automática de nota fiscal).
     *
     * @param idAgendamento identificador do agendamento a ser finalizado
     * @return {@code true} se o agendamento foi finalizado, {@code false} caso contrário
     */
    public boolean finalizarAgendamento(int idAgendamento) {
        Agendamento ag = buscarPorId(idAgendamento);
        if (ag == null) {
            System.out.println("Agendamento não encontrado para finalizar!");
            return false;
        }
        ag.setStatusCliente(StatusAtendimentoCliente.ATENDIDO);
        repo.salvarTodos(agendamentos);
        return true;
    }

}