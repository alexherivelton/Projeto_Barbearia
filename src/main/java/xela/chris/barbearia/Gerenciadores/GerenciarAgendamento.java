package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.negocio.Agendamento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GerenciarAgendamento {

    private List<Agendamento> agendamentos = new ArrayList<>();
    private RepositorioJson<Agendamento> repo = new RepositorioJson<>(Agendamento.class, "agendamentos.json");



    public void carregar(){
        agendamentos = repo.buscarTodos();
        if(!agendamentos.isEmpty()){
            int maiorId = agendamentos.stream()
                    .mapToInt(Agendamento::getId)
                    .max()
                    .orElse(0);
            Agendamento.atualizarContador(maiorId);
        }
    }


    public void criarAgendamento(Agendamento agendamento){
        this.agendamentos.add(agendamento);
        repo.salvarTodos(agendamentos);
    }

    public boolean removerPorId(int id){
        boolean removido = this.agendamentos.removeIf(agendamento -> agendamento.getId() == id);
        if(removido){
            repo.salvarTodos(agendamentos);
        }
        return removido;
    }


    public Agendamento buscarPorId(int id){
        Iterator<Agendamento> iterator = agendamentos.iterator();
        while(iterator.hasNext()){
            Agendamento agendamento = iterator.next();
            if(agendamento.getId() == id){
                return agendamento;
            }
        }
        System.out.println("Agendamento nao encontrado!");
        return null;
    }

        public boolean verificarDisponibilidadeCadeira(String horario, int idCadeira) {
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getDataHora().equals(horario) && agendamento.getIdCadeira() == idCadeira) {
//                System.out.println("Cadeira já ocupada neste horário!");
                return false;
            }
        }
        return true;
    }

    public boolean verificarHorarioAgendamento(String horario, Funcionario funcionario){
        Iterator<Agendamento> iterator = agendamentos.iterator();
        while(iterator.hasNext()){
            Agendamento agendamento = iterator.next();
            if (agendamento.getFuncionario().equals(funcionario) && agendamento.getDataHora().equals(horario)) {
                System.out.println("Já existe um horario para este funcionario e também neste horario!");
                return false;
            }
        }
        System.out.println("Horario e funcionario Disponivel! Pode realizar o agendamento.");
        return true;
    }

    public List<Agendamento> listarAgendamentos(){
        System.out.println("➡ Agedamentos carregados: " + agendamentos.size());
        for (Agendamento a : agendamentos) {
            System.out.println(a);
        }
        return List.of();
    }

    public void limparAgendamentos(){
        agendamentos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}
