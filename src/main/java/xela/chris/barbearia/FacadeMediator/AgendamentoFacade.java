package xela.chris.barbearia.FacadeMediator;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciarServico;
import xela.chris.barbearia.Gerenciadores.GerenciarCadeira;
import xela.chris.barbearia.negocio.Agendamento;

public class AgendamentoFacade {
    private final GerenciarCliente gc;
    private final GerenciadorFuncionario gf;
    private final GerenciarServico gs;
    private final GerenciarAgendamento ga;
    private final GerenciarCadeira gca;
    private final AgendamentoMediator mediator;


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


    public boolean criarAgendamento(int idCliente, int idFuncionario, int idServico, String dataHora) {
        return mediator.agendarPorIds(idCliente, idFuncionario, idServico, dataHora);
    }

    public boolean registrarAgendamentoManual(Agendamento ag) {
        return mediator.registrarAgendamento(ag);
    }

    public void listarAgendamentos() {
        mediator.listarAgendamentos();
    }

    public boolean excluirAgendamento(int idAgendamento) {
        return mediator.excluirAgendamento(idAgendamento);
    }

    public Agendamento buscarAgendamento(int idAgendamento) {
        return mediator.buscarAgendamento(idAgendamento);
    }

    public void limparAgendamentos() {
        mediator.limparAgendamentos();
    }

}
