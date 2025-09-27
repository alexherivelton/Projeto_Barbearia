package xela.chris.barbearia.negocio;

import java.util.List;

public class Agenda {
    List<Agendamento> agendamentos;


    public void adicionarAgendamento(Agendamento agendamento) {}
    public void removerAgendamento(Agendamento agendamento) {}
    public List<Agendamento> verAgenda() {return agendamentos;}
    public List<Agendamento> verAgendamentoLivres(){return agendamentos;}
}
