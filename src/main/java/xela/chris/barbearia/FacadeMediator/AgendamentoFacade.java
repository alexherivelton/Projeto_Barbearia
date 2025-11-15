package xela.chris.barbearia.FacadeMediator;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciarServico;
import xela.chris.barbearia.Gerenciadores.GerenciarCadeira;
import xela.chris.barbearia.negocio.Agendamento;

/**
 * Fornece uma interface simplificada (Facade) para as operações complexas
 * de gerenciamento de agendamentos.
 *
 * Esta classe atua como um ponto de entrada único para o sistema de agendamentos,
 * escondendo a complexidade da interação entre os diversos gerenciadores
 * (Clientes, Funcionários, Serviços, Cadeiras, etc.).
 *
 * Internamente, ela inicializa e carrega os dados necessários (chamando {@code carregar()})
 * e delega todas as operações de lógica de negócios para um
 * {@link AgendamentoMediator}, que coordena as interações.
 */
public class AgendamentoFacade {
    private final GerenciarCliente gc;
    private final GerenciadorFuncionario gf;
    private final GerenciarServico gs;
    private final GerenciarAgendamento ga;
    private final GerenciarCadeira gca;
    private final AgendamentoMediator mediator;


    /**
     * Construtor padrão do AgendamentoFacade.
     *
     * Inicializa todos os gerenciadores necessários (Cliente, Funcionário,
     * Serviço, Agendamento e Cadeira) e carrega seus dados iniciais
     * (chamando {@code carregar()}).
     *
     * Também instancia o {@link AgendamentoMediator} com as referências
     * para os gerenciadores.
     */
    public AgendamentoFacade (){
        this.gc = new GerenciarCliente();
        gc.carregar();
        this.gf = new GerenciadorFuncionario();
        gf.carregar();
        this.gs = new GerenciarServico();
        this.ga = new GerenciarAgendamento();
        this.gca = new GerenciarCadeira();

        gs.carregar();
        ga.carregar();
        this.mediator = new AgendamentoMediator(gc, gf, gs, ga, gca);
    }


    /**
     * Tenta criar um novo agendamento com base nos IDs fornecidos.
     * Delega a lógica para {@link AgendamentoMediator#agendarPorIds(int, int, int, String)}.
     *
     * @param idCliente O ID do cliente.
     * @param idFuncionario O ID do funcionário.
     * @param idServico O ID do serviço.
     * @param dataHora A data e hora desejada para o agendamento (formato esperado pela implementação).
     * @return {@code true} se o agendamento foi criado com sucesso, {@code false} caso contrário.
     */
    public boolean criarAgendamento(int idCliente, int idFuncionario, int idServico, String dataHora) {
        return mediator.agendarPorIds(idCliente, idFuncionario, idServico, dataHora);
    }

    /**
     * Solicita a persistência (salvamento) de todos os agendamentos.
     * Delega a lógica para {@link AgendamentoMediator#salvarTodosAgendamentos()}.
     */
    public void salvarAgendamentos(){
        mediator.salvarTodosAgendamentos();
    }

    /**
     * Registra um objeto {@link Agendamento} já existente (criado manualmente).
     * Delega a lógica para {@link AgendamentoMediator#registrarAgendamento(Agendamento)}.
     *
     * @param ag O objeto Agendamento a ser registrado.
     * @return {@code true} se o registro foi bem-sucedido, {@code false} caso contrário.
     */
    public boolean registrarAgendamentoManual(Agendamento ag) {
        return mediator.registrarAgendamento(ag);
    }

    /**
     * Solicita a listagem (geralmente no console) de todos os agendamentos.
     * Delega a lógica para {@link AgendamentoMediator#listarAgendamentos()}.
     */
    public void listarAgendamentos() {
        mediator.listarAgendamentos();
    }

    /**
     * Solicita a listagem (geralmente no console) de todos os agendamentos,
     * ordenados por data.
     * Delega a lógica para {@link AgendamentoMediator#listarAgendamentosOrdenadosPorData()}.
     */
    public void listarAgendamentosOrdenadosPorData() {mediator.listarAgendamentosOrdenadosPorData();}

    /**
     * Exclui um agendamento com base no ID fornecido.
     * Delega a lógica para {@link AgendamentoMediator#excluirAgendamento(int)}.
     *
     * @param idAgendamento O ID do agendamento a ser excluído.
     * @return {@code true} se a exclusão foi bem-sucedida, {@code false} caso contrário.
     */
    public boolean excluirAgendamento(int idAgendamento) {
        return mediator.excluirAgendamento(idAgendamento);
    }

    /**
     * Busca um agendamento específico pelo seu ID.
     * Delega a lógica para {@link AgendamentoMediator#buscarAgendamento(int)}.
     *
     * @param idAgendamento O ID do agendamento a ser buscado.
     * @return O objeto {@link Agendamento} encontrado, ou {@code null} se não for encontrado.
     */
    public Agendamento buscarAgendamento(int idAgendamento) {
        return mediator.buscarAgendamento(idAgendamento);
    }

    /**
     * Remove todos os agendamentos do gerenciador.
     * Delega a lógica para {@link AgendamentoMediator#limparAgendamentos()}.
     */
    public void limparAgendamentos() {
        mediator.limparAgendamentos();
    }

}