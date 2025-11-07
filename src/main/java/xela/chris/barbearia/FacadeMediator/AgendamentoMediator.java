package xela.chris.barbearia.FacadeMediator;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.Gerenciadores.GerenciarAgendamento;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciarServico;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.negocio.Agendamento;

import java.util.List;

/**
 * Classe que atua como Mediator entre os gerenciadores da barbearia.
 * Centraliza a comunicação e garante o acoplamento mínimo entre os módulos.
 */
public class AgendamentoMediator {
    private final GerenciarCliente gc;
    private final GerenciadorFuncionario gf;
    private final GerenciarServico gs;
    private final GerenciarAgendamento ga;

    public AgendamentoMediator(GerenciarCliente gc, GerenciadorFuncionario gf, GerenciarServico gs, GerenciarAgendamento ga) {
        this.gc = gc;
        this.gf = gf;
        this.gs = gs;
        this.ga = ga;
    }

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

        Agendamento ag = new Agendamento(dataHora, cliente, funcionario, List.of(servico), StatusAtendimentoCliente.AGENDADO);

        ga.criarAgendamento(ag);
        System.out.println("Agendamento criado pelo Mediator: " + cliente.getNome() + " - " + dataHora);
        return true;
    }

    /**
     * Registra um Agendamento já pronto (você cria o objeto fora e passa aqui).
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

        ga.criarAgendamento(ag);
        System.out.println("Agendamento registrado via registrarAgendamento: " + ag.getCliente().getNome());
        return true;
    }

    /**
     * Lista agendamentos (delegando ao gerenciador).
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

    public boolean excluirAgendamento(int idAgendamento) {
        boolean removido = ga.removerPorId(idAgendamento);
        if (removido) {
            System.out.println("Agendamento removido com sucesso!");
        } else {
            System.out.println("Agendamento não encontrado!");
        }
        return removido;
    }

    public Agendamento buscarAgendamento(int idAgendamento) {
        Agendamento achou = ga.buscarPorId(idAgendamento);
        if (!achou.equals(null)){
            System.out.println("Agendamento buscado com sucesso!");
            return achou;
        } else {
            System.out.println("Agendamento nao encontrado!");
        }
        return achou;
    }
}


