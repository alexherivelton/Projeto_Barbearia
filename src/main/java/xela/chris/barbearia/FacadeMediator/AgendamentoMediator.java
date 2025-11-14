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
 * Classe que atua como Mediator entre os gerenciadores da barbearia.
 * Centraliza a comunicação e garante o acoplamento mínimo entre os módulos.
 */
public class AgendamentoMediator {
    private final GerenciarCliente gc;
    private final GerenciadorFuncionario gf;
    private final GerenciarServico gs;
    private final GerenciarCadeira gca;
    private final GerenciarAgendamento ga;

    public AgendamentoMediator(GerenciarCliente gc, GerenciadorFuncionario gf, GerenciarServico gs, GerenciarAgendamento ga, GerenciarCadeira gca) {
        this.gc = gc;
        this.gf = gf;
        this.gs = gs;
        this.gca = gca;
        this.ga = ga;
    }
// comentario
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
        if (achou != null){
            System.out.println("Agendamento buscado com sucesso!");
            return achou;
        } else {
            System.out.println("Agendamento nao encontrado!");
        }
        return achou;
    }

    public void limparAgendamentos() {
        ga.limparAgendamentos();
    }
}


