package xela.chris.barbearia.FacadeMediator;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciarServico;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.Gerenciadores.GerenciarCadeira;
import xela.chris.barbearia.enums.TipoCadeira;
import xela.chris.barbearia.models.Cadeira;
import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.negocio.Agendamento;

import java.util.List;

/**
 * Classe que atua como Mediator (Mediador) entre os diversos gerenciadores
 * do sistema da barbearia.
 *
 * O objetivo desta classe é centralizar a lógica de negócio complexa que
 * envolve múltiplos gerenciadores (como Clientes, Funcionários, Serviços,
 * Cadeiras e Agendamentos), reduzindo o acoplamento entre eles.
 *
 * Ela orquestra as ações necessárias para validar e criar um agendamento,
 * verificando a disponibilidade de todos os recursos necessários.
 */
public class AgendamentoMediator {
    private final GerenciarCliente gc;
    private final GerenciadorFuncionario gf;
    private final GerenciarServico gs;
    private final GerenciarCadeira gca;
    private final GerenciarAgendamento ga;

    /**
     * Construtor do Mediator. Recebe instâncias de todos os gerenciadores
     * que ele precisa coordenar.
     *
     * @param gc Gerenciador de Clientes.
     * @param gf Gerenciador de Funcionários.
     * @param gs Gerenciador de Serviços.
     * @param ga Gerenciador de Agendamentos (o principal alvo das operações).
     * @param gca Gerenciador de Cadeiras.
     */
    public AgendamentoMediator(GerenciarCliente gc, GerenciadorFuncionario gf, GerenciarServico gs, GerenciarAgendamento ga, GerenciarCadeira gca) {
        this.gc = gc;
        this.gf = gf;
        this.gs = gs;
        this.gca = gca;
        this.ga = ga;
    }

    /**
     * Determina o tipo de cadeira ({@link TipoCadeira}) necessário com base
     * em uma lista de serviços.
     *
     * A lógica verifica se algum dos serviços na lista exige o uso de
     * lavagem ou secagem (baseado no atributo {@code isUtilizaLavagemSecagem}
     * do serviço).
     *
     * @param servicos A lista de serviços a ser verificada.
     * @return {@link TipoCadeira#LAVAR_SECAR} se algum serviço exigir,
     * ou {@link TipoCadeira#SERVICO_CORRIQUEIRO} caso contrário.
     */
    private TipoCadeira determinarTipoCadeira(List<Servico> servicos) {
        for (Servico servico : servicos) {
            // Assumindo que "lavar" ou "secar" no nome do serviço indica a necessidade da cadeira de lavar/secar
            // if (servico.getNome().toLowerCase().contains("lavagem") || servico.getNome().toLowerCase().contains("secar")) {
            if (servico.isUtilizaLavagemSecagem()) {
                return TipoCadeira.LAVAR_SECAR;
            }
        }
        return TipoCadeira.SERVICO_CORRIQUEIRO;
    }

    /**
     * Orquestra a criação de um novo agendamento com base nos IDs
     * do cliente, funcionário e serviço.
     *
     * Este método centraliza a lógica de validação:
     * 1. Busca as entidades (Cliente, Funcionario, Servico) por ID.
     * 2. Verifica a disponibilidade do funcionário no horário.
     * 3. Determina o tipo de cadeira necessária para o serviço.
     * 4. Busca cadeiras disponíveis desse tipo e verifica sua disponibilidade no horário.
     * 5. Se tudo estiver disponível, cria o {@link Agendamento} e o registra.
     *
     * @param idC O ID do Cliente.
     * @param idF O ID do Funcionário.
     * @param idS O ID do Serviço.
     * @param dataHora A data e hora desejada para o agendamento.
     * @return {@code true} se o agendamento foi criado com sucesso,
     * {@code false} se houve falha em qualquer etapa da validação
     * (dados não encontrados, funcionário ou cadeira indisponível).
     */
    public boolean agendarPorIds(int idC, int idF, int idS, String dataHora) {
        Cliente cliente = gc.buscarCliente(idC);
        Funcionario funcionario = gf.buscarFuncionario(idF);
        Servico servico = gs.buscarPorId(idS);

        if (cliente == null) {
            System.out.println("Cliente não encontrado (id=" + idC + ")");
            return false;
        }
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado (id=" + idF + ")");
            return false;
        }
        if (servico == null) {
            System.out.println("Serviço não encontrado (id=" + idS + ")");
            return false;
        }

        if (!ga.verificarHorarioAgendamento(dataHora, funcionario)) {
            System.out.println("Funcionário ocupado neste horário: " + dataHora);
            return false;
        }

        // Lógica da Cadeira
        TipoCadeira tipoCadeiraNecessaria = determinarTipoCadeira(List.of(servico));
        List<Cadeira> cadeirasDisponiveis = gca.buscarPorTipo(tipoCadeiraNecessaria);
        int idCadeiraSelecionada = -1;

        for (Cadeira cadeira : cadeirasDisponiveis) {
            if (ga.verificarDisponibilidadeCadeira(dataHora, cadeira.getId())) {
                idCadeiraSelecionada = cadeira.getId();
                System.out.println( cadeira.getNome() + " disponível para agendamento.");
                break;
            }
        }

        if (idCadeiraSelecionada == -1) {
            System.out.println("Nenhuma cadeira do tipo " + tipoCadeiraNecessaria + " disponível neste horário.");
            return false;
        }

        Agendamento ag = new Agendamento(dataHora, cliente, funcionario, List.of(servico), StatusAtendimentoCliente.AGENDADO, idCadeiraSelecionada);

        ga.criarAgendamento(ag);
        System.out.println("Agendamento criado pelo Mediator: " + cliente.getNome() + " - " + dataHora);
        return true;
    }

    /**
     * Registra um objeto {@link Agendamento} já existente (criado manualmente).
     *
     * Este método é usado quando o objeto Agendamento já foi instanciado.
     * Ele realiza as validações de disponibilidade necessárias (funcionário e cadeira).
     *
     * Se o agendamento fornecido não tiver um ID de cadeira ({@code getIdCadeira() == 0}),
     * este método tentará encontrar e atribuir uma cadeira disponível do tipo correto.
     * Se o agendamento já tiver um ID de cadeira, ele apenas validará a
     * disponibilidade dessa cadeira específica.
     *
     * @param ag O objeto Agendamento pré-construído a ser registrado.
     * @return {@code true} se o registro foi bem-sucedido, {@code false} se o
     * agendamento for nulo, inválido ou houver conflito de disponibilidade.
     */
    public boolean registrarAgendamento(Agendamento ag) {
        if (ag == null) {
            System.out.println("Agendamento nulo");
            return false;
        }
        // valida campos mínimos
        if (ag.getCliente() == null || ag.getFuncionario() == null || ag.getServicos() == null) {
            System.out.println("Agendamento inválido (cliente/funcionario/servicos faltando)");
            return false;
        }
        if (!ga.verificarHorarioAgendamento(ag.getDataHora(), ag.getFuncionario())) {
            System.out.println("Funcionário ocupado neste horário: " + ag.getDataHora());
            return false;
        }

        // Lógica da Cadeira para agendamento manual
        if (ag.getIdCadeira() == 0) { // Se a cadeira não foi definida no agendamento manual
            TipoCadeira tipoCadeiraNecessaria = determinarTipoCadeira(ag.getServicos());
            List<Cadeira> cadeirasDisponiveis = gca.buscarPorTipo(tipoCadeiraNecessaria);
            int idCadeiraSelecionada = -1;

            for (Cadeira cadeira : cadeirasDisponiveis) {
                if (ga.verificarDisponibilidadeCadeira(ag.getDataHora(), cadeira.getId())) {
                    idCadeiraSelecionada = cadeira.getId();
                    ag.setIdCadeira(idCadeiraSelecionada);
                    System.out.println(cadeira.getNome() + " selecionada para agendamento manual.");
                    break;
                }
            }

            if (idCadeiraSelecionada == -1) {
                System.out.println("Nenhuma cadeira do tipo " + tipoCadeiraNecessaria + " disponível neste horário para agendamento manual.");
                return false;
            }
        } else {
            // Se a cadeira foi definida, apenas verifica a disponibilidade
            if (!ga.verificarDisponibilidadeCadeira(ag.getDataHora(), ag.getIdCadeira())) {
                System.out.println("Cadeira " + ag.getIdCadeira() + " ocupada neste horário para agendamento manual.");
                return false;
            }
        }
        System.out.println("Agendamento registrado via registrarAgendamento: " + ag.getCliente().getNome());
        return true;
    }

    /**
     * Lista todos os agendamentos (delegando ao gerenciador).
     * Imprime a lista de agendamentos ou uma mensagem se estiver vazia.
     */
    public void listarAgendamentos() {
        var lista = ga.listarAgendamentos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }
        System.out.println("\nLista de agendamentos:");
        for (Agendamento a : lista) {
            System.out.println(a);
        }
    }

    /**
     * Lista todos os agendamentos ordenados por data.
     * Delega a busca e ordenação ao {@link GerenciarAgendamento} e imprime
     * o resultado no console.
     */
    public void listarAgendamentosOrdenadosPorData(){
        List<Agendamento> listaAgendamento = ga.listarAgendamentosOrdenadosPorData();

        if (listaAgendamento == null || listaAgendamento.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
        } else {
            System.out.println("\nLista de agendamentos ordenados:");
            for (Agendamento a : listaAgendamento) {
                System.out.println(a);
            }
        }
    }

    /**
     * Exclui um agendamento com base no seu ID.
     * Delega a remoção ao {@link GerenciarAgendamento}.
     *
     * @param idAgendamento O ID do agendamento a ser excluído.
     * @return {@code true} se a remoção foi bem-sucedida,
     * {@code false} se o agendamento não foi encontrado.
     */
    public boolean excluirAgendamento(int idAgendamento) {
        boolean removido = ga.removerPorId(idAgendamento);
        if (removido) {
            System.out.println("Agendamento removido com sucesso!");
        } else {
            System.out.println("Agendamento não encontrado!");
        }
        return removido;
    }

    /**
     * Busca um agendamento específico pelo seu ID.
     * Delega a busca ao {@link GerenciarAgendamento}.
     *
     * @param idAgendamento O ID do agendamento a ser buscado.
     * @return O objeto {@link Agendamento} encontrado, ou {@code null}
     * se não for encontrado.
     */
    public Agendamento buscarAgendamento(int idAgendamento) {
        Agendamento achou = ga.buscarPorId(idAgendamento);
        if (achou != null){
            System.out.println("Agendamento buscado com sucesso!");
            return achou;
        } else {
            System.out.println("Agendamento nao encontrado!");
        }
        return achou;
    }


    /**
     * Solicita a persistência (salvamento) de todos os agendamentos.
     * Delega a operação ao {@link GerenciarAgendamento}.
     */
    public void salvarTodosAgendamentos(){
        ga.salvarTodos();
        System.out.println("Agendamentos salvos com sucesso!");
    }

    /**
     * Remove todos os agendamentos do gerenciador (limpa a lista em memória).
     * Delega a operação ao {@link GerenciarAgendamento}.
     */
    public void limparAgendamentos() {
        ga.limparAgendamentos();
    }
}